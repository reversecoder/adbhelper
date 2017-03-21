package com.reversecoder.adbhelper.engine;

import com.reversecoder.adbhelper.data.ADBBatteryState.BATTERY_STATUS;

/**
 * @author Md. Rashsadul Alam
 *
 */
public class ADBShellCommands {

    public static final String PROPERTY_RAW_SCREENSHOT_PATH = "/sdcard/screenshot.raw";
    public static final String PROPERTY_PNG_SCREENSHOT_PATH = "/sdcard/screenshot.png";

    public static final String GET_PROPERTIES = "getprop";
    public static final String GET_DISPLAY_INFORMATION = "dumpsys display";
    public static final String GET_RAW_SCREENSHOT = "screencap " + PROPERTY_RAW_SCREENSHOT_PATH;
    public static final String DELETE_RAW_SCREENSHOT = "rm -r " + PROPERTY_RAW_SCREENSHOT_PATH;
    public static final String GET_PNG_SCREENSHOT = "screencap -p " + PROPERTY_PNG_SCREENSHOT_PATH;
    public static final String DELETE_PNG_SCREENSHOT = "rm -r " + PROPERTY_PNG_SCREENSHOT_PATH;
    public static final String DUMPSYS_BATTERY = "dumpsys battery";
    public static final String DISABLE_ACCELEROMETER_ROTATION = "content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:0";
    public static final String PORTRAIT_ORIENTATION_TOP = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:0";
    public static final String LANDSCAPE_ORIENTATION_RIGHT = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:1";
    public static final String PORTRAIT_ORIENTATION_BOTTOM = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:2";
    public static final String LANDSCAPE_ORIENTATION_LEFT = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:3";
    public static final String INPUT_TEXT = "input text";
    public static final String PROCESSES = "ps";
    public static final String RESET_BATTERY_FAKE_STATE="dumpsys battery reset";
    public static final String DISPLAY_POWER_STATE="dumpsys power | grep 'mHolding'";
    public static final String TOGGLE_DISPLAY_POWER="input keyevent 26";
    public static final String SWIPE_TO_UNLOCK="input swipe 200 700 200 0";
    public static final String ON_DISLPLAY_POWER_AND_SWIPE_TO_UNLOCK="\"input keyevent 26; input swipe 200 700 200 0;\"";
    public static final String ENABLE_GPS = "settings put secure location_providers_allowed +gps";
    public static final String DISABLE_GPS = "settings put secure location_providers_allowed -gps";
    public static final String OS_VERSION = "getprop ro.build.version.release";
    public static final String API_LEVEL = "getprop ro.build.version.sdk";
    public static final String IS_WIFI_ENABLE = "settings get global wifi_on";
    public static final String IS_BLUETOOTH_ENABLE = "settings get global bluetooth_on";
    public static final String IS_AIRPLANE_MODE_ENABLE = "settings get global airplane_mode_on";
    public static final String IS_NETWORK_PROVIDER_ALLOWED = "settings get secure location_providers_allowed";
    public static final String IS_GPS_PROVIDER_ALLOWED = "settings get secure location_providers_allowed";
    public static final String ENABLE_WIFI = "am start -n com.reversecoder.appium.settings/com.reversecoder.appium.settings.network.NetworkActivity -e wifi on";
    public static final String DISABLE_WIFI = "am start -n com.reversecoder.appium.settings/com.reversecoder.appium.settings.network.NetworkActivity -e wifi off";
    public static final String ENABLE_DATA = "am start -n com.reversecoder.appium.settings/com.reversecoder.appium.settings.network.NetworkActivity -e data on";
    public static final String DISABLE_DATA = "am start -n com.reversecoder.appium.settings/com.reversecoder.appium.settings.network.NetworkActivity -e data off";
    public static final String UNLOCK_DEVICE = "am start -n com.reversecoder.appium.settings/com.reversecoder.appium.settings.unlock.Unlock";
    public static final String GRANT_LOCAL_PERMISSION = "pm grant com.reversecoder.appium.settings android.permission.CHANGE_CONFIGURATION";
    public static final String LOCALE_LANGUAGE = "getprop |grep lang";
    public static final String LOCALE_COUNTRY = "getprop |grep country";

    public static final String setBatteryStatus(BATTERY_STATUS batteryStatus){
        String bStatus = "dumpsys battery set status " + batteryStatus.getBatteryStatusValue();
        return bStatus;
    }

    public static final String setBatteryLevel(int batteryLevel){
        String bLevel = "dumpsys battery set level " + batteryLevel;
        return bLevel;
    }

    public static final String getClearAndForceStop(String packageName) {
        String clearAndForceStop = "pm clear " + packageName;
        return clearAndForceStop;
    }

    public static final String getForceStop(String packageName) {
        String clearAndForceStop = "am force-stop " + packageName;
        return clearAndForceStop;
    }

    public static final String getLaunchApplication(String packageName) {
        String launchApplication = "monkey -p " + packageName
                + " -c android.intent.category.LAUNCHER 1";
        return launchApplication;
    }

    public static final String getLaunchActivity(String packageName,
            String activityNameWithFullPackage) {
        String launchActivity = "am start -n " + packageName + "/" + activityNameWithFullPackage;
        return launchActivity;
    }

    public static final String getSpecificService(String packageName,
            String serviceNameWithFullPackage) {
        String getSpecificService = "dumpsys activity services " + packageName + "/" + serviceNameWithFullPackage;
        return getSpecificService;
    }

    public static final String getLocaleChange(String language, String country) {
        String changeLocale = "am broadcast -a 'AppiumSettings' --es LANG " + language + " --es COUNTRY " + country;
        return changeLocale;
    }
}
