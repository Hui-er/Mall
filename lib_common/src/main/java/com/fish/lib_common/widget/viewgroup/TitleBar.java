package com.fish.lib_common.widget.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fish.lib_common.R;
import com.fish.lib_common.util.DisplayUtils;

/**
 * Created by liu bin on 2019/10/22
 * <p>
 * Function---通用标题栏
 * 1.左边和右边可以同时设置文字和图片，中间标题固定文字
 * 2.如果左右同时设置文字和图片，文字会覆盖图片
 */
public class TitleBar extends ConstraintLayout {

    private TextView tv_left;
    private TextView tv_title;
    private TextView tv_right_one;
    private TextView tv_right_two;
    private String title; //标题
    private boolean isLeftText = false; //左边是否是文字

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_title_bar, this, true);
        tv_left = findViewById(R.id.tv_left);
        tv_title = findViewById(R.id.tv_title);
        tv_right_one = findViewById(R.id.tv_right_one);
        tv_right_two = findViewById(R.id.tv_right_two);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        //背景
        int bg = ta.getResourceId(R.styleable.TitleBar_bg, 0);

        //左边，可图片，可文字
        int leftIcon = ta.getResourceId(R.styleable.TitleBar_left_icon, 0);
        String leftText = ta.getString(R.styleable.TitleBar_left_text);
        Boolean isLeftShow = ta.getBoolean(R.styleable.TitleBar_show_left, true);

        //中间标题
        title = ta.getString(R.styleable.TitleBar_center);

        //右边第一个，可图片，可文字
        int rightOneIcon = ta.getResourceId(R.styleable.TitleBar_right_one_icon, 0);
        String rightOneText = ta.getString(R.styleable.TitleBar_right_one_text);

        //右边第二个，可图片，可文字
        int rightTwoIcon = ta.getResourceId(R.styleable.TitleBar_right_two_icon, 0);
        String rightTwoText = ta.getString(R.styleable.TitleBar_right_two_text);

        ta.recycle();

        //处理背景
        if (bg != 0) {
            setBackgroundResource(bg);
        } else {
            setBackgroundResource(R.drawable.bg_toolbar_white_bottom_divider);
        }

        //左边是图片，默认是返回键
        if (leftIcon != 0) {
            setLeftIcon(leftIcon);
        }
        //左边是文字
        if (leftText != null) {
            setLeftText(leftText);
        }
        //不显示返回键
        if (!isLeftShow) {
            tv_left.setVisibility(GONE);
        }
        //标题
        tv_title.setText(title);

        //右边第一个是是图片
        if (rightOneIcon != 0) {
            setRightOneIcon(rightOneIcon);
        }

        //右边第一个是文字
        if (rightOneText != null) {
            setRightOneText(rightOneText);
        }

        //右边第二个是是图片
        if (rightTwoIcon != 0) {
            setRightTwoIcon(rightTwoIcon);
        }

        //右边第二个是文字
        if (rightTwoText != null) {
            setRightTwoText(rightTwoText);
        }
        if (false) {
            post(() -> {
                int height = DisplayUtils.getStatusHeight(context);
                setPadding(getPaddingLeft(), height, getPaddingRight(), getPaddingBottom());
                ViewGroup.LayoutParams params = getLayoutParams();
                params.height = getHeight() + height;
                setLayoutParams(params);
            });
        }

    }

    /**
     * 左边监听
     */
    public void setLeftListener(final View.OnClickListener listener) {
        tv_left.setOnClickListener(listener);
    }

    /**
     * 右边第一个监听
     */
    public void setRightOneListener(final View.OnClickListener listener) {
        tv_right_one.setOnClickListener(listener);
    }

    /**
     * 右边第二个监听
     */
    public void setRightTwoListener(final View.OnClickListener listener) {
        tv_right_two.setOnClickListener(listener);
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        this.title = title;
        tv_title.setText(title);
        tv_title.setVisibility(VISIBLE);
    }

    /**
     * 设置标题
     */
    public void setTitle(@StringRes int res) {
        this.title = getContext().getString(res);
        tv_title.setText(title);
        tv_title.setVisibility(VISIBLE);
    }

    /**
     * 获取标题
     */
    public String getTitle() {
        return title;
    }


    /**
     * 设置左边的图片,文字置空
     */
    public void setLeftIcon(@DrawableRes int res) {
        tv_left.setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
        tv_left.setText("");
        tv_left.setVisibility(VISIBLE);
        isLeftText = false;
    }

    /**
     * 设置左边的文字,图片置空
     */
    public void setLeftText(String text) {
        tv_left.setText(text);
        tv_left.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tv_left.setVisibility(VISIBLE);
        isLeftText = true;
    }

    /**
     * 设置左边的文字,图片置空
     */
    public void setLeftText(@StringRes int res) {
        tv_left.setText(res);
        tv_left.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tv_left.setVisibility(VISIBLE);
        isLeftText = true;
    }

    /**
     * 设置左边的可见性
     */
    public void setLeftVisible(boolean flag) {
        tv_left.setVisibility(flag ? VISIBLE : INVISIBLE);
        isLeftText = false;
    }

    /**
     * 左边是否是文字
     */
    public boolean isLeftTextVisible() {
        return isLeftText;
    }


    /**
     * 设置右边第一个的图片,文字置空
     */
    public void setRightOneIcon(@DrawableRes int res) {
        tv_right_one.setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
        tv_right_one.setText("");
        tv_right_one.setVisibility(VISIBLE);
    }

    /**
     * 设置右边第一个的文字,图片置空
     */
    public void setRightOneText(String text) {
        tv_right_one.setText(text);
        tv_right_one.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    /**
     * 设置右边第一个的文字,图片置空
     */
    public void setRightOneText(@StringRes int res) {
        tv_right_one.setText(res);
        tv_right_one.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    /**
     * 设置右边第一个的可见性
     */
    public void setRightOneVisible(boolean flag) {
        tv_right_one.setVisibility(flag ? VISIBLE : INVISIBLE);
    }

    /**
     * 设置右边第二个的图片,文字置空
     */
    public void setRightTwoIcon(@DrawableRes int res) {
        tv_right_two.setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
        tv_right_two.setText("");
    }

    /**
     * 设置右边第二个的文字
     */
    public void setRightTwoText(String text) {
        tv_right_two.setText(text);
        tv_right_two.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    /**
     * 设置右边第二个的文字
     */
    public void setRightTwoText(@StringRes int res) {
        tv_right_two.setText(res);
        tv_right_two.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    /**
     * 设置右边第二个的可见性
     */
    public void setRightTwoVisible(boolean flag) {
        tv_right_two.setVisibility(flag ? VISIBLE : INVISIBLE);
    }


    public TextView getLeftView() {
        return tv_left;
    }


}
