package com.hello.regular;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hello.droid.BaseListActivity;
import com.hello.droid.Info;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularTestActivity extends BaseListActivity {
    private static final String TAG = RegularTestActivity.class.getSimpleName();

    private enum DataItems {
        UNKNOWN,SPLIT;
    }

    @Override
    protected List<Info> buildDatas() {
        List<Info> datas = new ArrayList<Info>();
        for (DataItems e : DataItems.values()) {
            Info info = new Info(e.toString(), "");
            datas.add(info);
        }
        return datas;
    }

    @Override
    protected void onListItemClick(ListView listView, View v, int position, long id) {
        Log.d(TAG, "onListItemClick " + mHeaderAdapter.getData().get(position).getTitle());
        Pattern pattern;
        Matcher matcher;
        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            case UNKNOWN:
                 pattern = Pattern.compile("^Java.*");
                 matcher = pattern.matcher("Java code");
                //true
                Log.d(TAG, "find result: " + matcher.matches());
                //Java code
                Log.d(TAG, "find group :" + matcher.group());
                matcher = pattern.matcher("aJava code");
                //false
                Log.d(TAG, "find result :" + matcher.matches());
                // java.lang.IllegalStateException: No successful match so far
               // Log.d(TAG, "find group :" + matcher.group());


                break;
            case SPLIT:
                //splict by , space and |
//                 pattern = Pattern.compile("[,|]+");
//                String[] strs = pattern.split("Java Hello World  Java,Hello,,World|Sun");
//                for (int i = 0; i < strs.length; i++) {
//                    Log.d(TAG, "split: " + strs[i]);
//                }
                pattern = Pattern.compile("Hello");
                matcher = pattern.matcher("Hello World,Hello Annan");
                //result:Good World,Hello Annan
                Log.d(TAG, "replace result:" + matcher.replaceFirst("Good"));

                pattern = Pattern.compile("Hello");
                matcher = pattern.matcher("Hello World, Hello World");
                //result:Good World, Good World
                Log.d(TAG, "replace result:" + matcher.replaceAll("Good"));


                pattern = Pattern.compile("Hello");
                matcher = pattern.matcher("Hello World,Hello World ");
                StringBuffer sbr = new StringBuffer();
                while (matcher.find()) {
                    matcher.appendReplacement(sbr, "Java");
                }
                matcher.appendTail(sbr);
                //Java World,Java World
                Log.d(TAG, "replace result:" + sbr.toString());
                break;
            default:
                break;
        }

        super.onListItemClick(listView, v, position, id);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemLongClick " + mHeaderAdapter.getData().get(position).getTitle());
        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            case UNKNOWN:
                break;
            case SPLIT:
                break;

            default:
                break;
        }
        //return true and will not deal with short click event
        return true;
    }
}
