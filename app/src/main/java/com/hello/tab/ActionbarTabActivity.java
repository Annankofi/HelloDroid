package com.hello.tab;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hello.droid.R;


public class ActionbarTabActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionbar_tab);
        ActionBar actionBar = getActionBar();
      //  actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
       // actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayOptions(0);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab edit = actionBar.newTab().setText("Tab B");
        ActionBar.Tab computer = actionBar.newTab().setText("Tab A");
        MyTabListener editListener = new MyTabListener(new FragmentA());
        //if not set tablistener will throw an exception
        edit.setTabListener(editListener);
        MyTabListener computerListener = new MyTabListener(new FragmentA());
        computer.setTabListener(computerListener);
        actionBar.addTab(edit);
        actionBar.addTab(computer);
        //if hide the tab and actionbar both are hidden
        actionBar.hide();
    }

    class MyTabListener implements ActionBar.TabListener {
        private Fragment fragment;

        public MyTabListener(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            //ft.replace(R.id.main, fragment, null);
        }


        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }

    }

}
