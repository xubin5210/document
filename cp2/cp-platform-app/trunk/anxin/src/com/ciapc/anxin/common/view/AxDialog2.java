package com.ciapc.anxin.common.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;

import com.ciapc.anxin.R;
import com.ciapc.anxin.utils.OptAnimationLoader;

public class AxDialog2 extends Dialog implements OnClickListener {

	private Context mContext;
	// 提示内容
	private String reason;

	// 动画
	private AnimationSet mModalInAnim;
	private AnimationSet mModalOutAnim;
	private View mDialogView;
	private Animation mOverlayOutAnim;

	private AxDialogClickListen listener;

	// 点击回调接口
	public interface AxDialogClickListen {
		// 确定
		public void onConfirm();

		// 取消
		public void onCancel();
	}

	public void setAxDialogClickListen(AxDialogClickListen mListener) {
		this.listener = mListener;
	}

	public AxDialog2(Context context) {
		super(context);
	}
	
	public AxDialog2(Context context, String str) {
		super(context, R.style.alert_dialog);
		mContext = context;
		reason = str;
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
						// VipRestartDialog.super.dismiss();
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
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	};
}
