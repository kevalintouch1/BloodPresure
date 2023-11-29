package com.blood.presure.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class NewViewPagerAdapter extends FragmentStateAdapter {
    public ArrayList<Fragment> listFm;

    public NewViewPagerAdapter(FragmentActivity fragmentActivity, ArrayList<Fragment> arrayList) {
        super(fragmentActivity);
        this.listFm = arrayList;
    }

    public final int getItemCount() {
        return this.listFm.size();
    }

    @NonNull
    public Fragment createFragment(int i) {
        return this.listFm.get(i);
    }
}
