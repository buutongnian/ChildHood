package edu.buu.childhood.achievement.adapter;


/**
 * Created by lcc on 2016/9/27.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import edu.buu.childhood.achievement.Fragment.MedalFragment;
import edu.buu.childhood.achievement.Fragment.RecordFragment;
import edu.buu.childhood.achievement.Fragment.TrackFragment;
import edu.buu.childhood.achievement.activity.AchievementActivity;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 3;
    private MedalFragment myFragment2 = null;
    private TrackFragment myFragment1 = null;
    private RecordFragment myFragment3 = null;


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        myFragment2 = new MedalFragment();
        myFragment1 = new TrackFragment();
        myFragment3= new RecordFragment();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case AchievementActivity.PAGE_ONE:
                if(myFragment1==null){
                    myFragment1=new TrackFragment();
                }
                fragment = myFragment1;
                break;
            case AchievementActivity.PAGE_TWO:
                if(myFragment2==null){
                    myFragment2=new MedalFragment();
                }
                fragment = myFragment2;
                break;
            case AchievementActivity.PAGE_THREE:
                if(myFragment3==null){
                    myFragment3=new RecordFragment();
                }
                fragment = myFragment3;
                break;

        }
        return fragment;
    }
}