package com.master.util.common;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 
 * @author zhuwt  
 * @Description:文字排版
 * @ClassName: SortTextView.java   
 * @date 2015年6月7日 下午11:08:38      
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */
public class SortTextView extends TextView {
	private String text;
	private float textSize;
	private int textColor;
	private Paint paint1;
	private float textShowWidth;

	public SortTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint1 = this.getPaint();
		text = this.getText().toString();
		textSize = this.getTextSize();
		textColor = this.getTextColors().getDefaultColor();
		paint1.setTextSize(textSize);
		paint1.setColor(textColor);
		paint1.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		textShowWidth = this.getMeasuredWidth();
		int lineCount = 0;
		text = this.getText().toString();
		if (text == null)
			return;
		char[] textCharArray = text.toCharArray();
		float drawedWidth = 0;
		float charWidth;
		for (int i = 0; i < textCharArray.length; i++) {
			charWidth = paint1.measureText(textCharArray, i, 1);
			if (textCharArray[i] == '\n') {
				lineCount++;
				drawedWidth = 0;
				continue;
			}
			if (textShowWidth - drawedWidth < charWidth) {
				lineCount++;
				drawedWidth = 0;
			}
			canvas.drawText(textCharArray, i, 1, drawedWidth, (lineCount + 1)
					* textSize, paint1);
			drawedWidth += charWidth;
		}
		setHeight((int) ((lineCount + 2) * (int) textSize));
	}
}
