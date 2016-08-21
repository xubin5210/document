package com.ciapc.anxin.modules.details;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.ciapc.anxin.R;
import com.ciapc.anxin.common.BaseActivity;
import com.ciapc.anxin.common.GlobalConstants;
import com.ciapc.anxin.common.view.PubTitle;
import com.ciapc.anxin.common.view.RoundView;
import com.ciapc.anxin.common.view.PubTitle.OnTitleClickListener;
import com.master.util.common.DialogUtil;
import com.master.util.common.QRCodeUtils;
import com.master.util.common.StringUtils;
import com.master.util.common.SystemUtils;
import com.master.util.image.MasterImg;

/**
 * @author zhuwt
 * @Description: 二维码详情
 * @ClassName: QrDetailsActivity.java
 * @date 2015年6月29日 下午2:44:20
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class QrDetailsActivity extends BaseActivity {

	private Context mContext;

	private PubTitle pubTitle;

	private ImageView qr;

	private String cardId;

	// 二维码显示的宽高
	private int qrW;

	private RoundView head;
	private TextView nameT, card;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qr_details);
		mContext = this;
		initView();
		initClick();
		mContext = this;
		// 获取屏幕宽度和高度 用于二维码显示
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		float scale = mContext.getResources().getDisplayMetrics().density;  
		qrW = (int) (290.0 * scale + 0.5f);
		// initData();
		if (!SystemUtils.isNetWorkConnected(mContext)) {
			DialogUtil.showSystemToast(mContext, "网络异常 请检查您的网络状态");
			return;
		}
		mHandler.sendEmptyMessage(1);
	}

	private void initData() {
		// 获取屏幕宽度和高度 用于二维码显示
		String cardId = getIntent().getStringExtra("cardId");
		String url = GlobalConstants.SHARE+cardId;
		String name = getIntent().getStringExtra("name");
		String icon = getIntent().getStringExtra("icon");
		card.setText("名片号:" + cardId);
		if(StringUtils.isNotEmpty(name)){
			nameT.setText(name);
		}else{
			nameT.setText("无名氏");
		}
		Bitmap content = null;
		if (StringUtils.isNotEmpty(icon)) {
			AQuery a = new AQuery(mContext);
			content = a.getCachedImage(icon);
			head.setTag(icon);
			MasterImg.getInstance(mContext).loadImg(
					icon, head, true,
					true, 0, R.drawable.toux_default);
		}
		if (null == content) {
			content = BitmapFactory.decodeResource(getResources(),
					R.drawable.toux_default);
		}
		Bitmap mBitmap = QRCodeUtils
				.createQRCode(url, content, qrW, qrW,30);
		qr.setImageBitmap(mBitmap);
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
	}

	private void initView() {
		pubTitle = (PubTitle) findViewById(R.id.qr_detalis_title);
		qr = (ImageView) findViewById(R.id.qr_detail_content);
		head = (RoundView) findViewById(R.id.home_person_head);
		card = (TextView) findViewById(R.id.visiting_card);
		nameT = (TextView) findViewById(R.id.nick_name);

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				runOnUiThread(new Runnable() {
					public void run() {
						initData();
					}
				});
				break;

			default:
				break;
			}
		};
	};
}
