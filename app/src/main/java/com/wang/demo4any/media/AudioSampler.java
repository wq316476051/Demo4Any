package com.wang.demo4any.media;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;

public class AudioSampler {

    private static final String TAG = "AudioSampler";

    private String mFilePath;
    private MediaExtractor mMediaExtractor;
    private MediaCodec mMediaCodec;
    private String mMimeType;
    private MediaFormat mMediaFormat;
    private MediaFormat mOutputMediaFormat;

    public AudioSampler(String filePath) {
        mFilePath = filePath;
    }
    
    public void sample() {
        preSample();
        doSample();
        postSample();
    }

    private void preSample() {
        try {
            createMediaExtractor();
            createMediaCodec();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doSample() {
        mMediaCodec.setCallback(new MediaCodec.Callback() {
            @Override
            public void onInputBufferAvailable(MediaCodec codec, int index) {
                ByteBuffer inputBuffer = codec.getInputBuffer(index);

                mMediaExtractor.release();

                codec.queueInputBuffer(index, 0, 100, 11111, MediaCodec.BUFFER_FLAG_CODEC_CONFIG);
            }

            @Override
            public void onOutputBufferAvailable(MediaCodec codec, int index, MediaCodec.BufferInfo info) {

            }

            @Override
            public void onError(MediaCodec codec, MediaCodec.CodecException e) {

            }

            @Override
            public void onOutputFormatChanged(MediaCodec codec, MediaFormat format) {
                mOutputMediaFormat = format;
            }
        });
        mMediaCodec.start();
    }

    private void postSample() {
        mMediaExtractor.release();

        mMediaCodec.stop();
        mMediaCodec.release();
    }

    private void createMediaExtractor() throws IOException {
        mMediaExtractor = new MediaExtractor();
        mMediaExtractor.setDataSource(mFilePath);
        int trackCount = mMediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount - 1; i++) {
            mMediaFormat = mMediaExtractor.getTrackFormat(i);
            if (mMediaFormat.containsKey(MediaFormat.KEY_MIME)) {
                String mime = mMediaFormat.getString(MediaFormat.KEY_MIME);
                if (mime.startsWith("audio")) {
                    mMimeType = mime;
                    Log.d(TAG, "initMediaCodec: ");
                }
            }
        }
    }

    private void createMediaCodec() throws IOException {
        mMediaCodec = MediaCodec.createDecoderByType(mMimeType);
        mMediaCodec.configure(mMediaFormat, null, null, 0);
        mOutputMediaFormat = mMediaCodec.getOutputFormat();
    }
}
