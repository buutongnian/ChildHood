package edu.buu.childhood.baidumap.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.buu.childhood.R;

/**
 * Created by lcc on 2016/7/4.
 */
public class teamerName extends Activity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teamer_name);
        listView= (ListView) findViewById(R.id.teamer_name_listview);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,getData(),R.layout.teamer_name_model,
                new String[]{"name","img"},
                new int[]{R.id.teamer_name_model_text,R.id.teamer_name_model_image});
        listView.setAdapter(simpleAdapter);
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "G1");
        map.put("img", R.drawable.touxiang);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("name", "G2");
        map.put("img", R.drawable.touxiang);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("name", "G3");
        map.put("img", R.drawable.touxiang);
        list.add(map);
        return list;
    }
}

