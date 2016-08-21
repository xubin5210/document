package com.ciapc.anxin.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 
 * @author zhuwt
 * @Description: 嵌套在ScrollView的listview
 * @ClassName: AxListView.java
 * @date 2015年6月9日 下午10:08:48
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class AxListView extends ListView {

	public AxListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AxListView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}

}
