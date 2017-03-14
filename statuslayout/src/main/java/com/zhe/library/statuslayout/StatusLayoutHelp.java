package com.zhe.library.statuslayout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by zhe on 2017/3/11.
 */

public class StatusLayoutHelp implements View.OnClickListener {

    private final int TYPE_EMPTY = 0x666001;
    private final int TYPE_LOADING = 0x666002;
    private final int TYPE_ERROR = 0x666003;
    private final int TYPE_NET_ERROR = 0x666004;

    private final Context mContext;
    private final int mEmptyViewResId;
    private final int mLoadingViewResId;
    private final int mErrorViewResId;
    private final int mNetErrorViewResId;
    private final int mRetryActionId;
    private final View.OnClickListener mListener;
    private final int mImageResId;
    private final int mTittleResId;
    private final int mInfoResId;

    private SparseArray<View> mViewSparseArray;

    private View mView;

    public StatusLayoutHelp(Builder builder) {
        this.mContext = builder.mContext;
        this.mEmptyViewResId = builder.emptyViewResId;
        this.mLoadingViewResId = builder.loadingViewResId;
        this.mErrorViewResId = builder.errorViewResId;
        this.mNetErrorViewResId = builder.netErrorViewResId;
        this.mRetryActionId = builder.retryActionId;
        this.mListener = builder.listener;
        this.mImageResId = builder.imageResId;
        this.mTittleResId = builder.tittleResId;
        this.mInfoResId = builder.infoResId;
        this.mViewSparseArray = new SparseArray<>();
    }

    public View getEmptyView() {
        View view = mViewSparseArray.get(TYPE_EMPTY);
        if (view == null) {
            view = inflaterLayoutByType(TYPE_EMPTY);
            mViewSparseArray.put(TYPE_EMPTY, view);
        }
        return view;
    }

    public View getLoadingView() {
        View view = mViewSparseArray.get(TYPE_LOADING);
        if (view == null) {
            view = inflaterLayoutByType(TYPE_LOADING);
            mViewSparseArray.put(TYPE_LOADING, view);
        }
        return view;
    }

    public View getErrorView() {
        View view = mViewSparseArray.get(TYPE_ERROR);
        if (view == null) {
            view = inflaterLayoutByType(TYPE_ERROR);
            mViewSparseArray.put(TYPE_ERROR, view);
        }
        return view;
    }

    public View getNetErrorView() {
        View view = mViewSparseArray.get(TYPE_NET_ERROR);
        if (view == null) {
            view = inflaterLayoutByType(TYPE_NET_ERROR);
            mViewSparseArray.put(TYPE_NET_ERROR, view);
        }
        return view;
    }

    public AnimationDrawable getAnimationDrawable() {
        ImageView imageView = (ImageView) mView.findViewById(R.id.status_icon);
        if (imageView != null)
            return (AnimationDrawable) imageView.getDrawable();
        return null;
    }

    private View inflaterLayoutByType(int type) {
        int layoutId = 0;
        switch (type) {
            case TYPE_EMPTY:
                layoutId = mEmptyViewResId;
                break;
            case TYPE_LOADING:
                layoutId = mLoadingViewResId;
                break;
            case TYPE_ERROR:
                layoutId = mErrorViewResId;
                break;
            case TYPE_NET_ERROR:
                layoutId = mNetErrorViewResId;
                break;
        }
        if (layoutId == 0) return null;
        mView = LayoutInflater.from(mContext).inflate(layoutId, null);
        setView(mView);
        return mView;
    }

    private void setView(View view) {
        view.setBackgroundColor(mContext.getResources().getColor(android.R.color.background_light));
        view.setClickable(true);

        ImageView imageView = (ImageView) view.findViewById(R.id.status_drawable_icon);
        TextView tittleTv = (TextView) view.findViewById(R.id.status_string_tittle);
        TextView infoTv = (TextView) view.findViewById(R.id.status_string_info);
        View retryView = view.findViewById(mRetryActionId);

        if (imageView != null)
            imageView.setImageResource(mImageResId);

        if (tittleTv != null)
            tittleTv.setText(mContext.getText(mTittleResId));

        if (infoTv != null)
            infoTv.setText(mContext.getText(mInfoResId));

        if (retryView != null)
            retryView.setOnClickListener(mListener);
    }

    public static Builder init(Context context) {
        return new Builder(context);
    }

    @Override
    public void onClick(View v) {

    }

    public static final class Builder {
        private Context mContext;

        private int emptyViewResId;
        private int loadingViewResId;
        private int errorViewResId;
        private int netErrorViewResId;
        private int retryActionId;
        private View.OnClickListener listener;
        private int imageResId;
        private int tittleResId;
        private int infoResId;

        Builder(Context context) {
            this.mContext = context;
        }

        public Builder emptyDataView(@LayoutRes int emptyViewResId) {
            this.emptyViewResId = emptyViewResId;
            return this;
        }

        public Builder loadingView(@LayoutRes int loadingViewResId) {
            this.loadingViewResId = loadingViewResId;
            return this;
        }

        public Builder errorView(@LayoutRes int errorViewResId) {
            this.errorViewResId = errorViewResId;
            return this;
        }

        public Builder netWorkErrorView(@LayoutRes int netErrorViewResId) {
            this.netErrorViewResId = netErrorViewResId;
            return this;
        }

        public Builder retryActionId(@IdRes int retryActionId) {
            this.retryActionId = retryActionId;
            return this;
        }

        public Builder retryActionListener(View.OnClickListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder statusIcon(@DrawableRes int imageResId) {
            this.imageResId = imageResId;
            return this;
        }

        public Builder statusTittle(@StringRes int tittleResId) {
            this.tittleResId = tittleResId;
            return this;
        }

        public Builder statusInfomation(@StringRes int infoResId) {
            this.infoResId = infoResId;
            return this;
        }

        public StatusLayoutHelp builder() {
            return new StatusLayoutHelp(this);
        }
    }

}
