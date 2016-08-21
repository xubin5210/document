package com.ciapc.anxin.common.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ciapc.anxin.R;
import com.ciapc.anxin.modules.home.HomeSharedPreferences;
import com.ciapc.anxin.utils.OptAnimationLoader;
import com.master.util.common.SortTextView;
import com.master.util.common.StringUtils;

public class AxinDialog extends AlertDialog implements OnClickListener {
	// 只显示图片 显示二维码
	public static final int DIALOG_IMAGE = 1;

	// 显示提示内容 可以选择
	public static final int DIALOG_CHANGE = 2;

	// 显示输入框
	public static final int DIALOG_INPUT = 3;

	private Context mContext;

	// 当前选择的功能 1 显示图片 2 显示提示内容
	private int flag;

	// 提示内容
	private String title;
	// 需要显示的图片
	private Bitmap img;

	// 确定 取消
	private Button confirm, cancel;

	// 提示内容
	private SortTextView sortTextView;

	// 需要显示的图片
	private ImageView axImg;

	// 选择界面
	private LinearLayout changeLayout;

	// 确认的文字
	private String confirmStr;

	// 取消的文字
	private String cancelStr;

	private LinearLayout change;
	private RelativeLayout inputLayout;
	private CustomEditText inputContent;
	public static String content;

	// 动画
	private AnimationSet mModalInAnim;
	private AnimationSet mModalOutAnim;
	private View mDialogView;
	private Animation mOverlayOutAnim;

	// 确定和取消 图片点击事件
	private AxDialogClickListen confirmClick;
	private AxDialogClickListen cancelClick;
	private AxDialogClickListen imglClick;
	
	private LinearLayout view;

	// 点击回调接口
	public interface AxDialogClickListen {
		public void onClick(AxinDialog axinDialog);
	}

	public AxinDialog setImgClick(AxDialogClickListen listener) {
		imglClick = listener;
		return this;
	}

	public AxinDialog setConfirmClick(AxDialogClickListen listener) {
		confirmClick = listener;
		return this;
	}

	public AxinDialog setCancelClick(AxDialogClickListen listener) {
		cancelClick = listener;
		return this;
	}

	public AxinDialog(Context context) {
		super(context);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 自定义初始化 根据type 觉得不同布局
	 * @Date:2015年6月7日下午10:57:47
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public AxinDialog(Context context, int type) {
		super(context, R.style.alert_dialog);
		mContext = context;
		flag = type;
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
						AxinDialog.super.dismiss();
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
		setContentView(R.layout.anxin_dialog);
		mDialogView = getWindow().getDecorView().findViewById(
				android.R.id.content);
		view = (LinearLayout) findViewById(R.id.loading);
		initView();
	}

	/**
	 * @Auther: zhuwt
	 * @Description:初始化控件
	 * @Date:2015年6月7日下午11:30:39
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		switch (flag) {
		// 显示图片
		case DIALOG_IMAGE:
			axImg = (ImageView) findViewById(R.id.ax_dialog_img);
			axImg.setOnClickListener(this);
			axImg.setVisibility(View.VISIBLE);
			setImg(img);
			break;
		// 显示提示内容
		case DIALOG_CHANGE:
			setConfirmStr(confirmStr);
			setCancelStr(cancelStr);
			changeLayout = (LinearLayout) findViewById(R.id.ax_dialog_change);
			changeLayout.setVisibility(View.VISIBLE);
			inputLayout = (RelativeLayout) findViewById(R.id.ax_dialog_input);
			inputLayout.setVisibility(View.GONE);
			change = (LinearLayout) findViewById(R.id.show_text);
			change.setVisibility(View.VISIBLE);
			confirm = (Button) findViewById(R.id.ax_dialog_confirm);
			confirm.setOnClickListener(this);
			confirm.setText(confirmStr);
			cancel = (Button) findViewById(R.id.ax_dialog_cancel);
			cancel.setOnClickListener(this);
			cancel.setText(cancelStr);
			sortTextView = (SortTextView) findViewById(R.id.ax_dialog_textview);
			setTitle(title);
			break;

		// 显示输入框
		case DIALOG_INPUT:
			setConfirmStr(confirmStr);
			setCancelStr(cancelStr);
			changeLayout = (LinearLayout) findViewById(R.id.ax_dialog_change);
			changeLayout.setVisibility(View.VISIBLE);
			change = (LinearLayout) findViewById(R.id.show_text);
			change.setVisibility(View.GONE);
			inputLayout = (RelativeLayout) findViewById(R.id.ax_dialog_input);
			inputLayout.setVisibility(View.VISIBLE);
			inputContent = (CustomEditText) findViewById(R.id.ax_dialog_input_content);
			inputContent.setVisibility(View.VISIBLE);
			if (StringUtils.isNotEmpty(HomeSharedPreferences
					.getSignature(mContext))
					&& !"点击编辑个性签名".equals(HomeSharedPreferences
							.getSignature(mContext))) {
				inputContent.setText(HomeSharedPreferences
						.getSignature(mContext));
			}
			confirm = (Button) findViewById(R.id.ax_dialog_confirm);
			confirm.setOnClickListener(this);
			confirm.setText(confirmStr);
			cancel = (Button) findViewById(R.id.ax_dialog_cancel);
			cancel.setOnClickListener(this);
			cancel.setText(cancelStr);
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		// 确定
		case R.id.ax_dialog_confirm:
			if (confirmClick != null) {
				if (flag == DIALOG_INPUT) {
					content = inputContent.getText().toString();
				}
				confirmClick.onClick(AxinDialog.this);
			} else {
				dismiss();
			}
			break;

		// 取消
		case R.id.ax_dialog_cancel:
			if (cancelClick != null) {
				cancelClick.onClick(AxinDialog.this);
			} else {
				dismiss();
			}
			break;

		// 点击图片
		case R.id.ax_dialog_img:
			if (imglClick != null) {
				imglClick.onClick(AxinDialog.this);
			} else {
				dismiss();
			}
			break;
		default:
			break;
		}
	};

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
		if (flag == DIALOG_CHANGE) {
			confirm.startAnimation(mOverlayOutAnim);
		}
	}

	public String getTitle() {
		return title;
	}

	public AxinDialog setTitle(String title) {
		this.title = title;
		if (StringUtils.isNotEmpty(title) && null != sortTextView) {
			sortTextView.setText(title);
		}
		return this;
	}

	public Bitmap getImg() {
		return img;
	}

	public AxinDialog setImg(Bitmap img) {
		this.img = img;
		if (null != img && null != axImg) {
			axImg.setImageBitmap(img);
		}
		return this;
	}

	public String getConfirmStr() {
		return confirmStr;
	}

	public AxinDialog setConfirmStr(String confirmStr) {
		this.confirmStr = confirmStr;
		return this;
	}

	public String getCancelStr() {
		return cancelStr;
	}

	public AxinDialog setCancelStr(String cancelStr) {
		this.cancelStr = cancelStr;
		return this;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
//			if (!(event.getX() >= -10 && event.getY() >= -10)
//					|| event.getX() >= view.getWidth() + 10
//					|| event.getY() >= view.getHeight()) {// 如果点击位置在当前View外部则销毁当前视图,其中10与20为微调距离
//				dismiss();
//			}
			dismiss();
		}
		return true;
	}
}
