package com.ciapc.anxin.common.view;

import com.ciapc.anxin.R;
import com.master.util.common.ResourceFactory;
import com.master.util.common.StringUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author zhuwt
 * @Description: 公用的标题头
 * @ClassName: PubTitle.java
 * @date 2015年5月26日 下午1:51:50
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class PubTitle extends RelativeLayout {

	private String text;// 标题拦文字
	private String rightIconInt;// R文件中的ID值
	private String leftIconInt;// R文件中的ID值
	private String rightStyle;// 右边的样式
	private String leftStyle;// 左边的样式
	public ImageView rightView;// 标题栏右边的
	private TextView mTextView;// 标题拦文字
	private ImageView leftView;// 左边的图片

	private TextView rightTextView;// 右边的文字
	private String rightText;// 右边的文字

	private boolean showLeft;// 左边是否显示
	private boolean showRight;// 右边是否显示
	private boolean showRightText;// 右边是否是文字

	private OnTitleClickListener listener;
	private TypedArray mArray;
	private Context mContext;

	public ImageView getRightView() {
		return rightView;
	}

	public void setRightView(ImageView rightView) {
		this.rightView = rightView;
	}

	public ImageView getLeftView() {
		return leftView;
	}

	public void setLeftView(ImageView leftView) {
		this.leftView = leftView;
	}

	public TextView getRightTextView() {
		return rightTextView;
	}

	public void setRightTextView(TextView rightTextView) {
		this.rightTextView = rightTextView;
	}

	public void setOnTitleClickListener(OnTitleClickListener listener) {
		this.listener = listener;
	}

	// 点击回调方法
	public interface OnTitleClickListener {
		public void onLeftClick();

		public void onRightClick();

	};

	public PubTitle(Context context) {
		super(context);
	}

	public void setMainTitle(String str){
		mTextView.setText(str);
	}
	
	/**
	 * @Auther: zhuwt
	 * @Description: 左右边 是否可见
	 * @Date:2015年6月1日上午10:21:28
	 * @param left
	 * @param right
	 * @return
	 * @return PubTitle
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	public PubTitle setShowLeftOrRight(boolean left, boolean right,
			boolean rightText) {
		this.showLeft = left;
		this.showRight = right;
		this.showRightText = rightText;
		if (showLeft) {
			leftView.setVisibility(View.VISIBLE);
			// 左边初始化
			initLeft(mArray, mContext);
		}
		if (showRight) {
			rightView.setVisibility(View.VISIBLE);
			// 右边初始化
			initRight(mArray, mContext);
		}
		if (showRightText) {
			rightTextView.setVisibility(View.VISIBLE);
			// 右边文字初始化
			initRightText(mArray);
		}
		// 释放资源
		mArray.recycle();
		return this;
	}

	public PubTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.ax_title_layout, this);
		mArray = context.obtainStyledAttributes(attrs, R.styleable.pubTitle);
		initView();
		// 标题栏初始化
		initTitleText(mArray);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
	}

	/**
	 * @Auther: zhuwt
	 * @Description:控件初始化
	 * @Date:2015年6月4日下午9:18:36
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initView() {
		leftView = (ImageView) findViewById(R.id.title_back_btn);
		rightView = (ImageView) findViewById(R.id.title_right_btn);
		rightTextView = (TextView) findViewById(R.id.title_right_text);
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 获取R文件中 当前图片的值
	 * @Date:2015年6月2日下午2:19:04
	 * @param name
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private String getIdByName(TypedArray mArray, int name) {
		// 用反射获取图片文件在R文件中的ID
		String str = mArray.getString(name);
		if (!StringUtils.isNotEmpty(str)) {
			return null;
		}
		String strs[] = str.split("/");
		return strs[strs.length - 1].replaceAll(".png", "");
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 获取R文件中 当前样式的值
	 * @Date:2015年6月2日下午2:58:59
	 * @param mArray
	 * @param name
	 * @return
	 * @return String
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private String getIdByDraw(TypedArray mArray, int name) {
		// 用反射获取图片文件在R文件中的ID
		String str = mArray.getString(name);
		if (!StringUtils.isNotEmpty(str)) {
			return null;
		}
		String strs[] = str.split("/");
		return strs[strs.length - 1].replaceAll(".xml", "");
	}

	/**
	 * @Auther: zhuwt
	 * @Description:左边时图片时 初始化
	 * @Date:2015年6月4日下午5:44:18
	 * @param mArray
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initLeft(TypedArray mArray, Context context) {
		// 左边是否有内容 有内容则开始初始化控件
		if (showLeft) {
			// 用反射获取图片文件在R文件中的ID
			leftIconInt = getIdByName(mArray, R.styleable.pubTitle_leftIcon);
			leftStyle = getIdByDraw(mArray, R.styleable.pubTitle_leftStyle);

			// 开始赋值
			leftView.setImageResource(ResourceFactory.getIdByName(context,
					"drawable", leftIconInt));
			if (StringUtils.isNotEmpty(leftStyle)) {
				leftView.setBackgroundResource(ResourceFactory.getIdByName(
						context, "drawable", leftStyle));
			}
			// 左边的点击回调
			leftView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (null != listener) {
						listener.onLeftClick();
					}
				}
			});
		}
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 右边是图片时 初始化
	 * @Date:2015年6月4日下午5:43:59
	 * @param mArray
	 * @param context
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initRight(TypedArray mArray, Context context) {
		// 左边是否有内容 有内容则开始初始化控件
		if (showRight) {
			// 用反射获取图片文件在R文件中的ID
			rightIconInt = getIdByName(mArray, R.styleable.pubTitle_rightIcon);
			rightStyle = getIdByDraw(mArray, R.styleable.pubTitle_rightStyle);

			// 开始赋值
			rightView.setImageResource(ResourceFactory.getIdByName(context,
					"drawable", rightIconInt));
			rightView.setBackgroundResource(ResourceFactory.getIdByName(
					context, "drawable", rightStyle));
			// 右边图片的点击回调
			rightView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (null != listener) {
						listener.onRightClick();
					}
				}
			});
		}
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 右边是文字时 初始化
	 * @Date:2015年6月4日下午5:43:45
	 * @param mArray
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initRightText(TypedArray mArray) {
		// 右边是否是文字 有内容则开始初始化控件
		if (showRightText) {
			rightText = mArray.getString(R.styleable.pubTitle_rightText);
			rightTextView.setText(rightText);
			rightTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (null != listener) {
						listener.onRightClick();
					}
				}
			});
		}
	}

	/**
	 * @Auther: zhuwt
	 * @Description: 标题栏文字初始化
	 * @Date:2015年6月4日下午5:47:53
	 * @param mArray
	 * @return void
	 * @说明 代码版权归 杭州反盗版中心有限公司 所有
	 */
	private void initTitleText(TypedArray mArray) {
		mTextView = (TextView) findViewById(R.id.title_text);
		text = mArray.getString(R.styleable.pubTitle_text);
		mTextView.setText(text);
	}
}
