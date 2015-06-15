package com.example.nagatomo.rssreader;

/**
 * Created by Nagatomo on 2015/06/15.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SplashActivity extends Activity {

    Handler mHandler = new Handler();
    ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_view);
        // スプラッシュ用のビューを取得する
        //  setContentView(R.layout.splash_view);
        imageview = (ImageView) findViewById(R.id.background);

        // 2秒したらMainActivityを呼び出してSplashActivityを終了する
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // MainActivityを呼び出す
                Intent intent = new Intent(getApplicationContext(),
                        RSSReaderActivity.class);
                startActivity(intent);
                // SplashActivityを終了する
                SplashActivity.this.finish();
            }
        }, 3 * 1000); // 2000ミリ秒後（2秒後）に実行
    }
}