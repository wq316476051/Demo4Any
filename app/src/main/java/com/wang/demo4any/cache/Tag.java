package com.wang.demo4any.cache;


import java.util.Map;

import androidx.annotation.Nullable;

public interface Tag {

    interface Editor {
        Editor save(long time, String text);
        Editor remove(long time);
        Editor clear();
        boolean commit();
        void apply();
    }

    Map<Long, String> getAll();

    @Nullable
    String getText(Long time, @Nullable String defValue);

    boolean contains(Long time);

    Editor edit();
}
