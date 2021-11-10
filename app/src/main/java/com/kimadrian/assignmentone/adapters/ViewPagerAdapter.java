package com.kimadrian.assignmentone.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kimadrian.assignmentone.fragments.checkoutFragment;
import com.kimadrian.assignmentone.fragments.purchaseFragment;
import com.kimadrian.assignmentone.fragments.registerFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

    public ViewPagerAdapter(Context context,FragmentManager fm, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            checkoutFragment checkoutFragment = new checkoutFragment();
            return  checkoutFragment;
        }
        if (position == 1){
            purchaseFragment purchaseFragment = new purchaseFragment();
            return purchaseFragment;
        }

        if (position == 2){
            registerFragment registerFragment = new registerFragment();
            return registerFragment;
        } else{
            return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
