package com.example.lcc.childhood;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class Guide extends AppCompatActivity {

    private ViewPager mViewPager;
    private int[] mImgIds=new int[]
            {R.drawable.main,R.drawable.main1,R.drawable.main1};
    private List<ImageView> mImages=new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        mViewPager=(ViewPager)findViewById(R.id.id_viewpager);
        //为viewpager添加动画效果
       // mViewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        mViewPager.setPageTransformer(true,new DepthPageTransformer());
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView=new ImageView(Guide.this);
                imageView.setImageResource(mImgIds[position]);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);
                mImages.add(imageView);
               /* mImgIds[position].setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {
                        isInternetPresent = cd.isConnectingToInternet();
                        // TODO Auto-generated method stub
                        if(isInternetPresent)
                        {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "无网络", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImages.get(position));
            }

            @Override
            public int getCount() {
                return mImgIds.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        });
    }
}
