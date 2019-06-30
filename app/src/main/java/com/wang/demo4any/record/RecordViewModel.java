package com.wang.demo4any.record;

import android.app.Application;
import android.os.Looper;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RecordViewModel extends AndroidViewModel {

    private MutableLiveData<List<File>> mRecords = new MutableLiveData<>();

    public RecordViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<File>> getRecords() {
        return mRecords;
    }

    public void loadRecords(boolean isAsync) {
        if (isAsync) {
            CompletableFuture.runAsync(this::loadRecords);
        } else {
            loadRecords();
        }
    }

    private void loadRecords() {
        List<File> files = Arrays.asList(RecordStorage.getStorageDirectory().listFiles());
        if (isMainThread()) {
            mRecords.setValue(files);
        } else {
            mRecords.postValue(files);
        }
    }

    private boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
