package com.yang.keyboard.audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.yang.keyboard.utils.HadLog;


/**
 * Created by yangjiang on 2017/04/07.
 * E-Mail:1007181167@qq.com
 * Description:[音频录音和播放管理器]
 **/
public class AudioManger {
    private final String TAG = getClass().getSimpleName();
    private volatile static AudioManger instance = null;
    private MediaRecorder recorder;
    private String mPath;//音频路径
    private int mPeriod = 0;///隐音频的时长
    private static final int MIN_LENGTH = 2;

    public static AudioManger getInstance() {
        if (instance == null) {
            synchronized (AudioManger.class) {
                if (instance == null) {
                    instance = new AudioManger();
                }
            }
        }
        return instance;
    }

    public AudioManger() {
        new Timer().schedule(new AudioTimerTask(), 0, 1000);
    }

    private class AudioTimerTask extends TimerTask {

        @Override
        public void run() {
            ++mPeriod;
        }
    }

    public synchronized void start(String path, OnAudioListener listener) {
        HadLog.d(TAG, "start recording");
        mPeriod = 0;
        mListener = listener;
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 设置文件音频的输出格式为aac
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        // 设置音频的编码格式为aac
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        // .m4a 格式可以在 iOS 上直接播放
        //recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setOutputFile(path);

        try {
            recorder.prepare();
            recorder.start();
            updateMicStatus();
            HadLog.d(TAG, "record start success");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            HadLog.e(TAG, "IllegalStateException");
        } catch (IOException e) {
            e.printStackTrace();
            HadLog.e(TAG, "IOException:" + e.getMessage());
        }

        mPath = path;

    }

    /**
     * cancel, not save the file
     *
     * @return true, cancel success, false, cancel failed
     */
    public synchronized boolean cancel() {
        HadLog.d(TAG, "cancel recording");
        if (recorder == null) {
            HadLog.e(TAG, "recorder is null ");
            return false;
        }
        try {
            stopRecord();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            HadLog.e(TAG, "illegal state happened when cancel");
        }

        File file = new File(mPath);
        return file.exists() && file.delete();
    }


    /**
     * complete the recording
     *
     * @return recording last time
     */
    public synchronized int complete() {
        HadLog.i(TAG, "complete recording");
        if (recorder == null) {
            HadLog.e(TAG, "recorder is null ");
            return -1;
        }

        try {
            stopRecord();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            HadLog.e(TAG, "illegal state happened when complete");
            return -1;
        }

        if (mPeriod < MIN_LENGTH) {
            HadLog.i(TAG, "record time is too short");
            return -1;
        }

        return mPeriod;
    }

    public String generatePath(Context context) {
        boolean isSuccess = true;
        final String CACHE_DIR_NAME = "audioCache";
        final String cachePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + CACHE_DIR_NAME;
        File file = new File(cachePath);
        if (!file.exists()) {
            isSuccess = file.mkdirs();
        }
        if (isSuccess) {
            return cachePath + File.separator + System.currentTimeMillis() + ".aac";
        } else {
            return null;
        }
    }

    private synchronized void stopRecord() throws IllegalStateException {
        if (mHandler != null) {
            mHandler.removeCallbacks(mUpdateMicStatusTimer);
        }
        recorder.reset();
        recorder.release();
        recorder = null;
    }

    private final Handler mHandler = new Handler();
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };

    private void updateMicStatus() {
        if (recorder != null) {
            double ratio = (double) recorder.getMaxAmplitude();
            double db = 0;
            if (ratio > 1)
                db = 20 * Math.log10(ratio);
            if (mListener != null) {
                mListener.onDbChange(db);
            }
            mHandler.postDelayed(mUpdateMicStatusTimer, 500);
        }
    }

    private OnAudioListener mListener = null;

    public interface OnAudioListener {
        void onDbChange(double db);
    }

    private MediaPlayer mMediaPlayer = null;
    private String mCurrentPlayingAudioPath = null;
    private OnMediaPlayComplete mPlayListener = null;

    /**
     * play the audio
     *
     * @param path     path of the audio file
     * @param listener 接口
     */
    public synchronized void playAudio(String path, OnMediaPlayComplete listener) {
        mPlayListener = listener;
        if (mMediaPlayer != null) {
            stopPlay();
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
                if (mPlayListener != null) {
                    mPlayListener.onPlayComplete(true);
                }
            }
        });
        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            mPlayListener.onPlayComplete(false);
        }

        mMediaPlayer.start();

        mCurrentPlayingAudioPath = path;
    }

    public synchronized void stopPlay() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
        mCurrentPlayingAudioPath = null;
    }

    public boolean isPlaying(String path) {
        return (mMediaPlayer != null) && mMediaPlayer.isPlaying() && (path.equals(mCurrentPlayingAudioPath));
    }

    public interface OnMediaPlayComplete {
        void onPlayComplete(boolean isSuccess);
    }

    public String getmPath() {
        return mPath;
    }


}
