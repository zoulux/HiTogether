package com.lyy.hitogether.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyy.hitogether.R;
import com.lyy.hitogether.util.DensityUtils;

public class CustomTitleBarView extends RelativeLayout {
	//图片View
	private ImageView leftImage;
	private ImageView rightImage;
	private ImageView centerImage;
	//文字View
	private TextView leftTextView;
	private TextView rightTextView;
	private TextView centerTextView;
	//按钮View
	private Button leftButton;
	private Button rightButton;
	private Button centerButton;

	private static final int LEFT_TYPE_TEXT = 0;

	private static final int LEFT_TYPE_IMAGE = 1;

	private static final int LEFT_TYPE_BUTTON = 2;

	private static final int RIGHT_TYPE_TEXT = 0;

	private static final int RIGHT_TYPE_IMAGE = 1;

	private static final int RIGHT_TYPE_BUTTON = 2;

	private static final int CENTER_TYPE_TEXT = 0;

	private static final int CENTER_TYPE_IMAGE = 1;

	private static final int CENTER_TYPE_BUTTON = 2;
	//左边的文字的属性
	private String leftText;
	private int leftTextColor;
	private float leftTextSize;
	private float leftTextPadding;
	private float leftTextPaddingLeft;
	private float leftTextPaddingRight;
	private float leftTextPaddingTop;
	private float leftTextPaddingBottom;
	//左边的图片的属性
	private int leftImageSource;
	private float leftImagePadding;
	private float leftImagePaddingLeft;
	private float leftImagePaddingRight;
	private float leftImagePaddingTop;
	private float leftImagePaddingBottom;
	//右边的文字的属性
	private String rightText;
	private int rightTextColor;
	private float rightTextSize;
	private float rightTextPadding;
	private float rightTextPaddingLeft;
	private float rightTextPaddingRight;
	private float rightTextPaddingTop;
	private float rightTextPaddingBottom;
	//右边的图片的属性
	private int rightImageSource;
	private float rightImagePadding;
	private float rightImagePaddingLeft;
	private float rightImagePaddingRight;
	private float rightImagePaddingTop;
	private float rightImagePaddingBottom;
	//中间的文字的属性
	private String centerText;
	private int centerTextColor;
	private float centerTextSize;
	private float centerTextPadding;
	private float centerTextPaddingLeft;
	private float centerTextPaddingRight;
	private float centerTextPaddingTop;
	private float centerTextPaddingBottom;
	
	private onLeftBarViewClickListener mLeftBarViewClickListener = null;
	private onRightBarViewClickListener mRightBarViewClickListener = null;

	public interface onLeftBarViewClickListener {
		void onclick(View v);
	}

	public void setOnLeftBarViewClickListener(
			onLeftBarViewClickListener leftBarViewClickListener) {
		mLeftBarViewClickListener = leftBarViewClickListener;
	}

	public onLeftBarViewClickListener getLeftBarViewClickListener() {
		return mLeftBarViewClickListener;
	}

	public interface onRightBarViewClickListener {
		void onclick(View v);
	}

	public void setOnRightBarViewClickListener(
			onRightBarViewClickListener rightBarViewClickListener) {
		mRightBarViewClickListener = rightBarViewClickListener;
	}

	public onRightBarViewClickListener getRightBarViewClickListener() {
		return mRightBarViewClickListener;
	}

	public CustomTitleBarView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public CustomTitleBarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public CustomTitleBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		View leftView = null;
		View rightView = null;
		View centerView = null;
		
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.barView, defStyle, 0);
		int leftType = ta.getInt(R.styleable.barView_leftType, -1);
		int rightType = ta.getInt(R.styleable.barView_rightType, -1);
		int centerType = ta.getInt(R.styleable.barView_centerType, -1);

		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		lp1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		
		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		lp3.addRule(RelativeLayout.CENTER_IN_PARENT);
		
		switch (leftType) {

		case LEFT_TYPE_IMAGE:
			leftImage = new ImageView(context);
			leftView = leftImage;
			initLeftImage(ta, context);
			addView(leftView, lp1);
			setLeftView(leftView);
			break;
		case LEFT_TYPE_TEXT:
			leftTextView = new TextView(context);
			leftView = leftTextView;
			initLeftText(ta, context);
			addView(leftView, lp1);
			setLeftView(leftView);

			break;
		case LEFT_TYPE_BUTTON:
			// Todo
			leftButton = new Button(context);
			leftView = leftButton;
			addView(leftView, lp1);
			setLeftView(leftView);
			break;
		default:
			break;

		}

		switch (rightType) {

		case RIGHT_TYPE_IMAGE:
			rightImage = new ImageView(context);
			rightView = rightImage;
			initRighttImage(ta, context);
			addView(rightView, lp2);
			setRightView(rightView);
			break;
		case RIGHT_TYPE_TEXT:
			rightTextView = new TextView(context);
			rightView = rightTextView;
			initRightText(ta, context);
			addView(rightView, lp2);
			setRightView(rightView);
			break;
		case RIGHT_TYPE_BUTTON:
			// Todo
			rightButton = new Button(context);
			rightView = rightButton;
			addView(rightView, lp2);
			setRightView(rightView);
			break;
		default:
			break;
		}

		switch (centerType) {

		case CENTER_TYPE_IMAGE:
			// Todo
			centerImage = new ImageView(context);
			centerView = centerImage;
			addView(centerView, lp3);
			break;
		case CENTER_TYPE_TEXT:
			centerTextView = new TextView(context);
			centerView = centerTextView;
			initCenterText(ta, context);
			addView(centerView, lp3);
			setCenterView(centerView);

			break;
		case CENTER_TYPE_BUTTON:
			// Todo
			centerButton = new Button(context);
			centerView = centerButton;
			addView(centerView, lp3);
			break;
		default:
			break;

		}
		initEvent(leftView, rightView);
		ta.recycle();

	}

	private void initEvent(final View leftView, final View rightView) {
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mLeftBarViewClickListener != null) {
					mLeftBarViewClickListener.onclick(leftView);
				}

			}
		});
		rightView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mRightBarViewClickListener != null) {
					mRightBarViewClickListener.onclick(rightView);
				}

			}
		});
	}

	/**
	 * 设置中间的控件
	 * 
	 * @param centerView
	 */
	private void setCenterView(View centerView) {
		if (centerView == centerTextView) {
			// System.out.println("leftText " + leftText + "leftTextSize "
			// + leftTextSize + "leftTextColor " + leftTextColor
			// + "leftTextPadding " + leftTextPadding);
			centerTextView.setText(centerText);
			centerTextView.setTextSize(centerTextSize);
			centerTextView.setTextColor(centerTextColor);
			if (centerTextPadding == 0) {
				centerTextView.setPadding((int) centerTextPaddingLeft,
						(int) centerTextPaddingTop,
						(int) centerTextPaddingRight,
						(int) centerTextPaddingBottom);
			} else {
				centerTextView.setPadding((int) centerTextPadding,
						(int) centerTextPadding, (int) centerTextPadding,
						(int) centerTextPadding);
			}
			centerTextView.setGravity(Gravity.CENTER);
		}

	}

	/**
	 * 初始化中间的文字按钮
	 * 
	 * @param ta
	 * @param context
	 */
	private void initCenterText(TypedArray ta, Context context) {
		centerText = ta.getString(R.styleable.barView_centerText);
		if (centerText == null) {
			centerText = "请设置文字";
		}
		centerTextColor = ta.getColor(R.styleable.barView_centerTextColor,
				Color.BLACK);

		centerTextSize = ta.getDimension(R.styleable.barView_centerTextSize,
				14.0f);
		if (centerTextSize != 14.0f) {
			centerTextSize = DensityUtils.px2sp(context, centerTextSize);
		}
		// 默认为0
		centerTextPadding = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_centerTextPadding, 0));
		centerTextPaddingLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_centerTextPaddingLeft, 0));
		// System.out.println(leftTextPaddingLeft+"<><>");
		centerTextPaddingRight = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_centerTextPaddingRight, 0));
		centerTextPaddingTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_centerTextPaddingTop, 0));
		centerTextPaddingBottom = DensityUtils
				.px2dp(context, ta.getDimension(
						R.styleable.barView_centerTextPaddingBottom, 0));

	}

	/**
	 * 初始化左边的按钮图片
	 * 
	 * @param ta
	 * @param context
	 */
	private void initLeftImage(TypedArray ta, Context context) {
		// ta.getResources();
		leftImageSource = ta.getResourceId(R.styleable.barView_leftImageSource,
				R.drawable.ic_launcher);
		leftImagePadding = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftImagePadding, 0));
		leftImagePaddingLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftImagePaddingLeft, 0));
		leftImagePaddingRight = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftImagePaddingRight, 0));
		leftImagePaddingTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftImagePaddingTop, 0));
		leftImagePaddingBottom = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftImagePaddingBottom, 0));
	}

	/**
	 * 初始化左边按钮文字
	 * 
	 * @param ta
	 * @param context
	 */
	private void initLeftText(TypedArray ta, Context context) {
		leftText = ta.getString(R.styleable.barView_leftText);
		if (leftText == null) {
			leftText = "请设置文字";
		}
		leftTextColor = ta.getColor(R.styleable.barView_leftTextColor,
				Color.BLACK);

		leftTextSize = ta.getDimension(R.styleable.barView_leftTextSize, 14.0f);
		if (leftTextSize != 14.0f) {
			leftTextSize = DensityUtils.px2sp(context, leftTextSize);
		}
		// 默认为0
		leftTextPadding = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftTextPadding, 0));
		leftTextPaddingLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftTextPaddingLeft, 0));
		// System.out.println(leftTextPaddingLeft+"<><>");
		leftTextPaddingRight = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftTextPaddingRight, 0));
		leftTextPaddingTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftTextPaddingTop, 0));
		leftTextPaddingBottom = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_leftTextPaddingBottom, 0));

	}

	/**
	 * 设置左边的自定义控件
	 * 
	 * @param leftView
	 */
	private void setLeftView(View leftView) {
		if (leftView == leftTextView) {
			// System.out.println("leftText " + leftText + "leftTextSize "
			// + leftTextSize + "leftTextColor " + leftTextColor
			// + "leftTextPadding " + leftTextPadding);
			leftTextView.setText(leftText);
			leftTextView.setTextSize(leftTextSize);
			leftTextView.setTextColor(leftTextColor);
			if (leftTextPadding == 0) {
				leftTextView.setPadding((int) leftTextPaddingLeft,
						(int) leftTextPaddingTop, (int) leftTextPaddingRight,
						(int) leftTextPaddingBottom);
			} else {
				leftTextView.setPadding((int) leftTextPadding,
						(int) leftTextPadding, (int) leftTextPadding,
						(int) leftTextPadding);
			}
			leftTextView.setGravity(Gravity.CENTER);
		} else if (leftView == leftImage) {
			leftImage.setImageResource(leftImageSource);
			if (leftImagePadding == 0) {

				leftImage.setPadding((int) leftImagePaddingLeft,
						(int) leftImagePaddingTop, (int) leftImagePaddingRight,
						(int) leftImagePaddingBottom);
			} else {
				leftImage.setPadding((int) leftImagePadding,
						(int) leftImagePadding, (int) leftImagePadding,
						(int) leftImagePadding);
			}

		}

	}

	/**
	 * 设置右边的自定义控件
	 * 
	 * @param rightView
	 */
	private void setRightView(View rightView) {
		if (rightView == rightTextView) {
			// System.out.println("leftText " + leftText + "leftTextSize "
			// + leftTextSize + "leftTextColor " + leftTextColor
			// + "leftTextPadding " + leftTextPadding);
			rightTextView.setText(rightText);
			rightTextView.setTextSize(rightTextSize);
			rightTextView.setTextColor(rightTextColor);
			if (rightTextPadding == 0) {
				rightTextView.setPadding((int) rightTextPaddingLeft,
						(int) rightTextPaddingTop, (int) rightTextPaddingRight,
						(int) rightTextPaddingBottom);
			} else {
				rightTextView.setPadding((int) rightTextPadding,
						(int) rightTextPadding, (int) rightTextPadding,
						(int) rightTextPadding);
			}
			rightTextView.setGravity(Gravity.CENTER);
		}
		if (rightView == rightImage) {
			rightImage.setImageResource(rightImageSource);
			if (rightImagePadding == 0) {

				rightImage.setPadding((int) rightImagePaddingLeft,
						(int) rightImagePaddingTop,
						(int) rightImagePaddingRight,
						(int) rightImagePaddingBottom);
			} else {
				rightImage.setPadding((int) rightImagePadding,
						(int) rightImagePadding, (int) rightImagePadding,
						(int) rightImagePadding);
			}
		}
	}

	/**
	 * 初始化右边的文字按钮
	 * 
	 * @param ta
	 * @param context
	 */
	private void initRightText(TypedArray ta, Context context) {
		rightText = ta.getString(R.styleable.barView_rightText);
		if (rightText == null) {
			rightText = "请设置文字";
		}
		rightTextColor = ta.getColor(R.styleable.barView_rightTextColor,
				Color.BLACK);

		rightTextSize = ta.getDimension(R.styleable.barView_rightTextSize,
				14.0f);
		if (rightTextSize != 14.0f) {
			rightTextSize = DensityUtils.px2sp(context, rightTextSize);
		}
		// 默认为0
		rightTextPadding = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightTextPadding, 0));
		rightTextPaddingLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightTextPaddingLeft, 0));
		// System.out.println(leftTextPaddingLeft+"<><>");
		rightTextPaddingRight = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightTextPaddingRight, 0));
		rightTextPaddingTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightTextPaddingTop, 0));
		rightTextPaddingBottom = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightTextPaddingBottom, 0));

	}

	/**
	 * 初始化右边图片按钮
	 * 
	 * @param ta
	 * @param context
	 */
	private void initRighttImage(TypedArray ta, Context context) {
		rightImageSource = ta.getResourceId(
				R.styleable.barView_rightImageSource, R.drawable.ic_launcher);
		rightImagePadding = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightImagePadding, 0));
		rightImagePaddingLeft = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightImagePaddingLeft, 0));
		rightImagePaddingRight = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightImagePaddingRight, 0));
		rightImagePaddingTop = DensityUtils.px2dp(context,
				ta.getDimension(R.styleable.barView_rightImagePaddingTop, 0));
		rightImagePaddingBottom = DensityUtils
				.px2dp(context, ta.getDimension(
						R.styleable.barView_rightImagePaddingBottom, 0));

	}

}
