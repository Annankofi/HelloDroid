package com.hello.system;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.List;

public class CleanMaster {

    private static final String TAG = CleanMaster.class.getSimpleName();
    public static final int REMOVE_TASK_KILL_PROCESS = 0x0001;
    private Method mRemoveTask;

    // note:you will get com.android.launcher3 and com.yealink.phone
    private void doCleanAppsByRecentTask(Context context) {
        PackageManager pm = context.getPackageManager();
        List<RecentTaskInfo> recentTasks = getRecentTaskList(context);
        for (RecentTaskInfo task : recentTasks) {
            Intent intent = new Intent(task.baseIntent);
            // task.origActivity may be null
            if (task.origActivity != null) {
                intent.setComponent(task.origActivity);
            }
            String pkgName = intent.getComponent().getPackageName();
            ActivityInfo homeInfo = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME)
                    .resolveActivityInfo(pm, 0);
            // skip the launcher
            if (isCurrentHomeActivity(context, intent.getComponent(), homeInfo)) {
                Log.d(TAG, "Removetask and skip HomeActivity: " + intent.getComponent().getPackageName());
                continue;
            }
            // skip the current task com.yealink.phone
            if (pkgName.equals("com.android.launcher3") || pkgName.equals("com.yealink.phone")
                    || pkgName.equals("com.yealink.setting") || pkgName.equals("com.yealink.testmode")) {
                Log.d(TAG, "Removetask and skip " + intent.getComponent().getPackageName());
                continue;
            } else {
                Log.d(TAG, "Removetask " + pkgName);
                if (!removeTask(context, task.persistentId, REMOVE_TASK_KILL_PROCESS)) {
                    Log.d(TAG, "Removetask " + pkgName + " failed");
                }
            }
        }
    }

    private boolean isCurrentHomeActivity(Context context, ComponentName component, ActivityInfo homeInfo) {
        if (homeInfo == null) {
            final PackageManager pm = context.getPackageManager();
            homeInfo = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).resolveActivityInfo(pm, 0);
        }
        return homeInfo != null && homeInfo.packageName.equals(component.getPackageName())
                && homeInfo.name.equals(component.getClassName());
    }

    // @RECENT_WITH_EXCLUDED = 0x0001;
    // return all tasks, even those that have set their
    // FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS flag.
    // @RECENT_IGNORE_UNAVAILABLE = 0x0002;
    // Provides a list that does not contain any recent tasks that currently are
    // not available to the user.
    // here:do no get task if set as FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS or
    // config
    // android:excludeFromRecents="true"
    public List<RecentTaskInfo> getRecentTaskList(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RecentTaskInfo> recentTasks = null;
        try {
            recentTasks = am.getRecentTasks(64,
                    ActivityManager.RECENT_IGNORE_UNAVAILABLE | ActivityManager.RECENT_WITH_EXCLUDED);
            // ActivityManager.RECENT_IGNORE_UNAVAILABLE);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return recentTasks;
    }

    // flags Additional operational flags. May be 0 or REMOVE_TASK_KILL_PROCESS
    // remove success:android.rk.RockVideoPlayer but remove
    // failed:com.android.rk.mediafloat
    public boolean removeTask(Context context, int taskId, int flags) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (mRemoveTask == null) {
            try {
                Class<?> activityManagerClass = Class.forName("android.app.ActivityManager");
                mRemoveTask = activityManagerClass.getMethod("removeTask", new Class[] { int.class, int.class });
                mRemoveTask.setAccessible(true);
            } catch (Exception e) {
                Log.e(TAG, "General Exception occurred", e);
            }
        }
        try {
            return (Boolean) mRemoveTask.invoke(am, Integer.valueOf(taskId), Integer.valueOf(flags));
        } catch (Exception ex) {
            Log.e(TAG, "Task removal failed", ex);
        }
        return false;
    }
}
