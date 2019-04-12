package com.example.scrollingactivitydemo

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout


const val TAG = "AppBarBehavior"

/**
 * Created by Seaman on 2019/4/3.
 * Bangggood Ltd
 */
class AppBarBehavior(context: Context?, attrs: AttributeSet?) :
    AppBarLayout.Behavior(context, attrs) {

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        ev: MotionEvent
    ): Boolean {
        Log.v(TAG,"onInterceptTouchEvent ")
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        Log.v(TAG,"onNestedPreScroll ")
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }


    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        Log.v(TAG,"onNestedScroll ")
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed
        )
    }

    override fun onStartNestedScroll(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        Log.v(TAG,"onStartNestedScroll ")
        return super.onStartNestedScroll(
            parent,
            child,
            directTargetChild,
            target,
            nestedScrollAxes,
            type
        )
    }
}