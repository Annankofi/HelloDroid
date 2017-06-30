package com.hello.droid;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String PACKAGE_NAME_PREFIX = "com.hello";

    private static final String mTestOne = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new SimpleAdapter(this, getData(PACKAGE_NAME_PREFIX), android.R.layout.simple_list_item_1,
                new String[]{"title"}, new int[]{android.R.id.text1}));
        // locate it through keyboard
        getListView().setTextFilterEnabled(true);
    }


    // click and launch testActivity
    @Override
    protected void onListItemClick(ListView listView, View v, int position, long id) {
        Map<String, ?> map = (Map<String, ?>) listView.getItemAtPosition(position);
        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
        super.onListItemClick(listView, v, position, id);
    }

    // filter CATEGORY_TEST nad  packageName
    List<Map<String, ?>> getData(String prefix) {
        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        // Manifest add: <category android:name="android.intent.category.TEST"/>
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_TEST);
        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);
        if (list == null) {
            return data;
        }

        int len = list.size();
        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            String activityName = info.activityInfo.name;
            //可以只显示某个测试项
            if (prefix.length() == 0 || activityName.startsWith(prefix) &&(mTestOne.isEmpty() || (activityName.contains(mTestOne)))) {
                String[] labPath = activityName.split("\\.");
                String nextLable = labPath[labPath.length - 1];
                Intent intent = activityIntent(info.activityInfo.applicationInfo.packageName, info.activityInfo.name);
                addToItem(data, nextLable, intent);
            }
        }
        // sorted by name
        Collections.sort(data, DisplayByName);
        return data;
    }

    protected void addToItem(List<Map<String, ?>> data, String title, Intent intent) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", title);
        map.put("intent", intent);
        data.add(map);
    }

    protected Intent activityIntent(String pkg, String componentName) {
        Intent intent = new Intent();
        intent.setClassName(pkg, componentName);
        return intent;
    }

    private Comparator<Map<String, ?>> DisplayByName = new Comparator<Map<String, ?>>() {
        private final Collator collator = Collator.getInstance();

        public int compare(Map<String, ?> map1, Map<String, ?> map2) {
            return collator.compare(map1.get("title"), map2.get("title"));
        }
    };
}
