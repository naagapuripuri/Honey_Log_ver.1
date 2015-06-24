package com.example.nagatomo.rssreader;

import android.os.Bundle;
import android.view.Menu;
import android.app.ListActivity;
import java.util.ArrayList;
import android.widget.ListView;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.Button;
import android.content.DialogInterface;
import android.view.View.OnClickListener;

public class RSSReaderActivity extends ListActivity {
    //  private static final String RSS_FEED_URL = "http://itpro.nikkeibp.co.jp/rss/ITpro.rdf";
    private static final String RSS_FEED_URL = "http://news.livedoor.com/topics/rss/top.xml ";
    // private static final String RSS_FEED_URL = "http://b.hatena.ne.jp/hotentry.rss";
    //private static final String RSS_FEED_URL = "https://news.google.com/news?hl=ja&ned=us&ie=UTF-8&oe=UTF-8&output=rss&topic=b";
    public static final int MENU_ITEM_RELOAD = Menu.FIRST;
    private RSSListAdapter mAdapter;
    private ArrayList<Item> mItems;
    private String[] mStrings = { "test1", "test2", "test3", "test4" };
    private Spinner spinner2;
    protected AlertDialog alertDialog;
    protected Button spinnerButton;
    protected ArrayAdapter<String> adapter;
    protected int selectedIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssreader);

        // Itemオブジェクトを保持するためのリストを生成し、アダプタに追加する
        mItems = new ArrayList<Item>();//リストに表示したい配列を用意。ArrayListクラスは大きさが決まっていない配列。ArrayList<型> 変数名 = new ArrayList<型>();のように記述。Generics機能（型の部分にはクラス名を指定し、指定したクラスのオブジェクトを要素として格納できる。）
        mAdapter = new RSSListAdapter(this, mItems);//動的配列mItemsを渡してArrayAdapterクラスのオブジェクトを作成。

        // タスクを起動する
        RSSParserTask task = new RSSParserTask(this, mAdapter);
        task.execute(RSS_FEED_URL);   //AsyncTaskはインスタンスのexecuteメソッドでバックグラウンド処理を開始します。







        spinnerButton = (Button) findViewById(R.id.button);
        spinnerButton.setText("category");
        spinnerButton.setOnClickListener(onClickListener);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice);
        adapter.add("国内");
        adapter.add("海外");
        adapter.add("IT/経済");
        adapter.add("芸能");
        adapter.add("スポーツ");
        adapter.add("映画");
        adapter.add("グルメ");
        adapter.add("女子");
        adapter.add("トレンド");

/*
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mStrings);
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setPrompt("test一覧");
        spinner2.setAdapter(adapter);
    //    adapter.add("国内");
    //    adapter.add("海外");
    //    adapter.add("IT/経済");
    //    adapter.add("芸能");
    //    adapter.add("スポーツ");
    //    adapter.add("映画");
        spinner2.setSelection(0);
       // spinner2.setSelection(2);
       // spinner2.setPrompt("category");
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Itemが選択された時
            public void onItemSelected(AdapterView parent,
                                       View view, int position, long id) {
                //parentのspinnerを指定
                Spinner spinner2 = (Spinner) parent;
                //選択されたitemを取得
                String item = (String) spinner2.getSelectedItem();
                //Toast表示
                Toast.makeText(RSSReaderActivity.this,
                        String.format("%sが選択されました。", item),
                        Toast.LENGTH_SHORT).show();
            }
            //何も選択されなかったとき
            public void onNothingSelected(AdapterView parent) {
                Toast.makeText(RSSReaderActivity.this,
                        "何も選択されませんでした", Toast.LENGTH_SHORT).show();
            }
        });
*/
    }
    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // AlertDialogで選択肢を表示
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    RSSReaderActivity.this);
            builder.setTitle("category");
            builder.setSingleChoiceItems(adapter, selectedIndex, onDialogClickListener);
            alertDialog = builder.create();
            alertDialog.show();
        }
    };
    private DialogInterface.OnClickListener onDialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // AlertDialogで選択された内容を保持
            selectedIndex = which;
            spinnerButton.setText(adapter.getItem(which));
            alertDialog.dismiss();
            if(which == 0){
                Intent intent = new Intent(getApplicationContext(), RSSReaderJapanActivity.class);
                startActivity(intent);
                // SplashActivityを終了する
                RSSReaderActivity.this.finish();
            }
            else if(which == 3){
                Intent intent = new Intent(getApplicationContext(), RSSReaderArtistActivity.class);
                startActivity(intent);
                // SplashActivityを終了する
                RSSReaderActivity.this.finish();
            }
            else if(which == 4){
                Intent intent = new Intent(getApplicationContext(), RSSReaderSportActivity.class);
                startActivity(intent);
                // SplashActivityを終了する
                RSSReaderActivity.this.finish();
            }
            else if(which == 5){
                Intent intent = new Intent(getApplicationContext(), RSSReaderMovieActivity.class);
                startActivity(intent);
                // SplashActivityを終了する
                RSSReaderActivity.this.finish();
            }
            else if(which == 6){
                Intent intent = new Intent(getApplicationContext(), RSSReaderGourmetActivity.class);
                startActivity(intent);
                // SplashActivityを終了する
                RSSReaderActivity.this.finish();
            }
            else if(which == 7){
                Intent intent = new Intent(getApplicationContext(), RSSReaderGirlsActivity.class);
                startActivity(intent);
                // SplashActivityを終了する
                RSSReaderActivity.this.finish();
            }

        }
    };





    // リストの項目を選択した時の処理
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item item = mItems.get(position);
        //以下4行で、他のActivity起動時に値を渡す
        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra("TITLE", item.getTitle());//第一引数key、第二引数渡したい値
        intent.putExtra("DESCRIPTION", item.getDescription());
        intent.putExtra("PUBDATE", item.getPubDate());
        startActivity(intent);
    }


    //////////////////////////////////////////////////////////////////////////////




/*
    // MENUボタンを押したときの処理
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        // デフォルトではアイテムを追加した順番通りに表示する
        menu.add(0, MENU_ITEM_RELOAD, 0, "更新");
        return result;
    }


    // MENUの項目を押したときの処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 更新
            case MENU_ITEM_RELOAD:
                // アダプタを初期化し、タスクを起動する
                mItems = new ArrayList();
                mAdapter = new RSSListAdapter(this, mItems);
                // タスクはその都度生成する
                RSSParserTask task = new RSSParserTask(this, mAdapter);
                task.execute(RSS_FEED_URL);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}*/

}
/*
public class RSSReaderActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssreader);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rssreader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
*/