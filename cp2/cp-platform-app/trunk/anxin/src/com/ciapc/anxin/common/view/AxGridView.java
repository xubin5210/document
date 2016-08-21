package com.ciapc.anxin.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class AxGridView extends GridView{

	public AxGridView(Context context) {
		super(context);
	}
	

	public AxGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
	
}
