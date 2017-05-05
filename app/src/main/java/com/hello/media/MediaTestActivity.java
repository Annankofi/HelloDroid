package com.hello.media;

import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hello.droid.BaseListActivity;
import com.hello.droid.Info;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaTestActivity extends BaseListActivity {
    private static final String TAG = MediaTestActivity.class.getSimpleName();
    private AudioManager mAudioManager;
    private boolean mIsAudioFocusLossPermanent =false;
    private enum DataItems {
        PLAY_SONG, AUDIOFOCUS_GAIN,AUDIOFOCUS_GAIN_TRANSIENT,AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK,AUDIOFOCUS_ABANDON
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
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

        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            case PLAY_SONG:
                Intent intent = new Intent("android.intent.action.VIEW");
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("oneshot", 0);
                intent.putExtra("configchange", 0);
                Uri uri = Uri.fromFile(new File("/mnt/internal_sd/AnnanMusic.mp3"));
                intent.setDataAndType(uri, "audio/*");
                startActivity(intent);
                break;
            case AUDIOFOCUS_GAIN:
                mAudioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
                break;
            case AUDIOFOCUS_GAIN_TRANSIENT:
                mAudioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                break;
            case AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                mAudioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
                break;
            case AUDIOFOCUS_ABANDON:
                if (mAudioManager != null) {
                    mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
                }
                break;
            default:
                break;
        }

        super.onListItemClick(listView, v, position, id);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemLongClick");
        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            case PLAY_SONG:
                break;
            default:
                break;
        }
        //return true and will not deal with short click event
        return true;
    }

    AudioManager.OnAudioFocusChangeListener   mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener(){
        @Override
        public void onAudioFocusChange(int focusChange) {
            Log.d(TAG, "onAudioFocusChange " + focusChange + " mIsAudioFocusLossPermanent "+mIsAudioFocusLossPermanent);
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS://-1
                    mIsAudioFocusLossPermanent= true;
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT://-2
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK://-3
                    mIsAudioFocusLossPermanent= false;
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    break;
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT://2
                    break;
                default:
                    break;
            }
        }
    };
}
