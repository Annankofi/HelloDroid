package com.hello.thread;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hello.droid.BaseListActivity;
import com.hello.droid.Info;

import java.util.ArrayList;
import java.util.List;


public class ThreadTestActivity extends BaseListActivity {
    private static final String TAG = ThreadTestActivity.class.getSimpleName();

    private enum DataItems {
        JOIN, INTERRUPT
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
        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            case JOIN:
                AnnanThread threadA = new AnnanThread("ThreadA");
                AnnanThread threadB = new AnnanThread("ThreadB");
                threadA.start();
                try {
                    Log.d(TAG,"threadA join");
                    //block here and waiting threadA to finish
                    threadA.join();
                } catch (InterruptedException e) {
                    Log.d(TAG,"threadA InterruptedException");
                    e.printStackTrace();
                }
                threadB.start();
                break;
            case INTERRUPT:

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
            default:
                break;
        }
        //return true and will not deal with short click event
        return true;
    }

    public class AnnanThread extends  Thread{
        public AnnanThread(String name){
            this.setName(name);
        }
        @Override
        public void run() {
            for(int i=0;i<10;i++){
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG,this.getName()+" "+i);
            }
            super.run();
        }
    }
}
