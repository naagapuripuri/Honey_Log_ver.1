package com.example.nagatomo.rssreader;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nagatomo on 2015/06/21.
 */
public class RSSListGourmetAdapter extends ArrayAdapter<Item> {
    private LayoutInflater mInflater;
    private TextView mTitle;
    private TextView mDescr;
    private TextView mPubDate;
    //コンストラクタ
    public RSSListGourmetAdapter(Context context, List<Item> objects) {//ArrayAdapter クラスのコンストラクターで、使わない引数（textViewResourceId）の分を除いたコンストラクターを作っておく。基本的に、オブジェクトのリストか、オブジェクトの配列を受け取れるようにすればよい。
        super(context, 0, objects);// 第2引数はtextViewResourceIdとされていますが、カスタムリストアイテムを使用する場合は特に意識する必要のない引数です。ArrayAdapter クラスのコンストラクターを呼び出す。このとき、textViewResourceId は指定されていないので、0 にしておく。
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// レイアウトXML自体をソースコード上にViewとして、 システムサービスからLayoutInflaterを取得
    }

    // 1行ごとのビューを生成する
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {// ListViewに表示する分のレイアウトが生成されていない場合レイアウトを作成する。convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る。convertViewがnullだった場合のみLayoutInflaterを利用して、activity_itemrow.xmlからビューを取得する
            view = mInflater.inflate(R.layout.activity_itemrow, null);
        }

        Item item = this.getItem(position);    // position行目のデータを取得する。特定の行(position)のデータを得る// 現在参照しているリストの位置からItemを取得する
        if (item != null) {
            // Itemから必要なデータを取り出し、それぞれTextViewにセットする
            String title = item.getTitle().toString();
            mTitle = (TextView) view.findViewById(R.id.item_title);
            mTitle.setText(title);
            mTitle.setTextSize(11);
            mTitle.setHeight(11);
            mTitle.setMinimumHeight(11);
            mTitle.setTextColor(Color.parseColor("blue"));
            String descr = item.getDescription().toString();//インスタンスに格納されたデータを文字列表現にする。
            mDescr = (TextView) view.findViewById(R.id.item_descr);//findViewByIdでリソースIDに対応するビューのオブジェクトを取得する
            mDescr.setText(descr);//変数descrを、mDescrオブジェクトのsetTextメソッドに渡して何かしらの結果を返す
            mDescr.setVisibility(View.GONE);
            String pubdate = item.getPubDate().toString();
            pubdate = pubdate.replaceAll("\\+0900", "");
            mPubDate = (TextView) view.findViewById(R.id.item_pubdate);
            mPubDate.setText(pubdate);
            mPubDate.setTextSize(8);
            mPubDate.setTextColor(Color.parseColor("black"));
        }
        return view;//viewを返す
    }
}

