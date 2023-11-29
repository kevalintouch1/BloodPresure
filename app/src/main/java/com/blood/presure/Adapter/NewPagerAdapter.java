package com.blood.presure.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.blood.presure.Fragment.NewHistoryFragment;
import com.blood.presure.NewBloodApplication;
import com.blood.presure.R;


public class NewPagerAdapter extends FragmentStatePagerAdapter {
    public int getCount() {
        return 1;
    }

    public Fragment getItem(int i) {
        return NewHistoryFragment.newInstance();
    }

    public NewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public CharSequence getPageTitle(int i) {
        if (i == 0) {
            return NewBloodApplication.getInstance().getString(R.string.chart);
        }
        return NewBloodApplication.getInstance().getString(R.string.history);
    }
}
