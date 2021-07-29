package com.rifeng.p2p.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.util.List;

public class VoiceUtils {

    private Context mContext;
    private MediaPlayer mBackgroundMediaPlayer;
    private AudioManager am;

    public VoiceUtils(Context context) {
        this.mContext = context;
        initData();
    }

    // 初始化一些数据
    private void initData() {
        am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mBackgroundMediaPlayer = null;
    }

    /**
     * 根据path路径播放背景音乐
     *
     * @param path :assets中的音频路径
     */
    public void playVoice(String path) {
        if (mBackgroundMediaPlayer == null) {
            // 这是第一次播放背景音乐--- it is the first time to play background music
            // 或者是执行end()方法后，重新被叫---or end() was called
            mBackgroundMediaPlayer = createMediaplayerFromAssets(path);
        } else {
            // 播放一个新的背景音乐--- play new background music
            // 释放旧的资源并生成一个新的----release old resource and create a new one
            if (mBackgroundMediaPlayer != null) {
                mBackgroundMediaPlayer.release();
            }
            mBackgroundMediaPlayer = createMediaplayerFromAssets(path);
        }

        if (mBackgroundMediaPlayer != null) {

            mBackgroundMediaPlayer.seekTo(0);
            mBackgroundMediaPlayer.start();
        }
    }

    /**
     * 根据path路径播放背景音乐
     *
     * @param paths :assets中的音频路径
     */
    public void playVoice(final List<String> paths) {

        if (paths == null || paths.size() == 0) {
            return;
        }

        if (mBackgroundMediaPlayer == null) {
            // 这是第一次播放背景音乐--- it is the first time to play background music
            // 或者是执行end()方法后，重新被叫---or end() was called
            mBackgroundMediaPlayer = createMediaplayerFromAssets(paths.get(0));
        } else {
            // 播放一个新的背景音乐--- play new background music
            // 释放旧的资源并生成一个新的----release old resource and create a new one
            if (mBackgroundMediaPlayer != null) {
                mBackgroundMediaPlayer.release();
            }
            mBackgroundMediaPlayer = createMediaplayerFromAssets(paths.get(0));
        }

        if (mBackgroundMediaPlayer != null) {

            mBackgroundMediaPlayer.seekTo(0);
            mBackgroundMediaPlayer.start();
            mBackgroundMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (paths.size() > 0) {
                        paths.remove(0);
                        playVoice(paths);
                    }
                }
            });
        }
    }

    /**
     * 停止播放背景音乐
     */
    public void stopVoice() {
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.stop();
            // should set the state, if not , the following sequence will be
            // error
            // play -> pause -> stop -> resume
            end();
        }
    }

    /**
     * 暂停播放背景音乐
     */
    public void pauseBackgroundMusic() {
        if (mBackgroundMediaPlayer != null
                && mBackgroundMediaPlayer.isPlaying()) {
            mBackgroundMediaPlayer.pause();
        }
    }


    /**
     * 判断背景音乐是否正在播放
     *
     * @return：返回的boolean值代表是否正在播放
     */
    public boolean isBackgroundMusicPlaying() {
        boolean ret = false;
        if (mBackgroundMediaPlayer == null) {
            ret = false;
        } else {
            ret = mBackgroundMediaPlayer.isPlaying();
        }
        return ret;
    }

    /**
     * 结束背景音乐，并释放资源
     */
    public void end() {
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.release();
        }
        // 重新“初始化数据”
        initData();
    }

    /**
     * create mediaplayer for music
     *
     * @param path the path relative to assets
     * @return
     */
    private MediaPlayer createMediaplayerFromAssets(String path) {
        MediaPlayer mediaPlayer = null;
        try {
            AssetFileDescriptor assetFileDescritor = mContext.getAssets()
                    .openFd(path);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(assetFileDescritor.getFileDescriptor(),
                    assetFileDescritor.getStartOffset(),
                    assetFileDescritor.getLength());
            mediaPlayer.prepare();

        } catch (Exception e) {
            mediaPlayer = null;
            e.printStackTrace();
        }
        return mediaPlayer;
    }

    public void restoreStreamVolume() {

//        am.setStreamVolume(AudioManager.STREAM_MUSIC, MyApp.getmInstance().currentVolume, 0);

    }

}