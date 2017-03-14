package com.zhe.library.statuslayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * Created by zhe on 2017/3/11.
 */

public class StatusLayout extends FrameLayout {

    private StatusLayoutHelp mLayoutHelp;
    private View currentStateView;
    private OnClickListener mListener;
    private AnimationDrawable drawable;

    public StatusLayout(@NonNull Context context) {
        this(context, null);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
        initHelp(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void initHelp(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mLayoutHelp = StatusLayoutHelp.init(getContext())
                .loadingView(R.layout.layout_status_loading)
                .emptyDataView(R.layout.layout_status_empty)
                .errorView(R.layout.layout_status_error)
                .netWorkErrorView(R.layout.layout_status_error)
                .statusIcon(R.drawable.anim_loading_view)
                .statusTittle(R.string.status_layout_tittle)
                .statusInfomation(R.string.status_layout_info)
                .retryActionId(R.id.status_action_retry)
                .retryActionListener(getListener())
                .builder();
        this.setLayoutHelp(mLayoutHelp);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (!(getLayoutParams() instanceof LayoutParams)) {
            LayoutParams layoutParams = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    Gravity.CENTER);
            setLayoutParams(layoutParams);
        }
    }

    private OnClickListener getListener() {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onClick(v);
            }
        };
    }

    public void setTargetView(View targetView) {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(targetView);
        }

        if (targetView == null) {
            return;
        }

        if (targetView.getParent() instanceof ViewGroup) {
            ViewGroup parentContainer = (ViewGroup) targetView.getParent();
            int groupIndex = parentContainer.indexOfChild(targetView);
            parentContainer.removeView(targetView);

            ViewGroup.LayoutParams params = targetView.getLayoutParams();
            this.setLayoutParams(params);

            targetView.setLayoutParams(new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER));

            parentContainer.addView(this, groupIndex, params);
            this.addView(targetView);
        } else {
            throw new RuntimeException("You Need A Useful Group ! ! !");
        }
    }

    public void setLayoutHelp(StatusLayoutHelp layoutHelp) {
        this.mLayoutHelp = layoutHelp;
    }

    public void setOnRetryClickListener(OnClickListener listener) {
        this.mListener = listener;
    }

    public void showContentView() {
        if (mLayoutHelp == null) return;
        this.removeView(currentStateView);
        if (drawable != null)
            drawable.stop();
    }

    public void showEmptyView() {
        if (mLayoutHelp == null) return;
        this.removeView(currentStateView);
        currentStateView = mLayoutHelp.getEmptyView();
        this.addView(currentStateView);
    }

    public void showLoadingView() {
        if (mLayoutHelp == null) return;
        this.removeView(currentStateView);
        currentStateView = mLayoutHelp.getLoadingView();
        drawable = mLayoutHelp.getAnimationDrawable();
        if (drawable != null)
            drawable.start();
        this.addView(currentStateView);
    }

    public void showErrorView() {
        if (mLayoutHelp == null) return;
        this.removeView(currentStateView);
        currentStateView = mLayoutHelp.getErrorView();
        this.addView(currentStateView);
    }

    public void showNetErrorView() {
        if (mLayoutHelp == null) return;
        this.removeView(currentStateView);
        currentStateView = mLayoutHelp.getNetErrorView();
        this.addView(currentStateView);
    }

}
