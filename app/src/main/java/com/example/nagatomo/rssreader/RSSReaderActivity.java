package com.example.nagatomo.rssreader;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ListActivity;
import java.util.ArrayList;
import android.widget.ListView;
import android.view.View;
import android.content.Intent;

public class RSSReaderActivity extends ListActivity {
    //  private static final String RSS_FEED_URL = "http://itpro.nikkeibp.co.jp/rss/ITpro.rdf";
    private static final String RSS_FEED_URL = "http://news.livedoor.com/topics/rss/love.xml ";
    // private static final String RSS_FEED_URL = "http://b.hatena.ne.jp/hotentry.rss";
    public static final int MENU_ITEM_RELOAD = Menu.FIRST;
    private RSSListAdapter mAdapter;
    private ArrayList<Item> mItems;

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
    }

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