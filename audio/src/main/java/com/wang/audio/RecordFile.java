package com.wang.audio;

import android.support.annotation.Nullable;
import android.util.Log;

import com.wang.audio.utils.ByteUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RecordFile {

    private static final String TAG = "RecordFile";

    public File mFile;
    private Extra mExtra;
    private long mRecordLength;

    public RecordFile(File file) {
        mFile = file;
        mExtra = Extra.parse(mFile);
        if (mExtra == null) {
            mRecordLength = mFile.length();
        } else {
            mRecordLength = mFile.length() - mExtra.extraSize;
        }
    }

    public Extra getExtra() {
        return mExtra;
    }

    public void writeAtTail(File file) {
        Log.d(TAG, "writeAtTail: file = " + file.getAbsolutePath());
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
            if (mExtra == null) {
                Log.w(TAG, "writeAtTail: no data to write.");
                return;
            }
            randomAccessFile.seek(mRecordLength);

            int totalSize = 0;

            // createTime
            if (mExtra.createTimeBox != null) {
                randomAccessFile.write(Extra.CreateTimeBox.TYPE.getBytes());
                randomAccessFile.writeInt(Extra.CreateTimeBox.size);
                randomAccessFile.writeLong(System.currentTimeMillis());

                totalSize += Extra.CreateTimeBox.size;
            }

            // tag
            for (Extra.TagBox tag : mExtra.tagBoxes) {
                randomAccessFile.write(Extra.TagBox.TYPE.getBytes()); // 4
                randomAccessFile.writeInt(tag.size); // 4
                randomAccessFile.writeLong(tag.time); // 8
                randomAccessFile.write(tag.content.getBytes());

                totalSize += tag.size;
            }

            // empty ?
            long last = file.length() - randomAccessFile.getFilePointer() - Extra.TAIL_SIZE;
            if (last > 0) {
                randomAccessFile.write(Extra.EmptyBox.TYPE.getBytes(), 0, Math.min((int) last, 4));
                totalSize += last;
            }

            // header
            randomAccessFile.write(Extra.TYPE.getBytes());
            randomAccessFile.writeInt((totalSize += Extra.TAIL_SIZE));
            randomAccessFile.writeInt(mExtra.extraVersion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createFilename(String extension) {
        SimpleDateFormat format = new SimpleDateFormat("YYYmmdd", Locale.ENGLISH);
        String title = format.format(new Date());
        return title + "." + extension;
    }

    static class Extra {
        public static final int BYTES_OF_TYPE = 4;
        public static final int BYTES_OF_SIZE = 4;
        public static final int BYTES_OF_VERSION = 4;
        public static final int TAIL_SIZE = BYTES_OF_TYPE + BYTES_OF_SIZE + BYTES_OF_VERSION;

        public static final String TYPE = "extr";
        /**
         * 1: initial extraVersion
         */
        public int extraVersion = 1;
        public long extraOffset;
        public int extraSize;
        public CreateTimeBox createTimeBox;
        public ArrayList<TagBox> tagBoxes = new ArrayList<>();
        public EmptyBox emptyBox;

        public Extra(long offset) {
            this.extraOffset = offset;
        }
        @Override
        public String toString() {
            return "Extra{" +
                    "extraVersion=" + extraVersion +
                    ", extraOffset=" + extraOffset +
                    ", extraSize=" + extraSize +
                    ", createTimeBox=" + createTimeBox +
                    ", tagBoxes=" + tagBoxes +
                    ", emptyBox=" + emptyBox +
                    '}';
        }

        @Nullable
        public static Extra parse(File file) {
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {

                // tail
                Extra extra = Extra.parseTail(randomAccessFile, file.length());
                if (extra !=  null) {
                    randomAccessFile.seek(extra.extraOffset);

                    while (true) {
                        byte[] buffer = new byte[8];
                        randomAccessFile.read(buffer);
                        String boxType = new String(buffer, 0, Extra.BYTES_OF_TYPE);
                        int boxSize = ByteUtils.bytesToInt(buffer, Extra.BYTES_OF_TYPE);
                        Log.d(TAG, "parse: boxType = " + boxType);
                        Log.d(TAG, "parse: boxSize = " + boxSize);

                        byte[] content = new byte[boxSize - BYTES_OF_TYPE - BYTES_OF_SIZE];
                        randomAccessFile.read(content);
                        if (CreateTimeBox.TYPE.equals(boxType)) {
                            // createTime
                            extra.createTimeBox = Extra.parseCreateTime(boxSize, content);
                        } else if (TagBox.TYPE.equals(boxType)) {
                            // tag
                            extra.tagBoxes.add(Extra.parseTag(boxSize, content));
                        } else if (EmptyBox.TYPE.equals(boxType)) {
                            // empty
                            Log.d(TAG, "parse: break normally");
                            break;
                        } else {
                            Log.d(TAG, "parse: break abnormally");
                            break;
                        }
                        long lastBytes = file.length() - randomAccessFile.getFilePointer() - TAIL_SIZE;
                        Log.d(TAG, "parse: lastBytes = " + lastBytes);
                        if (lastBytes == 0) {
                            Log.d(TAG, "parse: reach the end.");
                            break;
                        } else if (lastBytes < Extra.BYTES_OF_TYPE + Extra.BYTES_OF_SIZE) {
                            byte[] end = new byte[(int) lastBytes];
                            randomAccessFile.read(end);
                            String str = new String(end, 0, Math.min(end.length, 4));
                            if (EmptyBox.TYPE.startsWith(str)) {
                                Log.d(TAG, "parse: reach the end normally.");
                            } else {
                                Log.d(TAG, "parse: reach the end abnormally.");
                            }
                            break;
                        }
                    }

                    Log.d(TAG, "parse: extra = " + extra);
                    return extra;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Nullable
        public static Extra parseTail(RandomAccessFile randomAccessFile, long fileLength) {
            try {
                byte[] header;
                randomAccessFile.seek(fileLength - Extra.TAIL_SIZE);
                randomAccessFile.read(header = new byte[Extra.TAIL_SIZE]);
                String type = new String(header, 0, BYTES_OF_TYPE);
                int size = ByteUtils.bytesToInt(header, BYTES_OF_TYPE);
                int version = ByteUtils.bytesToInt(header, BYTES_OF_TYPE + BYTES_OF_SIZE);
                Log.d(TAG, "parse: type = " + type);
                Log.d(TAG, "parse: extraSize = " + size);
                Log.d(TAG, "parse: extraVersion = " + version);
                if (Extra.TYPE.equals(type) && size > 0 && version > 0) {
                    Extra extra = new Extra(fileLength - size);
                    extra.extraSize = size;
                    extra.extraVersion = version;
                    Log.d(TAG, "parse: extra = " + extra);
                    return extra;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private static CreateTimeBox parseCreateTime(int boxSize, byte[] content) {
            CreateTimeBox createTimeBox = new CreateTimeBox();
            createTimeBox.size = boxSize;
            createTimeBox.createTime = ByteUtils.bytesToLong(content);
            Log.d(TAG, "parseCreateTime: createTimeBox = " + createTimeBox);
            return createTimeBox;
        }

        private static TagBox parseTag(int size, byte[] content) {
            Log.d(TAG, "parseTag: length = " + content.length);
            TagBox tagBox = new TagBox();
            tagBox.size = size;
            tagBox.time = ByteUtils.bytesToLong(content);
            tagBox.content = new String(content, TagBox.BYTES_OF_TIME, content.length - TagBox.BYTES_OF_TIME);
            Log.d(TAG, "parse: tagBox = " + tagBox);
            return tagBox;
        }

        public static class CreateTimeBox {
            public static final int BYTES_OF_CONTENT = 8;
            public static final String TYPE = "time";
            public static int size = BYTES_OF_TYPE + BYTES_OF_SIZE + BYTES_OF_CONTENT;
            public long createTime;

            public static CreateTimeBox parse(long offset, int sizeOfBox, byte[] contentBytes) {
                CreateTimeBox box = new CreateTimeBox();
                box.createTime = ByteUtils.bytesToLong(contentBytes);
                Log.d(TAG, "CreateTimeBox parse: createTime = " + box.createTime);
                return box;
            }

            @Override
            public String toString() {
                return "CreateTimeBox{" +
                        "createTime=" + createTime +
                        '}';
            }
        }

        public static class TagBox {
            public static final String TYPE = "tags";
            public static final int BYTES_OF_TIME = 8;
            int size;
            long time;
            private String content;

            public void setContent(String content) {
                this.content = content;
                this.size = BYTES_OF_TYPE + BYTES_OF_SIZE + content.length();
            }

            @Override
            public String toString() {
                return "TagBox{" +
                        "extraSize=" + size +
                        ", time=" + time +
                        ", content='" + content + '\'' +
                        '}';
            }
        }

        public static class EmptyBox {
            public static final String TYPE = "empt";
            public long size;

            public static EmptyBox parse(long offset, int sizeOfBox) {
                EmptyBox box = new EmptyBox();
                box.size = sizeOfBox;
                return box;
            }
        }
    }
}
