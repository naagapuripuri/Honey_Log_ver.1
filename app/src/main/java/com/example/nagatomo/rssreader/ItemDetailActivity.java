package com.example.nagatomo.rssreader;
/**
 * Created by Nagatomo on 2015/06/13.
 */
import android.app.Activity;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.widget.TextView;
import android.os.Bundle;
import android.content.Intent;
import android.text.Html;

public class ItemDetailActivity extends Activity {
    private TextView mTitle;
    private TextView mDescr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetail);

        //受け取り側のActivityは設定されてるkey(ここではTITLE)で値を取り出す
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        String descr = intent.getStringExtra("DESCRIPTION");
        CharSequence cs1 = Html.fromHtml(title);
        CharSequence cs2 = Html.fromHtml(descr);
        mTitle = (TextView) findViewById(R.id.item_detail_title);//findViewByIdでリソースIDに対応するビューのオブジェクトを取得する
       // mTitle.setText(title);  //変数titleを、mTitleオブジェクトのsetTextメソッドに渡して何かしらの結果を返す
        mDescr = (TextView) findViewById(R.id.item_detail_descr);
      //  mDescr.setText(descr);
        mTitle.setText(cs1);

        MovementMethod mMethod = LinkMovementMethod.getInstance();
        mDescr.setMovementMethod(mMethod);
        mDescr.setText(cs2);
    }
}
