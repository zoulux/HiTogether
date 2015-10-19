package com.lyy.hitogether.view;
import com.lyy.hitogether.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ChangeColorIconWithText extends View {
	private int mColor = 0xFF45C01A;
	private Bitmap mIconBitmap;
	private String mText = "Ӧ��";
	private int mTextSize = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());

	private Canvas mCanvas;
	private Paint mPaint;
	private Bitmap mBitmap;

	private float mAlpha;

	private Rect mIconRect;
	private Rect mTextBound;

	private Paint mTextPaint;
	

	public ChangeColorIconWithText(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public ChangeColorIconWithText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public ChangeColorIconWithText(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.ChangeColorIconWithText);

		int n = ta.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = ta.getIndex(i);
			switch (attr) {
			case R.styleable.ChangeColorIconWithText_icon:
				BitmapDrawable drawable = (BitmapDrawable) ta.getDrawable(attr);
				mIconBitmap = drawable.getBitmap();
				break;

			case R.styleable.ChangeColorIconWithText_color:
				mColor = ta.getColor(attr, 0xFF45C01A);
				break;
			case R.styleable.ChangeColorIconWithText_text:
				mText = ta.getString(attr);
				break;
			case R.styleable.ChangeColorIconWithText_text_size:
				mTextSize = (int) ta.getDimension(attr, TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
								getResources().getDisplayMetrics()));
				break;
			}
		}
		ta.recycle();

		mTextBound = new Rect();
		mTextPaint = new Paint();
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(0xff555555);
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
				- getPaddingRight(), getMeasuredHeight() - getPaddingTop()
				- getPaddingBottom() - mTextBound.height());
		int left = getMeasuredWidth() / 2 - iconWidth / 2;
		int top = getMeasuredHeight() / 2 - (iconWidth + mTextBound.height())
				/ 2;

		mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mIconBitmap, null, mIconRect, null);

		int alpha = (int) Math.ceil(255 * mAlpha);

		// �ڴ�ȥ׼��mBitmap��setAlpha,���ƴ�ɫ,����Xfermode,����ͼ��
		setupTargetBitmap(alpha);
		// ����ԭ�ı�
		drawSourceText(canvas, alpha);

		drawTargetText(canvas, alpha);

		canvas.drawBitmap(mBitmap, 0, 0, null);

	}

	/**
	 * ���Ʊ�ɫ���ı�
	 * 
	 * @param canvas
	 * @param alpha
	 */
	private void drawTargetText(Canvas canvas, int alpha) {
			mTextPaint.setColor(mColor);
			mTextPaint.setAlpha(alpha);
			
			int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
			int y = mIconRect.bottom + mTextBound.height();
			canvas.drawText(mText, x, y, mTextPaint);
	}

	/**
	 * ����ԭ�ı�
	 * 
	 * @param canvas
	 * @param alpha
	 */
	private void drawSourceText(Canvas canvas, int alpha) {
		mTextPaint.setColor(0xff333333);
		mTextPaint.setAlpha(255-alpha);
		int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
		int y = mIconRect.bottom + mTextBound.height();
		canvas.drawText(mText, x, y, mTextPaint);
	}

	/**
	 * ���ڴ��л��ƿɱ�ɫ��icon
	 */
	private void setupTargetBitmap(int alpha) {
		mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
				Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		mPaint = new Paint();
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(alpha);

		mCanvas.drawRect(mIconRect, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mPaint.setAlpha(255);
		mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
	}
	
	
	public void setIconAlpha(float alpha){
		this.mAlpha = alpha;
		invalidateView();
	}

	/**
	 * �ػ�
	 */
	private void invalidateView() {
			if (Looper.getMainLooper()==Looper.myLooper()) {
				invalidate();//ui�߳�ˢ��
			}else{
				postInvalidate();//��ui�߳�ˢ��
			}
	}

}
