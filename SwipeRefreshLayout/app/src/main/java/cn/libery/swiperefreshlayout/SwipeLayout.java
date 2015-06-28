package cn.libery.swiperefreshlayout;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

/**
 * Created by SZQ on 2015/6/28.
 */
public class SwipeLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {


    private int mTouchSlop;

    private ListView mListView;


    private OnLoadListener mOnLoadListener;

    private View mListViewFooter;


    private int mYDown;

    private int mLastY;

    private boolean isLoading = false;


    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setFooterView(Context context, ListView mListView, int layoutId) {
        this.mListView = mListView;
        mListViewFooter = LayoutInflater.from(context).inflate(layoutId, null,
                false);
        mListView.addFooterView(mListViewFooter);
        mListView.setFooterDividersEnabled(false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mListView == null) {
            getListView();
        }
    }

    private void getListView() {
        int child = getChildCount();
        if (child > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                mListView.setOnScrollListener(this);
            }
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }


    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }


    private boolean isBottom() {

        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }
        return false;
    }


    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }


    private void loadData() {
        if (mOnLoadListener != null) {
            // 设置状态
            setLoading(true);
            //
            mOnLoadListener.onLoad();
        }
    }


    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            if (isRefreshing()) setRefreshing(false);
            if (mListView.getFooterViewsCount() == 0) {
                mListView.addFooterView(mListViewFooter);
                mListView.setSelection(mListView.getAdapter().getCount() - 1);
            } else {
                mListViewFooter.setVisibility(VISIBLE);
            }
        } else {
            if (mListView.getAdapter() instanceof HeaderViewListAdapter) {
                mListView.removeFooterView(mListViewFooter);
            } else {
                mListViewFooter.setVisibility(View.GONE);
            }
            mYDown = 0;
            mLastY = 0;
        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        if (canLoad()) {
            loadData();
        }
    }

    public interface OnLoadListener {
        void onLoad();
    }

}