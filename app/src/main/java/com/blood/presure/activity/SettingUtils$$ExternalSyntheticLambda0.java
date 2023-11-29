package com.blood.presure.activity;

import android.app.Activity;

import com.blood.presure.Utils.NewSettingUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewManager;


public final  class SettingUtils$$ExternalSyntheticLambda0 implements OnCompleteListener {
    public final  ReviewManager f$0;
    public final  Activity f$1;
    public final  NewSettingUtils.onResultRate f$2;

    public  SettingUtils$$ExternalSyntheticLambda0(ReviewManager reviewManager, Activity activity, NewSettingUtils.onResultRate onresultrate) {
        this.f$0 = reviewManager;
        this.f$1 = activity;
        this.f$2 = onresultrate;
    }

    public final void onComplete(Task task) {
        NewSettingUtils.lambda$showRatingGoogle$0(this.f$0, this.f$1, this.f$2, task);
    }
}
