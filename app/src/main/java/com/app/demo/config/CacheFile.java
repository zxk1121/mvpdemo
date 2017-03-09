package com.app.demo.config;

import com.app.demo.application.App;

import java.io.File;

/**
 * Created by zxk on 17-3-9.
 */

public class CacheFile {
    public static File getHttpCache() {
        return new File(App.getContext().getCacheDir(), "HttpCache");
    }
}
