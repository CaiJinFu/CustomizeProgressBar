package com.caijinfu.customizeprogressbar;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.DimenRes;

public class DensityUtil {

  /**
   * dp转px
   *
   * @param context
   * @param dpVal
   * @return
   */
  public static int dp2px(Context context, float dpVal) {
    return (int)
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
  }

  /**
   * sp转px
   *
   * @param context
   * @param spVal
   * @return
   */
  public static int sp2px(Context context, float spVal) {
    return (int)
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
  }

  /**
   * px转dp
   *
   * @param context
   * @param pxVal
   * @return
   */
  public static int px2dp(Context context, float pxVal) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxVal / scale + 0.5f);
  }

  /**
   * px转sp
   *
   * @param context
   * @param pxVal
   * @return
   */
  public static float px2sp(Context context, float pxVal) {
    return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
  }

  /** 根据id获取尺寸 */
  public static int getDimens(Context context, @DimenRes int id) {
    return context.getResources().getDimensionPixelSize(id);
  }
}
