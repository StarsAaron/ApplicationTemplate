package com.aaron.utils;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import static android.R.attr.path;

/**
 * 声音工具
 */
public class AudioUtils {
    private static int SAMPLE_RATE_IN_HZ = 8000; // 采样率

    private MediaRecorder mMediaRecorder;
    private String mPath;

    /**
     *
     * @param name 录音名
     */
    public AudioUtils(String name) {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        this.mPath = sanitizePath(name);
    }

    /**
     * 构造保存路径
     * @param name
     * @return
     */
    private String sanitizePath(String name) {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/voiceRecord/" + name + ".amr";
    }

    /**
     * 录音开始
     * @throws IOException
     */
    public void start() throws IOException {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            throw new IOException("SD Card is not mounted,It is  " + state
                    + ".");
        }
        File directory = new File(mPath).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Path to file could not be created");
        }
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        // recorder.setAudioChannels(AudioFormat.CHANNEL_CONFIGURATION_MONO);
        mMediaRecorder.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);
        mMediaRecorder.setOutputFile(mPath);
        mMediaRecorder.prepare();
        mMediaRecorder.start();
    }

    /**
     * 录音暂停
     * @throws IOException
     */
    public void stop() throws IOException {
        mMediaRecorder.stop();
        mMediaRecorder.release();
    }

    /**
     *
     * @return
     */
    public double getAmplitude() {
        if (mMediaRecorder != null) {
            return (mMediaRecorder.getMaxAmplitude());
        } else {
            return 0;
        }
    }
}
