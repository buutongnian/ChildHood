package edu.buu.childhood;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by lcc on 2016/6/25.
 */
public class Ranking_list extends Activity {
    private ListView listView;
    private List<ranking_list_itembean> rankDatalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_list);
        listView=(ListView) findViewById(R.id.ranking_list_listview);
        rankDatalist = new ArrayList<ranking_list_itembean>();
        rankDatalist.add(new ranking_list_itembean(R.drawable.texts,"1","刘诚诚","3000"));
        listView.setAdapter(new Ranking_list_listview_Adapter(this,rankDatalist));
    }
}
