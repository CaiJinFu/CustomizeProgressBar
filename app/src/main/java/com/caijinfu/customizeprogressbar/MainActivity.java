package com.caijinfu.customizeprogressbar;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

  private ProgressBar mPbHori;
  private ProgressBar mPbHoriSend;
  private ProgressBar mPbHoriDefault;
  private CoolProgressBar mCPbHori;
  private int mProgress = 10;

  private Handler mHandler = new Handler();
  private Runnable mRunnable =
      new Runnable() {
        @Override
        public void run() {
          mProgress++;
          mCPbHori.setProgress(mProgress);
          NumberFormat percentInstance = NumberFormat.getPercentInstance();
          String text;
          if (mProgress == 100) {
            text = "加载完成";
          } else {
            text = percentInstance.format(mProgress / 100.0);
          }
          mCPbHori.setText(text);
          mPbHoriDefault.setProgress(mProgress);
          mPbHori.setProgress(mProgress);
          mPbHoriSend.setProgress(mProgress);
          mPbHoriSend.setSecondaryProgress(mProgress + 10);
          mHandler.postDelayed(this, 200);
        }
      };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mPbHori = findViewById(R.id.pb_hori);
    mPbHoriSend = findViewById(R.id.pb_hori_send);
    mPbHoriDefault = findViewById(R.id.pb_hori_default);
    mCPbHori = findViewById(R.id.cpb_hori);
    // 每200毫秒执行一次runnable.
    mHandler.postDelayed(mRunnable, 200);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mHandler.removeCallbacks(mRunnable);
  }
}
