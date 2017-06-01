package com.hello.cases.thread;


import android.support.test.espresso.core.deps.guava.collect.Lists;
import android.util.Log;

import com.hello.cases.model.Apple;

import java.util.List;

public class ConsumeAndProductThread {
    private static final String TAG = ConsumeAndProductThread.class.getSimpleName();

    private List<Apple> mList = Lists.newArrayList();

    public void start() {
        new Thread(new Consumer("C1", mList)).start();
        new Thread(new Producer("P1", mList)).start();
//        new Thread(new Consumer("C2", mList)).start();
//        new Thread(new Producer("P2", mList)).start();
//        new Thread(new Consumer("C3", mList)).start();
//        new Thread(new Producer("P3", mList)).start();
    }


    class Consumer implements Runnable {
        private List<Apple> mList;
        private String mName;

        public Consumer(String name, List<Apple> list) {
            mList = list;
            mName = name;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (mList) {
                    Log.d(TAG, mName+" goto synchronized ");
                    if (mList.isEmpty()) {
                    //while  (mList.isEmpty()) {
                        Log.d(TAG, mName + " consume but it is empty and wait");
                        try {
                            mList.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    Log.d(TAG, mName + " run and consume apple:" + mList.get(0).getName());
                    mList.remove(0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mList.notify();
                    Log.d(TAG, mName+" leave synchronized ");
                }
            }
        }
    }

    private static int appleId = 0;

    class Producer implements Runnable {
        private List<Apple> mList;
        private int MAX_COUNT = 5;
        private String mName;

        public Producer(String name, List<Apple> list) {
            mName = name;
            mList = list;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (mList) {
                    Log.d(TAG, mName+" goto synchronized ");
                   if (mList.size() == MAX_COUNT) {
                    //while (mList.size() == MAX_COUNT) {
                        Log.d(TAG, mName + " product but it is full and wait");
                        try {
                            //if wait and get notify and lock ,the code will run from here
                            mList.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    appleId++;
                    mList.add(new Apple(String.valueOf(appleId)));
                    Log.d(TAG, mName + " run and produce apple:" + mList.get(mList.size() - 1).getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mList.notify();
                    Log.d(TAG, mName+" leave synchronized ");
                }
            }
        }
    }

}
