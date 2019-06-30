package com.wang.demo4any.cache;

import android.system.ErrnoException;
import android.system.Os;
import android.system.StructStat;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;

/**
 * 做一个类似 SharedPreferences 的缓存类
 */
public class TagImpl implements Tag {

    private static final String TAG = "TagImpl";

    private final File mFile;
    private final File mBackupFile;
    private final Object mLock = new Object();

    @GuardedBy("mLock")
    private Map<String, Object> mMap;

    @GuardedBy("mLock")
    private boolean mLoaded;

    public TagImpl(File file) {
        mFile = file;
        mBackupFile = makeBackupFile(file);
        mLoaded = false;
        mMap = null;

        startLoadFromDisk();
    }

    public static TagImpl create(File file) {
        return new TagImpl(file);
    }

    @Override
    public Map<Long, String> getAll() {
        return null;
    }

    @Nullable
    @Override
    public String getText(Long time, @Nullable String defValue) {
        return null;
    }

    @Override
    public boolean contains(Long time) {
        return false;
    }

    @Override
    public Editor edit() {
        synchronized (mLock) {
//            awaitLoadedLocked();
        }

        return new EditorImpl();
    }

    /****************************************/

    private static File makeBackupFile(File tagFile) {
        return new File(tagFile.getPath() + ".bak");
    }

    private void startLoadFromDisk() {
        synchronized (mLock) {
            mLoaded = false;
        }
        CompletableFuture.runAsync(() -> {
            loadFromDisk();
        });
    }

    private void loadFromDisk() {
        Log.d(TAG, "loadFromDisk: " + Thread.currentThread().getName());
        synchronized (mLock) {
            if (mLoaded) {
                return;
            }
            if (mBackupFile.exists()) {
                mFile.delete();
                mBackupFile.renameTo(mFile);
            }
        }

        // Debugging
        if (mFile.exists() && !mFile.canRead()) {
            Log.w(TAG, "Attempt to read preferences file " + mFile + " without permission");
        }

        Map map = null;
        StructStat stat = null;
        try {
            stat = Os.stat(mFile.getPath());
            if (mFile.canRead()) {
                BufferedInputStream str = null;
                try {
                    str = new BufferedInputStream(
                            new FileInputStream(mFile), 16*1024);
//                    map = XmlUtils.readMapXml(str);
                } catch (Exception e) {
                    Log.w(TAG, "Cannot read " + mFile.getAbsolutePath(), e);
                } finally {
//                    IoUtils.closeQuietly(str);
                }
            }
        } catch (ErrnoException e) {
            /* ignore */
        }

        synchronized (mLock) {
            mLoaded = true;
            if (map != null) {
                mMap = map;
            } else {
                mMap = new HashMap<>();
            }
            mLock.notifyAll();
        }
    }

    /****************************************/

    public final class EditorImpl implements Editor {

        private final Object mLock = new Object();

        @GuardedBy("mLock")
        private final Map<Long, String> mModified = new HashMap<>();

        @GuardedBy("mLock")
        private boolean mClear = false;

        @Override
        public Editor save(long time, String text) {
            synchronized (mLock) {
                mModified.put(time, text);
                return this;
            }
        }

        @Override
        public Editor remove(long time) {
            synchronized (mLock) {
                return this;
            }
        }

        @Override
        public Editor clear() {
            synchronized (mLock) {
                mClear = true;
                return this;
            }
        }

        @Override
        public boolean commit() {
            return false;
        }


        @Override
        public void apply() {

        }
    }
}
