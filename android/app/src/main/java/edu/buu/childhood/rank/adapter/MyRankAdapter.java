package edu.buu.childhood.rank.adapter;

/**
 * Created by lcc on 2016/7/1.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import edu.buu.childhood.rank.activity.MainRank;
import edu.buu.childhood.rank.fragment.Ranking_gamelist;
import edu.buu.childhood.rank.fragment.Ranking_personlist;

public class MyRankAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT =2;
    private Ranking_personlist myFragment1 = null;
    private Ranking_gamelist myFragment2 = null;



    public MyRankAdapter(FragmentManager fm) {
        super(fm);
        myFragment1 = new Ranking_personlist();
        myFragment2 = new Ranking_gamelist();

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
            case MainRank.PAGE_ONE:
                fragment= myFragment1;
                break;
            case MainRank.PAGE_TWO:
                fragment = myFragment2;
                break;

        }
        return fragment;
    }
}
