package com.ciapc.anxin.modules.lead;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author zhuwt
 * @Description: 引导页
 * @ClassName: LeadAdapter.java
 * @date 2015年6月2日 上午10:38:18
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class LeadAdapter extends PagerAdapter {

	// 引导的界面
	private List<View> list;

	public LeadAdapter(List<View> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		if (null != list) {
			return list.size();
		}
		return 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	// 销毁界面
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(list.get(position));
	}

	// 初始化界面
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(list.get(position), 0);
		return list.get(position);
	}

}
