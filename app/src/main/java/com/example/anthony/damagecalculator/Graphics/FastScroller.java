package com.example.anthony.damagecalculator.Graphics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.example.anthony.damagecalculator.R;


/**
 * Created by DragonLotus on 5/14/2016.
 */
public class FastScroller extends LinearLayout {
    private static final int HANDLE_ANIMATION_DURATION = 100;
    private static final int HANDLE_HIDE_DELAY = 1000;
    private static final int TRACK_SNAP_RANGE = 0;
    private static final String SCALE_X = "scaleX";
    private static final String SCALE_Y = "scaleY";
    private static final String ALPHA = "alpha";

    //    private final HandleHider handleHider = new HandleHider();
    private final ScrollListener scrollListener = new ScrollListener();
    private RecyclerView recyclerView;

    private AnimatorSet currentAnimator = null;

    private View track;
    //    private View handle;
    private int height;

    public FastScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public FastScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        setOrientation(HORIZONTAL);
        setClipChildren(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.fastscroller, this);
        track = findViewById(R.id.thumb_track);
//        handle = findViewById(R.id.thumb_handle);
    }

    public void resizeScrollBar(int listType) {
        Log.d("FastScrollerTag", "Track Height/Width before is " + track.getMeasuredHeight() + "/" + track.getMeasuredWidth() +" "+ recyclerView.getAdapter().getItemCount());
        if(recyclerView.getAdapter().getItemCount()!=0 || recyclerView != null){
            float displayedItems = height/ TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getContext().getResources().getDisplayMetrics());
            int itemCount = recyclerView.getAdapter().getItemCount();
            float proportion = displayedItems / itemCount;

            LinearLayout.LayoutParams z = (LinearLayout.LayoutParams) track.getLayoutParams();
            Log.d("FastScrollerTag", "proportion/scrollbar height is " + proportion + "/" + Math.round(proportion * height) + " itemCount is: " + itemCount + " displayedItems is: " + displayedItems);
            if(proportion >= 1){
                track.setVisibility(INVISIBLE);
            } else if (Math.round(proportion * height) <= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getContext().getResources().getDisplayMetrics())){
                track.setVisibility(VISIBLE);
                z.height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getContext().getResources().getDisplayMetrics());
            } else {
                track.setVisibility(VISIBLE);
                z.height = Math.round(proportion * height);
            }
        }
    }

//    private void showHandle(){
//        AnimatorSet animatorSet = new AnimatorSet();
//        handle.setPivotX(handle.getWidth());
//        handle.setPivotY(handle.getHeight());
//        handle.setVisibility(VISIBLE);
//        Animator growerX = ObjectAnimator.ofFloat(handle, SCALE_X, 1f, 0f).setDuration(HANDLE_ANIMATION_DURATION);
//        Animator growerY = ObjectAnimator.ofFloat(handle, SCALE_Y, 1f, 0f).setDuration(HANDLE_ANIMATION_DURATION);
//        Animator alpha = ObjectAnimator.ofFloat(handle, ALPHA, 0f, 1f).setDuration(HANDLE_ANIMATION_DURATION);
//        animatorSet.playTogether(growerX, growerY, alpha);
//        animatorSet.start();
//    }
//
//    private void hideHandle(){
//        currentAnimator = new AnimatorSet();
//        handle.setPivotX(handle.getWidth());
//        handle.setPivotY(handle.getHeight());
//        Animator shrinkerX = ObjectAnimator.ofFloat(handle, SCALE_X, 1f, 0f).setDuration(HANDLE_ANIMATION_DURATION);
//        Animator shrinkerY = ObjectAnimator.ofFloat(handle, SCALE_Y, 1f, 0f).setDuration(HANDLE_ANIMATION_DURATION);
//        Animator alpha = ObjectAnimator.ofFloat(handle, ALPHA, 1f, 0f).setDuration(HANDLE_ANIMATION_DURATION);
//        currentAnimator.playTogether(shrinkerX, shrinkerY, alpha);
//        currentAnimator.addListener(animatorListenerAdapter);
//        currentAnimator.start();
//    }
//
//    private AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
//        @Override
//        public void onAnimationEnd(Animator animation) {
//            super.onAnimationEnd(animation);
//            handle.setVisibility(INVISIBLE);
//            currentAnimator = null;
//        }
//
//        @Override
//        public void onAnimationCancel(Animator animation) {
//            super.onAnimationCancel(animation);
//            handle.setVisibility(INVISIBLE);
//            currentAnimator = null;
//        }
//    };

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
    }

    private void setPosition(float y) {
        float position = y / height;
        int trackHeight = track.getHeight();
        track.setY(getValueInRange(0, height - trackHeight, (int) ((height - trackHeight) * position)));
//        int handleHeight = handle.getHeight();
//        handle.setY(getValueInRange(0,height-handleHeight, (int)((height - handleHeight) * position)));
    }

    private int getValueInRange(int min, int max, int value) {
        int minimum = Math.max(min, value);
        return Math.min(minimum, max);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.setOnScrollListener(scrollListener);
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            View firstVisibleView = recyclerView.getChildAt(0);
            int firstVisiblePosition = recyclerView.getChildPosition(firstVisibleView);
            int visibleRange = recyclerView.getChildCount();
            Log.d("FastScrollerTag", "getChildCount " + recyclerView.getChildCount());
            int lastVisiblePosition = firstVisiblePosition + visibleRange;
            int itemCount = recyclerView.getAdapter().getItemCount();
            int position;
            if (firstVisiblePosition == 0) {
                position = 0;
            } else if (lastVisiblePosition == itemCount - 1) {
                position = itemCount - 1;
            } else {
                position = firstVisiblePosition;
            }
            float proportion = (float) position / (float) itemCount;
            setPosition(height * proportion);
//            super.onScrolled(recyclerView, dx, dy);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            setPosition(event.getY());
            if (currentAnimator != null) {
                currentAnimator.cancel();
            }
//            getHandler().removeCallbacks(handleHider);
//            if(handle.getVisibility() == INVISIBLE){
//                showHandle();
//            }
            setRecyclerViewPosition(event.getY());
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
//            getHandler().postDelayed(handleHider, HANDLE_HIDE_DELAY);
        }
        return super.onTouchEvent(event);
    }
//
//    private class HandleHider implements Runnable {
//        @Override
//        public void run() {
//            hideHandle();
//        }
//    }

    private void setRecyclerViewPosition(float y) {
        if (recyclerView != null) {
            int itemCount = recyclerView.getAdapter().getItemCount();
            float proportion;
            if (track.getY() == 0) {
                proportion = 0f;
            } else if (track.getY() + track.getHeight() >= height - TRACK_SNAP_RANGE) {
                proportion = 1f;
            } else {
                proportion = y / (float) height;
            }
            int targetPos = getValueInRange(0, itemCount - 1, (int) (proportion * (float) itemCount));
            recyclerView.scrollToPosition(targetPos);
        }
    }
}
