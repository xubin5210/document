package com.ciapc.anxin.modules.setting;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/**
 * 
 * @author zhoumc
 * @Description: 设置新消息提醒主页面
 * @ClassName: SetNewInfoActivity.java
 * @date 2015-6-9 下午2:20:56
 * @说明 代码版权归 杭州安存网络科技有限公司 所有
 */
public class SetNewInfoActivity extends BaseActivity {
	// 设置日志文件
	public static final String TAG = "SetNewInfoActivity";

	// 自己在values下面array中定义一个名称
	private PubTitle pubTitle;

	private Context mContext;

	private CheckBox ckNewCardId, ckSound, ckShake;
	
	private LinearLayout content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_new_info_layout);
		mContext = this;
		initView();
		initClick();
		initData();

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化数据
	 * @Date:2015-6-18上午11:43:35
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initData() {
		// 新名片通知
		if (AXSharedPreferences.getNewInfoRemind(mContext)) {
			ckNewCardId.setChecked(true);
			content.setVisibility(View.VISIBLE);
		} else {
			ckNewCardId.setChecked(false);
			content.setVisibility(View.GONE);
		}

		// 是否开启声音
		if (AXSharedPreferences.getSound(mContext)) {
			ckSound.setChecked(true);
		} else {
			ckSound.setChecked(false);
		}

		// 是否开启震动
		if (AXSharedPreferences.getShake(mContext)) {
			ckShake.setChecked(true);
		} else {
			ckShake.setChecked(false);
		}

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化控件
	 * @Date:2015-6-18上午11:38:36
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initView() {
		// 标题
		pubTitle = (PubTitle) findViewById(R.id.set_newinfo_title);
		// 新名片
		ckNewCardId = (CheckBox) findViewById(R.id.ck_newcardid);
		// 声音
		ckSound = (CheckBox) findViewById(R.id.ck_sound);
		// 震动
		ckShake = (CheckBox) findViewById(R.id.ck_shake);
		
		content = (LinearLayout) findViewById(R.id.set_content);

	}

	/**
	 * @Auther: zhoumc
	 * @Description: 初始化监听事件
	 * @Date:2015-6-18上午11:39:00
	 * @return void
	 * @说明 代码版权归 杭州安存网络科技有限公司 所有
	 */
	private void initClick() {
		// 标题栏监听事件
		pubTitle.setShowLeftOrRight(true, false, false)
				.setOnTitleClickListener(new OnTitleClickListener() {

					@Override
					public void onRightClick() {

					}

					@Override
					public void onLeftClick() {
						finish();
					}
				});

		// 新名片通知
		ckNewCardId.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ckNewCardId.isChecked()) {
					AXSharedPreferences.setNewInfoRemind(mContext, true);
					ckNewCardId.setChecked(true);
					content.setVisibility(View.VISIBLE);
					return;
				} else {
					AXSharedPreferences.setNewInfoRemind(mContext,false);
					ckNewCardId.setChecked(false);
					content.setVisibility(View.GONE);
				}
			}
		});

		// 声音
		ckSound.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ckSound.isChecked()) {
					AXSharedPreferences.setSound(mContext, true);
					ckSound.setChecked(true);
					return;
				} else {
					AXSharedPreferences.setSound(mContext, false);
					ckSound.setChecked(false);
				}

			}
		});

		// 震动
		ckShake.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ckShake.isChecked()) {
					AXSharedPreferences.setShake(mContext, true);
					ckShake.setChecked(true);
					return;
				} else {
					AXSharedPreferences.setShake(mContext, false);
					ckShake.setChecked(false);
				}

			}
		});

	}

}
