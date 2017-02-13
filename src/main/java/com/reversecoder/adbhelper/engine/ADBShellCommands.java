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

    public static final String setBatteryStatus(BATTERY_STATUS batteryStatus){
        String bStatus = "dumpsys battery set status " + batteryStatus.getBatteryStatusValue();
        return bStatus;
    }

    public static final String setBatteryLevel(int batteryLevel){
        String bLevel = "dumpsys battery set level " + batteryLevel;
        return bLevel;
    }
}
