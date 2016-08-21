package com.ciapc.anxin.modules.statistics;

import java.util.HashMap;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ciapc.anxin.R;
import com.ciapc.anxin.common.AXSharedPreferences;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.InterfaceConstants;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.ciapc.anxin.modules.exchange.ExchangeCardActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.master.util.common.GsonUtils;
import com.master.util.common.StringUtils;
import com.master.util.http.MasterHttpClient;

/**
 * @author zhuwt
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @ClassName: StatisticsActivity.java
 * @date 2015年7月6日 上午10:22:15
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class StatisticsActivity extends BaseActivity {

	private final String TAG = "StatisticsActivity";

	private Context mContext;

	private ImageView mBar, companyBar, jobBar;

	private PubTitle pubTitle;
	private String cardNum, entNum, postNum;
	private TextView a, b, c, top;
	private LinearLayout left, right, exchange;
	private Button exchangeBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics_layout);
		mContext = this;
		initView();
		initClick();
		initData();
	}

	private void initView() {
		mBar = (ImageView) findViewById(R.id.card_progressbar);
		companyBar = (ImageView) findViewById(R.id.company_progressbar);
		jobBar = (ImageView) findViewById(R.id.job_progressbar);
		pubTitle = (PubTitle) findViewById(R.id.statistics_title);
		a = (TextView) findViewById(R.id.card_num);
		b = (TextView) findViewById(R.id.post_num);
		c = (TextView) findViewById(R.id.ent_num);
		left = (LinearLayout) findViewById(R.id.left);
		right = (LinearLayout) findViewById(R.id.right);
		top = (TextView) findViewById(R.id.top);
		exchange = (LinearLayout) findViewById(R.id.exchange);
		exchangeBtn = (Button) findViewById(R.id.exchange_card);
	}

	private void initClick() {
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

		exchangeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext, ExchangeCardActivity.class));
			}
		});
	}

	private void initData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tokenid", AXSharedPreferences.getTokenId(mContext));
		map.put(InterfaceConstants.INTERFACE_NAME,
				InterfaceConstants.CONTACT_CODE);
		MasterHttpClient.postForJava(mContext, map,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String result = new String(arg2);
						if (StringUtils.isNotEmpty(result)) {
							if (GlobalConstants.isDebug) {
								Log.i(TAG, "code = " + arg0 + " , msg = "
										+ new String(arg2));
							}
							// 返回成功
							if (GlobalConstants.HTTP_SUCCESS_CODE
									.equals(GsonUtils.getJsonValue(result,
											GlobalConstants.HTTP_CODE))) {
								String data = GsonUtils.getJsonValue(result,
										"data");
								cardNum = GsonUtils.getJsonValue(data,
										"cardNum");
								postNum = GsonUtils.getJsonValue(data,
										"positionNum");
								entNum = GsonUtils.getJsonValue(data, "entNum");
								if (StringUtils.isNotEmpty(postNum) && !"0".equals(postNum)) {
									right.setVisibility(View.VISIBLE);
									b.setText(postNum);
								} else {
									right.setVisibility(View.GONE);
								}
								if (StringUtils.isNotEmpty(entNum) && !"0".equals(entNum)) {
									left.setVisibility(View.VISIBLE);
									c.setText(entNum);
								} else {
									left.setVisibility(View.GONE);
								}

								if (StringUtils.isNotEmpty(cardNum) && !"0".equals(cardNum)) {
									a.setText(cardNum);
									mBar.setImageResource(R.drawable.card_statistics);
									exchange.setVisibility(View.GONE);
									top.setVisibility(View.VISIBLE);
								} else {
									mBar.setImageResource(R.drawable.no_card_statistics);
									exchange.setVisibility(View.VISIBLE);
									top.setVisibility(View.GONE);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {

					}
				});
		RotateAnimation mAnimation = new RotateAnimation(0f, 270f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		RotateAnimation mAnimation2 = new RotateAnimation(0f, 270f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		RotateAnimation mAnimation3 = new RotateAnimation(0f, 270f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mAnimation.setFillAfter(true);
		mAnimation.setDuration(1500);
		mAnimation.setFillEnabled(true);
		mAnimation2.setFillAfter(true);
		mAnimation2.setDuration(1500);
		mAnimation2.setFillEnabled(true);
		mAnimation3.setFillAfter(true);
		mAnimation3.setDuration(1500);
		mAnimation3.setFillEnabled(true);
		mBar.setAnimation(mAnimation);
		companyBar.setAnimation(mAnimation2);
		jobBar.setAnimation(mAnimation3);
		mAnimation.startNow();
		mAnimation2.startNow();
		mAnimation3.startNow();
	}
}
