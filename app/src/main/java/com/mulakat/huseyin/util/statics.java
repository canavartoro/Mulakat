package com.mulakat.huseyin.util;

import android.app.Activity;

import com.mulakat.huseyin.data.ArchiveItemComponent;
import com.mulakat.huseyin.mulakat.MainActivity;

/**
 * Created by huseyin on 1.11.2017.
 */

public class statics {

    private static String weburl = "https://www.wired.com/most-popular/";
    private static MainActivity mainActivity;
    private static Activity currentActivity;
    private static ArchiveItemComponent component;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static void setMainActivity(MainActivity a) {
        statics.mainActivity = a;
    }

    public static void setCurrentActivity(Activity ac) {
        currentActivity = ac;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentComponent(ArchiveItemComponent c) {
        component = c;
    }

    public static ArchiveItemComponent getCurrentComponent() {
        return component;
    }

    public static String getWeburl() { return weburl; }

}
