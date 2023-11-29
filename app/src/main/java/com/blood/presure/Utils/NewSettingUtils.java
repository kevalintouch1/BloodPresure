package com.blood.presure.Utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public class NewSettingUtils {

    public interface onResultRate {
        void onResult();
    }

    public static void showRatingGoogle(Activity activity, onResultRate onresultrate) {
        ReviewManager create = ReviewManagerFactory.create(activity);

        create.requestReviewFlow().addOnCompleteListener(task -> showRatingGooglee(create, activity, onresultrate, task));
    }

    public static void showRatingGooglee(ReviewManager reviewManager, final Activity activity, final onResultRate onresultrate, Task task) {
        if (task.isSuccessful()) {
            ReviewInfo reviewInfo2 = (ReviewInfo) task.getResult();
            if (reviewInfo2 != null) {
                reviewManager.launchReviewFlow(activity, reviewInfo2).addOnCompleteListener(task1 -> {
                    Activity activity1 = new Activity();
                    NewSettingUtils.funcRateAppOnStore(activity1, activity1.getOpPackageName());
                    onResultRate onresultrate1 = null;
                    if (onresultrate1 != null) {
                        onresultrate1.onResult();
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
