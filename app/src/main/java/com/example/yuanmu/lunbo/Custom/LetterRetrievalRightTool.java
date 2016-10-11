package com.example.yuanmu.lunbo.Custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class LetterRetrievalRightTool extends View {
	private String[] mLetterArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","#" };

	// public void setLetterList(ArrayList<String> letterList) {
	// this.letterList = letterList;
	// }

	OnTouchingLetterChangedListener onTouchingLetterChangedListener;

	int choose = -1;
	Paint paint = new Paint();
	boolean showBkg = false;

	public LetterRetrievalRightTool(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LetterRetrievalRightTool(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LetterRetrievalRightTool(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showBkg) {
			canvas.drawColor(0x10000000);
		}

		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / mLetterArray.length;
		for (int i = 0; i < mLetterArray.length; i++) {
			paint.setColor(Color.BLACK);
			// paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setTextSize(30);
			paint.setColor(0xff888888);
			paint.setAntiAlias(true);
			if (i == choose) {
				paint.setColor(0xFF3399FF);
				paint.setFakeBoldText(true);
			}
			float xPos = (width >> 1) - paint.measureText(mLetterArray[i]) / 2;
			float yPos = singleHeight * i + singleHeight ;
			canvas.drawText(mLetterArray[i], xPos, yPos, paint);
			paint.reset();
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * mLetterArray.length); // 字母位置

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < mLetterArray.length) { 
					listener.onTouchingLetterChanged(mLetterArray[c]);
					choose = c; // 处理重复
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < mLetterArray.length) {
					listener.onTouchingLetterChanged(mLetterArray[c]);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}