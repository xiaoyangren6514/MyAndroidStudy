package com.happy.xyr.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.happy.xyr.R;
import com.happy.xyr.view.HookView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class AppMainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DemoAdapter demoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        ListView demoListView = (ListView) findViewById(R.id.simple_list_view);
        demoListView.setOnItemClickListener(this);

        demoAdapter = new DemoAdapter(this);
        demoListView.setAdapter(demoAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String key = demoAdapter.getItem(position);
        Intent intent = new Intent(this, demoAdapter.getValue(key));
        startActivity(intent);
    }

    public static class DemoAdapter extends BaseAdapter {

        private Map<String, Class<? extends Activity>> DEMOS
                = new HashMap<String, Class<? extends Activity>>();

        {
            DEMOS.put("HookView Demo", HookViewActivity.class);
            DEMOS.put("CircleView Demo", CircleViewActivity.class);
        }

        private String[] keys = null;

        private LayoutInflater mInflater;

        public DemoAdapter(Context context) {
            super();
            mInflater = LayoutInflater.from(context);
            Set<String> strings = DEMOS.keySet();
            keys = new String[strings.size()];
            keys = strings.toArray(keys);
        }

        @Override
        public int getCount() {
            return DEMOS.size();
        }

        public Class<? extends Activity> getValue(String key) {
            return DEMOS.get(key);
        }

        @Override
        public String getItem(int position) {
            return keys[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView name = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.simple_item, null);
                name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(name);
            } else {
                name = (TextView) convertView.getTag();
            }

            name.setText(getItem(position));
            return convertView;
        }
    }
}
