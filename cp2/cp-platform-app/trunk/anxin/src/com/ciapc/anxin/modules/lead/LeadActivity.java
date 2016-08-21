/**    
 * @author zhuwt 
 * @Description: TODO(用一句话描述该文件做什么)   
 * @Package com.ciapc.anxin.modules.leaders   
 * @Title: LeadActivity.java   
 * @date 2015年6月2日 上午9:57:17   
 * @version V1.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
package com.ciapc.anxin.modules.lead;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.modules.leaders.LeaderSharedPreferences;
import com.ciapc.anxin.modules.login.LoginActivity;

/**
 * @author zhuwt
 * @Description: 引导页
 * @ClassName: LeadActivity.java
 * @date 2015年6月2日 上午9:57:17
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class LeadActivity extends BaseActivity implements OnClickListener,
		OnPageChangeListener {

	private final String TAG = "LeadActivity";

	private Context mContext;

	private ViewPager mPager;

	private List<View> list;

	private LeadAdapter mAdapter;

	// 引导的图片
	private final int[] guides = { R.drawable.test2, R.drawable.test3,
			R.drawable.test4 };

	// 当前选中的位置
	private int index;

	// 立即体验
	private Button experience;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lead_layout);
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		mContext = this;
		initView();
		init(mParams);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化应道数据
	 * @Date:2015年6月2日上午10:52:34
	 * @param params
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void init(LinearLayout.LayoutParams params) {
		list = new ArrayList<View>();
		// 初始化引导图片列表
		for (int i = 0; i < guides.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(params);
			iv.setImageResource(guides[i]);
			list.add(iv);
		}

		// 初始化Adapter
		mAdapter = new LeadAdapter(list);
		mPager.setAdapter(mAdapter);
		// 绑定回调
		mPager.setOnPageChangeListener(this);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 初始化控件
	 * @Date:2015年6月2日上午10:54:45
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		mPager = (ViewPager) findViewById(R.id.lead_viewpage);
		experience = (Button) findViewById(R.id.lead_experience);
		experience.setOnClickListener(this);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 设置当前页
	 * @Date:2015年6月2日上午10:56:26
	 * @param position
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= guides.length) {
			return;
		}
		mPager.setCurrentItem(position);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		if (arg0 >= guides.length - 1) {
			experience.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == experience.getId()) {
			LeaderSharedPreferences.setInitialize(mContext, true);
			startActivity(new Intent(mContext, LoginActivity.class));
			finish();
			return;
		}
		int position = (Integer) arg0.getTag();
		setCurView(position);
	}
}
