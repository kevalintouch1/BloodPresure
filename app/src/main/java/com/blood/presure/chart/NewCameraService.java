package com.blood.presure.chart;

import android.app.Activity;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Surface;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.Collections;

public class NewCameraService {

    public final Activity activity;
    private String cameraId;
    private final Handler handler;
    public CameraDevice mCameraDevice;
    public CaptureRequest.Builder previewCaptureRequestBuilder;
    public CameraCaptureSession previewSession;

    public NewCameraService(Activity activity2, Handler mainHandler) {
        this.activity = activity2;
        this.handler = mainHandler;
    }

    public void start(final Surface surface) {
        CameraManager cameraManager = (CameraManager) this.activity.getSystemService("camera");
        try {
            this.cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            if (ActivityCompat.checkSelfPermission(this.activity, "android.permission.CAMERA") != 0) {
                Handler handler2 = this.handler;
                handler2.sendMessage(Message.obtain(handler2, 3, "No permission to take photos"));
                return;
            }
            String str = this.cameraId;
            if (str != null) {
                cameraManager.openCamera(str, new CameraDevice.StateCallback() {
                    public void onDisconnected(CameraDevice cameraDevice) {
                    }

                    public void onError(CameraDevice cameraDevice, int i) {
                        Toast.makeText(NewCameraService.this.activity, "Error Hearth!", 0).show();
                    }

                    public void onOpened(final CameraDevice cameraDevice) {
                        NewCameraService.this.mCameraDevice = cameraDevice;
                        try {
                            cameraDevice.createCaptureSession(Collections.singletonList(surface), new CameraCaptureSession.StateCallback() {
                                public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                                    NewCameraService.this.previewSession = cameraCaptureSession;
                                    try {
                                        NewCameraService.this.previewCaptureRequestBuilder = cameraDevice.createCaptureRequest(1);
                                        NewCameraService.this.previewCaptureRequestBuilder.addTarget(surface);
                                        NewCameraService.this.previewCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, 2);
                                        new HandlerThread("CameraPreview").start();
                                        NewCameraService.this.previewSession.setRepeatingRequest(NewCameraService.this.previewCaptureRequestBuilder.build(), (CameraCaptureSession.CaptureCallback) null, (Handler) null);
                                    } catch (CameraAccessException e) {
                                        e.printStackTrace();
                                    }
                                }

                                public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                                    Toast.makeText(NewCameraService.this.activity, "onConfigureFailed!", 0).show();
                                }
                            }, (Handler) null);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }, this.handler);
            }
        } catch (CameraAccessException | SecurityException e2) {
            e2.printStackTrace();
        }
    }

    public void stop() {
        try {
            this.mCameraDevice.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
