package edu.buu.childhood.game.refresh; /**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.buu.childhood.R;

/*自定义类，用于处理ListView下拉刷新的显示部分
 *
 * */
public class XListViewHeader extends LinearLayout {
    private LinearLayout mContainer; // 整体显示部分
    private ImageView mArrowImageView; // 箭头图像
    private ProgressBar mProgressBar; // 进度条
    private TextView mHintTextView; // 显示提示文字
    private int mState = STATE_NORMAL; // 当前下拉状态

    // 箭头旋转的动画
    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;
    // 设置动画持续时间
    private final int ROTATE_ANIM_DURATION = 180;

    // 定义所有下拉状态
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;

    public XListViewHeader(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public XListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    // 自定义方法，用于初始化显示
    private void initView(Context context) {
        // 初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams(
                LayoutParams.FILL_PARENT, 0);
        // 初始化LinearLayout对象
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.xlistview_header, null);
        // 将mContainer添加到当前自定义控件中显示
        addView(mContainer, lp);
        // 设置mContainer的位置为靠底
        setGravity(Gravity.BOTTOM);
        // 初始化相关控件对象
        mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
        mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
        mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);
        // 初始化动画对象
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }

    // 自定义方法，根据参数对应的状态，刷新相关控件的显示
    public void setState(int state) {
        if (state == mState)
            return;
        if (state == STATE_REFRESHING) { // 显示进度
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else { // 显示箭头图片
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_READY) {
                    mArrowImageView.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    mArrowImageView.clearAnimation();
                }
                mHintTextView.setText("下拉可以刷新");
                break;
            case STATE_READY:
                if (mState != STATE_READY) {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mHintTextView.setText("松手后开始刷新");
                }
                break;
            case STATE_REFRESHING:
                mHintTextView.setText("正在加载中");
                break;
            default:
        }

        mState = state;
    }

    // 自定义方法，用于设置可见高度
    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) mContainer
                .getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    // 自定义方法啊，用户获取高度
    public int getVisiableHeight() {
        return mContainer.getHeight();
    }

}
