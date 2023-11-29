package com.blood.presure.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class NewHomeUtils {
    private static Context mContext;
    private static IntentFilter mFilter;
    private static ResumeReceiver mReceiver;
    public OnHomeResumeListener mListener;


    public interface OnHomeResumeListener {
        void onHomeLongPressed();

        void onHomePressed();
    }


    class ResumeReceiver extends BroadcastReceiver {
        ResumeReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            String stringExtra;
            if (intent.getAction().equals("android.intent.action.CLOSE_SYSTEM_DIALOGS") && (stringExtra = intent.getStringExtra("reason")) != null && NewHomeUtils.this.mListener != null) {
                if (stringExtra.equals("homekey")) {
                    NewHomeUtils.this.mListener.onHomePressed();
                } else if (stringExtra.equals("recentapps")) {
                    NewHomeUtils.this.mListener.onHomeLongPressed();
                }
            }
        }
    }

    public NewHomeUtils(Context context) {
        mContext = context;
        mFilter = new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS");
    }

    public void setOnHomePressedListener(OnHomeResumeListener onHomeResumeListener) {
        this.mListener = onHomeResumeListener;
        mReceiver = new ResumeReceiver();
    }

    public static void startHome() {
        ResumeReceiver resumeReceiver = mReceiver;
        if (resumeReceiver != null) {
            mContext.registerReceiver(resumeReceiver, mFilter);
        }
    }

    public static void stopHome() {
        ResumeReceiver resumeReceiver = mReceiver;
        if (resumeReceiver != null) {
            mContext.unregisterReceiver(resumeReceiver);
        }
    }
}
