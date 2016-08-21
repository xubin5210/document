package com.ciapc.anxin.common.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import com.ciapc.anxin.R;
import com.ciapc.anxin.utils.OptAnimationLoader;

public class AxShareDialog extends Dialog implements OnClickListener {

	private Context mContext;

	// 动画
	private AnimationSet mModalInAnim;
	private AnimationSet mModalOutAnim;
	private View mDialogView;
	private Animation mOverlayOutAnim;

	private AxDialogShareClickListen listener;

	// 点击回调接口
	public interface AxDialogShareClickListen {
		//微信
		public void onShareWxClick(AxShareDialog axClickListener);
		
		//短信
		public void onShareMsgClick(AxShareDialog axClickListener);
		
		//邮件
		public void onShareEmailClick(AxShareDialog axClickListener);
	}

	public AxShareDialog setAxDialogShareClickListen(AxDialogShareClickListen mListener) {
		listener = mListener;
		return this;
	}

	public AxShareDialog(Context context) {
		super(context, R.style.alert_dialog);
		mContext = context;
		mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(
				getContext(), R.anim.gd_dialog_modal_in);
		mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(
				getContext(), R.anim.gd_dialog_modal_out);
		mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mDialogView.setVisibility(View.GONE);
				mDialogView.post(new Runnable() {
					@Override
					public void run() {
						AxShareDialog.super.dismiss();
					}
				});
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		// 退出动画
		mOverlayOutAnim = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {
				getWindow().getDecorView().getBackground()
						.setAlpha((int) ((1 - interpolatedTime) * 255));
			}
		};
		mOverlayOutAnim.setDuration(150);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ax_share_layout);
		mDialogView = getWindow().getDecorView().findViewById(
				android.R.id.content);
		initView();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		// 微信
		case R.id.share_wx:
			listener.onShareWxClick(AxShareDialog.this);
			break;

		// 短信
		case R.id.share_msg:
			listener.onShareMsgClick(AxShareDialog.this);
			break;

		// 邮件
		case R.id.share_email:
			listener.onShareEmailClick(AxShareDialog.this);
			break;
		default:
			break;
		}
	};

	private void initView() {
		findViewById(R.id.share_wx).setOnClickListener(this);
		findViewById(R.id.share_msg).setOnClickListener(this);
		findViewById(R.id.share_email).setOnClickListener(this);
	}
	

	@Override
	protected void onStart() {
		getWindow().getDecorView().getBackground().setAlpha(255);
		mDialogView.startAnimation(mModalInAnim);
		super.onStart();
	}

	public void dismiss() {
		if (null != mDialogView) {
			mDialogView.startAnimation(mModalOutAnim);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			dismiss();
		}
		return true;
	}
	
}
