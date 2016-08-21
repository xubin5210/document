package com.ciapc.anxin.modules.home;


import com.ciapc.anxin.common.view.DragLayout;
import com.ciapc.anxin.common.view.DragLayout.Status;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Activity内容外层布局，协助菜单动作反应
 * 
 * @author zihao
 * 
 */
public class HomeRelativeLayout extends LinearLayout {
	
	private DragLayout dl;
	
	public HomeRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HomeRelativeLayout(Context context) {
		super(context);
	}

	public void setDragLayout(DragLayout dl) {
		this.dl = dl;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (dl.getStatus() != Status.Close) {
			return true;
		}
		return super.onInterceptTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (dl.getStatus() != Status.Close) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				dl.close();
			}
			return true;
		}
		return super.onTouchEvent(event);
	}

}
