package edu.buu.childhood.mainui.activity;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import edu.buu.childhood.R;
import edu.buu.childhood.achievement.activity.AchievementActivity;
import edu.buu.childhood.baidumap.activity.MainBaidu;
import edu.buu.childhood.chat.activity.ChatContactActivity;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.forum.someWebview;
import edu.buu.childhood.game.activity.GameRules;
import edu.buu.childhood.login.activity.Login;
import edu.buu.childhood.my.activity.My;
import edu.buu.childhood.rank.activity.MainRank;
import edu.buu.childhood.register.activity.PerfectInfo;
import edu.buu.childhood.util.CloseActivity;


public class Main_interface extends BaseAvtivity {

    private long exitTime = 0;

    private Button buttongame;
    private Button buttonforum;
    private Button buttonrank;
    private Button buttonmap;
    private Button buttonmy;
    private Button buttonchat;
    private ImageButton ibLevelButton;

    private ImageView messageTip;

    private static Handler mainHandler;

    public static Handler getMainHandler() {
        return mainHandler;
    }

    private int[] imageResource = {R.drawable.level_name1, R.drawable.level_name2, R.drawable.level_name3, R.drawable.level_name4, R.drawable.level_name5,
            R.drawable.level_name6, R.drawable.level_name7, R.drawable.level_name8, R.drawable.level_name9, R.drawable.level_name10,
            R.drawable.level_name11, R.drawable.level_name12, R.drawable.level_name13, R.drawable.level_name14, R.drawable.level_name15};

    private static List<ScaleAnimation> blingBlingAnims;
    private static List<ImageView> blingBlingImages;

    private static List<Animation> blingMeteorAnims;
    private static List<ImageView> blingMeteors;

    private Timer starAnimTimer;
    private Timer meteorAnimTimer;

    private TimerTask blingStarTask;
    private TimerTask blingMeteorTask;
    private ImageView meteorImage;
    private Animation meteorAnim;

    private ImageView blingImage;
    private ScaleAnimation blingAnim;

    private ImageView blingStar1;
    private ScaleAnimation blingBlingAnim1 = getScaleAnimation();
    private ImageView blingStar2;
    private ScaleAnimation blingBlingAnim2 = getScaleAnimation();
    private ImageView blingStar3;
    private ScaleAnimation blingBlingAnim3 = getScaleAnimation();
    private ImageView blingStar4;
    private ScaleAnimation blingBlingAnim4 = getScaleAnimation();
    private ImageView blingStar5;
    private ScaleAnimation blingBlingAnim5 = getScaleAnimation();
    private ImageView blingStar6;
    private ScaleAnimation blingBlingAnim6 = getScaleAnimation();
    private ImageView blingStar7;
    private ScaleAnimation blingBlingAnim7 = getScaleAnimation();
    private ImageView blingStar8;
    private ScaleAnimation blingBlingAnim8 = getScaleAnimation();
    private ImageView blingStar9;
    private ScaleAnimation blingBlingAnim9 = getScaleAnimation();
    private ImageView blingStar10;
    private ScaleAnimation blingBlingAnim10 = getScaleAnimation();

    private ImageView blingMeteor1;
    private Animation blingMeteorAnim1;
    private ImageView blingMeteor2;
    private Animation blingMeteorAnim2;
    private ImageView blingMeteor3;
    private Animation blingMeteorAnim3;

    private ScaleAnimation getScaleAnimation() {
        return new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f) {
            {
                setDuration(250);
                setRepeatMode(Animation.REVERSE);
                setRepeatCount(1);
                setFillAfter(true);
            }
        };
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.main_interface);

        blingStar1 = (ImageView) findViewById(R.id.main_star_1);
        blingStar1.setAnimation(blingBlingAnim1);
        blingStar2 = (ImageView) findViewById(R.id.main_star_2);
        blingStar2.setAnimation(blingBlingAnim2);
        blingStar3 = (ImageView) findViewById(R.id.main_star_3);
        blingStar3.setAnimation(blingBlingAnim3);
        blingStar4 = (ImageView) findViewById(R.id.main_star_4);
        blingStar4.setAnimation(blingBlingAnim4);
        blingStar5 = (ImageView) findViewById(R.id.main_star_5);
        blingStar5.setAnimation(blingBlingAnim5);
        blingStar6 = (ImageView) findViewById(R.id.main_star_6);
        blingStar6.setAnimation(blingBlingAnim6);
        blingStar7 = (ImageView) findViewById(R.id.main_star_7);
        blingStar7.setAnimation(blingBlingAnim7);
        blingStar8 = (ImageView) findViewById(R.id.main_star_8);
        blingStar8.setAnimation(blingBlingAnim8);
        blingStar9 = (ImageView) findViewById(R.id.main_star_9);
        blingStar9.setAnimation(blingBlingAnim9);
        blingStar10 = (ImageView) findViewById(R.id.main_star_10);
        blingStar10.setAnimation(blingBlingAnim10);

        blingMeteor1 = (ImageView) findViewById(R.id.main_meteor_1);
        blingMeteorAnim1 = AnimationUtils.loadAnimation(this, R.anim.main_meteor_1);
        blingMeteorAnim1.setFillAfter(true);
        blingMeteor1.setAnimation(blingMeteorAnim1);
        blingMeteor2 = (ImageView) findViewById(R.id.main_meteor_2);
        blingMeteorAnim2 = AnimationUtils.loadAnimation(this, R.anim.main_meteor_2);
        blingMeteorAnim2.setFillAfter(true);
        blingMeteor2.setAnimation(blingMeteorAnim2);
        blingMeteor3 = (ImageView) findViewById(R.id.main_meteor_3);
        blingMeteorAnim3 = AnimationUtils.loadAnimation(this, R.anim.main_meteor_3);
        blingMeteorAnim3.setFillAfter(true);
        blingMeteor3.setAnimation(blingMeteorAnim3);

        blingMeteors = new ArrayList<ImageView>() {
            {
                add(blingMeteor1);
                add(blingMeteor2);
                add(blingMeteor3);
            }
        };

        blingMeteorAnims = new ArrayList<Animation>() {
            {
                add(blingMeteorAnim1);
                add(blingMeteorAnim2);
                add(blingMeteorAnim3);
            }
        };

        blingBlingImages = new ArrayList<ImageView>() {
            {
                add(blingStar1);
                add(blingStar2);
                add(blingStar3);
                add(blingStar4);
                add(blingStar5);
                add(blingStar6);
                add(blingStar7);
                add(blingStar8);
                add(blingStar9);
                add(blingStar10);
            }
        };

        blingBlingAnims = new ArrayList<ScaleAnimation>() {
            {
                add(blingBlingAnim1);
                add(blingBlingAnim2);
                add(blingBlingAnim3);
                add(blingBlingAnim4);
                add(blingBlingAnim5);
                add(blingBlingAnim6);
                add(blingBlingAnim7);
                add(blingBlingAnim8);
                add(blingBlingAnim9);
                add(blingBlingAnim10);
            }
        };

        GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
        buttonforum = (Button) findViewById(R.id.main_btn_forum);
        buttongame = (Button) findViewById(R.id.main_btn_game);
        buttonrank = (Button) findViewById(R.id.main_btn_rank);
        buttonmap = (Button) findViewById(R.id.main_btn_onekey_convene);
        buttonmy = (Button) findViewById(R.id.main_btn_my);
        buttonchat = (Button) findViewById(R.id.main_btn_friends);

        messageTip = (ImageView) findViewById(R.id.main_message_tip);

        if (((GlobalVeriable) getApplication()).isHaveMessage()) {
            messageTip.setVisibility(View.VISIBLE);
        } else {
            messageTip.setVisibility(View.GONE);
        }
        mainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String handleEvent = bundle.getString("handleEvent");
                switch (handleEvent) {
                    case "changeMessageTip":
                        if (messageTip != null) {
                            if (((GlobalVeriable) getApplication()).isHaveMessage()) {
                                ((Activity) messageTip.getContext()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        messageTip.setVisibility(View.VISIBLE);
                                    }
                                });
                            } else {
                                ((Activity) messageTip.getContext()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        messageTip.setVisibility(View.GONE);
                                    }
                                });
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        ibLevelButton = (ImageButton) findViewById(R.id.main_btn_achievement);
        ibLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_interface.this, AchievementActivity.class));
            }
        });

        //主页面称号
        if (globalVeriable.getLogin()) {
            if (globalVeriable.getUserLevel() >= 0) {
                ibLevelButton.setImageResource(imageResource[globalVeriable.getUserLevel()]);
            }
            if (!globalVeriable.isPerfectInfo()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Main_interface.this);
                builder.setTitle("提示");
                builder.setInverseBackgroundForced(true);
                builder.setMessage("注册信息未完善，是否立即完善？");
                builder.setIcon(R.drawable.logo);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra("userName", ((GlobalVeriable) getApplication()).getUserName());
                        startActivity(new Intent(Main_interface.this, PerfectInfo.class));
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        } else {
            ibLevelButton.setVisibility(View.GONE);
        }
        buttonchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((GlobalVeriable) getApplication()).getLogin()) {
                    startActivity(new Intent(Main_interface.this, ChatContactActivity.class));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Main_interface.this);
                    builder.setTitle("提示");
                    builder.setInverseBackgroundForced(true);
                    builder.setMessage("你还未登录，是否现在登陆");
                    builder.setIcon(R.drawable.logo);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Main_interface.this, Login.class));
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
        buttongame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_interface.this, GameRules.class));
            }
        });
        buttonforum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((GlobalVeriable) getApplication()).getLogin()) {
                    startActivity(new Intent(Main_interface.this, someWebview.class));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Main_interface.this);
                    builder.setTitle("提示");
                    builder.setInverseBackgroundForced(true);
                    builder.setMessage("你还未登录，是否现在登陆");
                    builder.setIcon(R.drawable.logo);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Main_interface.this, Login.class));
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();
                }
            }
        });
        buttonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_interface.this, MainBaidu.class));
            }
        });
        buttonrank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((GlobalVeriable) getApplication()).getLogin()) {
                    startActivity(new Intent(Main_interface.this, MainRank.class));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Main_interface.this);
                    builder.setTitle("提示");
                    builder.setInverseBackgroundForced(true);
                    builder.setMessage("你还未登录，是否现在登陆");
                    builder.setIcon(R.drawable.logo);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Main_interface.this, Login.class));
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();
                }
            }
        });
        buttonmy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_interface.this, My.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        starAnimTimer = new Timer();
        meteorAnimTimer = new Timer();
        blingStarTask = new TimerTask() {
            @Override
            public void run() {
                int index = (int) (Math.random() * blingBlingAnims.size());
                blingImage = blingBlingImages.get(index);
                blingAnim = blingBlingAnims.get(index);
                Main_interface.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (View.VISIBLE == blingImage.getVisibility()) {
                            blingImage.setVisibility(View.GONE);
                        } else {
                            blingImage.setVisibility(View.VISIBLE);
                        }
                        blingAnim.startNow();
                    }
                });
            }
        };
        blingMeteorTask = new TimerTask() {
            @Override
            public void run() {
                int index = (int) (Math.random() * blingMeteors.size());
                meteorImage = blingMeteors.get(index);
                meteorAnim = blingMeteorAnims.get(index);
                Main_interface.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        meteorAnim.reset();
                        meteorAnim.startNow();
                    }
                });
            }
        };
        starAnimTimer.schedule(blingStarTask, 1000, 1000);
        meteorAnimTimer.schedule(blingMeteorTask, 3000, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        starAnimTimer.cancel();
        meteorAnimTimer.cancel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    Intent intentSevice = new Intent();
                    intentSevice.setAction("CoreService");
                    intentSevice.setPackage(getPackageName());
                    startService(intentSevice);
                    stopService(intentSevice);
                    CloseActivity.exitClient(this);
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intentSevice = new Intent();
        intentSevice.setAction("CoreService");
        intentSevice.setPackage(getPackageName());
        startService(intentSevice);
        stopService(intentSevice);
        CloseActivity.exitClient(this);
    }

}