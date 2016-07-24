package com.example.anthony.damagecalculator.Graphics;

import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


import com.example.anthony.damagecalculator.R;


/**
 * Created by DragonLotus on 5/14/2016.
 */
public class FastScroller extends LinearLayout {
    public static final int SAVE_MONSTER_LIST = 0;
    public static final int BASE_MONSTER_LIST = 1;
    public static final int TEAM_LIST = 1;
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

    private View handle;
    private float previousY = 0;

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
        handle = findViewById(R.id.thumb_track);
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
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.addOnScrollListener(scrollListener);
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_MOVE){
            recyclerView.stopScroll();
            setPosition(event.getY());
            Log.d("FastScrollerTag", "getY is: " + event.getY() + " handle getY is: " + handle.getY());
            return true;
        } else if (event.getActionMasked() == MotionEvent.ACTION_UP){
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void setPosition(float y){
        float scrollHeight = recyclerView.getMeasuredHeight();
        float offset = handle.getHeight() / 2;
        float deltaY = y-previousY;
        float deltaYRatio = deltaY/scrollHeight;
        if(y <= offset){
            handle.setY(0);
        } else if(y>=scrollHeight-offset) {
            handle.setY(scrollHeight - handle.getHeight());
        } else {
            handle.setY(y - offset);
        }
        Log.d("FastScrollerTag", "handle Height: " + handle.getHeight() + " deltaYRatio: " + deltaYRatio + " deltaY: " + deltaY + " previousY: " + previousY + " handle getY is: " + handle.getY());

//        recyclerView.scrollBy(0, (int)(deltaYRatio*recyclerView.computeVerticalScrollRange()));
        previousY = y;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d("FastScrollerTag", "onLayout: "+ changed + " " + l + " " + t + " " + r + " " + b);

        int scrollOffset = recyclerView.computeVerticalScrollOffset();
        int verticalScrollRange = recyclerView.computeVerticalScrollRange() + recyclerView.getPaddingBottom();

        int trackHeight = recyclerView.getMeasuredHeight();
        Log.d("FastScrollerTag", "VerticalScrollOffset: " + scrollOffset + " verticalScrollRange: " + verticalScrollRange + " trackHeight: " + trackHeight);
        Log.d("FastScrollerTag", "getMeasuredHeight: " + trackHeight);
        float ratio = (float) scrollOffset/(verticalScrollRange - trackHeight);
        int resizedHandleHeight = (int)((float)trackHeight / verticalScrollRange * trackHeight);
        if(resizedHandleHeight < convertDpToPx(24)){
            resizedHandleHeight = convertDpToPx(24);
        } else if(resizedHandleHeight > trackHeight){
            handle.setVisibility(INVISIBLE);
            return;
        }
        if(handle.getVisibility()==INVISIBLE){
            handle.setVisibility(VISIBLE);
        }
        float y = ratio * (trackHeight - resizedHandleHeight);
        handle.layout(handle.getLeft(), (int) y, handle.getRight(), (int) y + resizedHandleHeight);
    }

    private int convertDpToPx(float dp){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }
}
