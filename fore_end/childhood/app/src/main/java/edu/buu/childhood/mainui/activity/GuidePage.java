package edu.buu.childhood.mainui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.util.CloseActivity;

public class GuidePage extends BaseAvtivity {
    private ViewPager viewPager;
    private List<View> mImageViews; // 滑动的图片集合
    private int[] imageResId; // 图片ID
    private int currentItem = 0; // 当前图片的索引号
    private GestureDetector gestureDetector; // 用户滑动
    /**
     * 记录当前分页ID
     */
    private int flaggingWidth;// 互动翻页所需滚动的长度是当前屏幕宽度的1/3

    private EdgeEffectCompat leftEdge;
    private EdgeEffectCompat rightEdge;

//    private boolean canJump = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.guide_pages);
        gestureDetector = new GestureDetector(new GuideViewTouch());
// 获取分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        flaggingWidth = dm.widthPixels / 3;
        imageResId = new int[]
                {R.drawable.guide_page1, R.drawable.guide_page2, R.drawable.guide_page3};
        mImageViews = new ArrayList<View>();
// 初始化图片资源
        LayoutInflater viewInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
// 0
        View convertView0 = viewInflater.inflate(R.layout.guide_pages_item, null);
        LinearLayout linearLayout0 = (LinearLayout) convertView0.findViewById(R.id.guide_item);
        linearLayout0.setBackgroundResource(imageResId[0]);
        mImageViews.add(linearLayout0);
// 1
        View convertView1 = viewInflater.inflate(R.layout.guide_pages_item, null);
        LinearLayout linearLayout1 = (LinearLayout) convertView1.findViewById(R.id.guide_item);
        linearLayout1.setBackgroundResource(imageResId[1]);
        mImageViews.add(linearLayout1);
// 2
        View convertView2 = viewInflater.inflate(R.layout.guide_pages_item, null);
        LinearLayout linearLayout2 = (LinearLayout) convertView2.findViewById(R.id.guide_item);
        linearLayout2.setBackgroundResource(imageResId[2]);
        mImageViews.add(linearLayout2);


        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
// 设置一个监听器，当ViewPager中的页面改变时调用
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        try {
            Field leftEdgeField = viewPager.getClass().getDeclaredField("mLeftEdge");
            Field rightEdgeField = viewPager.getClass().getDeclaredField("mRightEdge");
            if (leftEdgeField != null && rightEdgeField != null) {
                leftEdgeField.setAccessible(true);
                rightEdgeField.setAccessible(true);
                leftEdge = (EdgeEffectCompat) leftEdgeField.get(viewPager);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(viewPager);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }

    private class GuideViewTouch extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (currentItem == 3) {
                if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY() - e2.getY()) && (e1.getX() - e2.getX() <= (-flaggingWidth) || e1.getX() - e2.getX() >= flaggingWidth)) {
                    if (e1.getX() - e2.getX() >= flaggingWidth) {
                        GoToMainActivity();
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * 进入主界面
     */
    void GoToMainActivity() {
        Intent intent = new Intent(GuidePage.this, Main_interface.class);
        startActivity(intent);
        finish();
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Administrator
     */
    private class MyPageChangeListener implements OnPageChangeListener {
        public void onPageSelected(int position) {
            currentItem = position;
        }

        public void onPageScrollStateChanged(int arg0) {
            if (rightEdge != null && !rightEdge.isFinished()) {//到了最后一张并且还继续拖动
                startActivity(new Intent(GuidePage.this, Main_interface.class));
                GuidePage.this.finish();
            }
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (leftEdge != null && rightEdge != null) {
                leftEdge.finish();
                leftEdge.setSize(0, 0);
                rightEdge.setSize(0, 0);
            }
        }
    }

    /**
     * 填充ViewPager页面的适配器
     *
     * @author Administrator
     */
    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageResId.length;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mImageViews.get(arg1));
            return mImageViews.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}