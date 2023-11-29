package com.blood.presure.Utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.blood.presure.activity.SettingUtils$$ExternalSyntheticLambda0;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public class NewSettingUtils {
    private static ReviewInfo reviewInfo;

    public interface onResultRate {
        void onResult();
    }

    public static void showRatingGoogle(Activity activity, onResultRate onresultrate) {
        ReviewManager create = ReviewManagerFactory.create(activity);
        create.requestReviewFlow().addOnCompleteListener(new SettingUtils$$ExternalSyntheticLambda0(create, activity, onresultrate));
    }

    public static /* synthetic */ void lambda$showRatingGoogle$0(ReviewManager reviewManager, final Activity activity, final onResultRate onresultrate, Task task) {
        if (task.isSuccessful()) {
            ReviewInfo reviewInfo2 = (ReviewInfo) task.getResult();
            reviewInfo = reviewInfo2;
            if (reviewInfo2 != null) {
                reviewManager.launchReviewFlow(activity, reviewInfo2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @RequiresApi(api = Build.VERSION_CODES.Q)
                    public void onComplete(Task<Void> task) {
                        Activity activity = new Activity();
                        NewSettingUtils.funcRateAppOnStore(activity, activity.getOpPackageName());
                        onResultRate onresultrate = null;
                        if (onresultrate != null) {
                            onresultrate.onResult();
                        }
                    }
                });
            }
        }
    }

    public static void funcRateAppOnStore(Context context, String str) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + str)));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
