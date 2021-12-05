package com.caijinfu.customizeprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

/**
 * 自定义的带进度显示的ProgressBar
 *
 * @author 猿小蔡
 * @date 2021/12/5
 */
public class CoolProgressBar extends ProgressBar {

  private final TextPaint mPaint = new TextPaint();
  private String mText;
  private float mTextSize = 18;
  private int mTextColor = 0xff4d4d4d;

  public CoolProgressBar(Context context) {
    this(context, null);
  }

  public CoolProgressBar(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CoolProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.CoolProgressBar);
    mTextColor = attribute.getColor(R.styleable.CoolProgressBar_textColor, mTextColor);
    mTextSize =
        attribute.getDimension(
            R.styleable.CoolProgressBar_textSize, DensityUtil.sp2px(getContext(), 18));
    mText = attribute.getString(R.styleable.CoolProgressBar_text);
    attribute.recycle();
    mPaint.setAntiAlias(true);
    mPaint.setColor(mTextColor);
    mPaint.setTextSize(mTextSize);
  }

  @Override
  protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    Log.i("TAG", "onMeasure: ");
    int[] textSize = getTextSize();
    if (textSize == null) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      return;
    }
    int width = measureWidth(widthMeasureSpec);
    int height = measureHeight(heightMeasureSpec);
    int textWith = textSize[0];
    int textHeight = textSize[1];
    if (width > textWith && height > textHeight) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      return;
    }
    if (textWith > width) {
      width = textWith + 50;
    } else if (textHeight > height) {
      height = textHeight + 50;
    }
    Log.i("TAG", "width: " + width);
    Log.i("TAG", "height: " + height);
    setMeasuredDimension(width, height);
  }

  private int measureWidth(int measureSpec) {
    int result = 200;
    int specMode = MeasureSpec.getMode(measureSpec);
    int specSize = MeasureSpec.getSize(measureSpec);
    switch (specMode) {
      case MeasureSpec.UNSPECIFIED:
        result = specSize;
        break;
      case MeasureSpec.AT_MOST:
        result = Math.min(result, specSize);
        break;
      case MeasureSpec.EXACTLY:
        result = specSize;
        break;
      default:
        break;
    }
    return result;
  }

  private int measureHeight(int measureSpec) {
    int result = 200;
    int specMode = MeasureSpec.getMode(measureSpec);
    int specSize = MeasureSpec.getSize(measureSpec);
    switch (specMode) {
      case MeasureSpec.UNSPECIFIED:
        result = specSize;
        break;
      case MeasureSpec.AT_MOST:
        result = Math.min(result, specSize);
        break;
      case MeasureSpec.EXACTLY:
        result = specSize;
        break;
      default:
        break;
    }
    return result;
  }

  public void setText(String text) {
    mText = text;
  }

  public void setTextSize(float textSize) {
    mPaint.setTextSize(textSize);
  }

  private int[] getTextSize() {
    if (TextUtils.isEmpty(mText)) {
      return null;
    }
    Rect rect = new Rect();
    mPaint.getTextBounds(mText, 0, mText.length(), rect);
    int w = rect.width();
    int h = rect.height();
    Log.i("TAG", "TextWidth: " + w);
    Log.i("TAG", "TextHeight: " + h);
    int[] sizeArr = new int[2];
    sizeArr[0] = w;
    sizeArr[1] = h;
    return sizeArr;
  }

  @Override
  protected synchronized void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawText(canvas);
  }

  private void drawText(Canvas canvas) {
    if (TextUtils.isEmpty(mText)) {
      return;
    }
    int[] textSize = getTextSize();
    if (textSize == null) {
      return;
    }
    if (textSize[0] > getWidth() || textSize[1] > getHeight()) {
      requestLayout();
      invalidate();
      return;
    }
    canvas.save();
    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;
    // 将坐标原点移到控件中心
    canvas.translate(centerX, centerY);
    // 绘制居中文字
    String text = mText;
    // 文字宽
    float textWidth = mPaint.measureText(text);
    // 文字baseline在y轴方向的位置
    float baseLineY = Math.abs(mPaint.ascent() + mPaint.descent()) / 2;
    canvas.drawText(text, -textWidth / 2, baseLineY, mPaint);
    canvas.restore();
  }
}
