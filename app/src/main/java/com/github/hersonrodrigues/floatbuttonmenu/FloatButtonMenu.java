package com.github.hersonrodrigues.floatbuttonmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Herson Rodrigues on 21/08/17.
 */
public class FloatButtonMenu extends LinearLayout {
    private int mRight, mLeft, mTop, mBottom;
    private int mIconClosed, mIconOpened;
    private Context mContext;
    private boolean mOpen;
    private View mContainer;
    private RelativeLayout mBoxMenu;
    private LinearLayout mMenu, mMenuItem;
    private FloatingActionButton mFab;

    public FloatButtonMenu(Context context) {
        super(context);
        init(context);
    }

    public FloatButtonMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloatButtonMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatButtonMenu, defStyle, 0);

        mIconClosed = a.getResourceId(R.styleable.FloatButtonMenu_iconMenuClosed, R.mipmap.ic_launcher);
        mIconOpened = a.getResourceId(R.styleable.FloatButtonMenu_iconMenuOpened, R.mipmap.ic_launcher);

        a.recycle();
    }

    public void setIconMenuClosed(int icone) {
        mIconClosed = icone;
        mFab.setImageResource(mIconClosed);
    }

    public void setIconMenuOpened(int icone) {
        mIconOpened = icone;
    }

    private void init(Context context) {
        View.inflate(context, R.layout.float_button_menu, this);
        mContext = getContext();
        mContainer = this.findViewById(R.id.container);
        mBoxMenu = (RelativeLayout) this.findViewById(R.id.container_menu_box);
        mBoxMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        mFab = (FloatingActionButton) this.findViewById(R.id.fab);
        mFab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu();
            }
        });

        mMenu = (LinearLayout) this.findViewById(R.id.container_menu);
        mMenuItem = (LinearLayout) this.findViewById(R.id.container_menu_item);

        setIconMenuClosed(mIconClosed);
        setIconMenuOpened(mIconOpened);

        this.removeAllViews();
        this.addView(mContainer);
    }

    private void toggleMenu() {
        if (mOpen) {
            close();
        } else {
            open();
        }
    }

    public void addMenuItem(final MenuItem menuItem) {
        LinearLayout menView = (LinearLayout) View.inflate(getContext(), R.layout.flaot_button_menu_item, null);
        View containerView = menView.findViewById(R.id.container);
        containerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu();
                v.setOnClickListener(menuItem.getClick());
                v.callOnClick();
            }
        });
        ImageView iconeView = (ImageView) menView.findViewById(R.id.icon);
        iconeView.setImageDrawable(menuItem.getIcon());
        TextView textView = (TextView) menView.findViewById(R.id.title);
        textView.setText(menuItem.getTitle());
        mMenuItem.addView(menView);
    }


    public void open() {
        mOpen = true;

        Animation animFab = AnimationUtils.loadAnimation(mContext, R.anim.rotation);
        animFab.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFab.setImageResource(mIconOpened);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mFab.setAnimation(animFab);
        animFab.start();

        Animation animMenu = AnimationUtils.loadAnimation(mContext, R.anim.bottom_up);
        animMenu.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mMenu.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mMenu.startAnimation(animMenu);

        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mBoxMenu.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBoxMenu.startAnimation(anim);
    }

    public void close() {
        mOpen = false;

        Animation animFab = AnimationUtils.loadAnimation(mContext, R.anim.rotation);
        animFab.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFab.setImageResource(mIconClosed);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mFab.setAnimation(animFab);
        animFab.start();

        Animation animMenu = AnimationUtils.loadAnimation(mContext, R.anim.bottom_down);
        animMenu.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mMenu.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mMenu.setAnimation(animMenu);
        animMenu.start();

        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mBoxMenu.setOnClickListener(null);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBoxMenu.setVisibility(GONE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBoxMenu.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                close();
                            }
                        });
                    }
                }, 300);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBoxMenu.startAnimation(anim);
    }

    public boolean isOpened() {
        return mOpen;
    }

    public static class MenuItem {
        private String mTitle;
        private Drawable mIcon;
        private OnClickListener mClick;

        public MenuItem(Drawable icon, String title, OnClickListener onclick) {
            this.mIcon = icon;
            this.mTitle = title;
            this.mClick = onclick;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            this.mTitle = title;
        }

        public Drawable getIcon() {
            return mIcon;
        }

        public void setIcon(Drawable icon) {
            this.mIcon = icon;
        }

        public OnClickListener getClick() {
            return mClick;
        }

        public void setClick(OnClickListener click) {
            this.mClick = click;
        }
    }
}