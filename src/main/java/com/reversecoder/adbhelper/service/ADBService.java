package com.reversecoder.adbhelper.service;

import com.reversecoder.adbhelper.data.ADBBatteryState;
import com.reversecoder.adbhelper.data.ADBDisplayOrientation;
import com.reversecoder.adbhelper.data.ADBProcess;
import com.reversecoder.adbhelper.data.ADBRemoteFile;
import com.reversecoder.adbhelper.data.ADBScreenshotType;
import com.reversecoder.adbhelper.data.ADBBatteryState.BATTERY_STATUS;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author Md. Rashsadul Alam
 *
 */

public interface ADBService {

    void executeShellCommand(String udid, String command) throws Exception;

    List<String> getConnectedDevicesUdid() throws Exception;

    Map getPropertiesForDevice(String udid) throws Exception;

    byte[] getScreenshot(String udid, ADBScreenshotType screenshotType) throws Exception;

    List<ADBRemoteFile> getFileList(String udid, String path) throws Exception;

    void pushFile(String udid, File localeFile, ADBRemoteFile remoteFile) throws Exception;

    void pullFile(String udid, File localeFile, ADBRemoteFile remoteFile) throws Exception;

    byte[] pullFile(String udid, ADBRemoteFile remoteFile) throws Exception;

    void rotate(String udid, ADBDisplayOrientation orientation) throws Exception;

    void sendKeys(String udid, String text) throws Exception;

    List<ADBProcess> getProcessList(String udid) throws Exception;

    ADBBatteryState getBatteryInfo(String udid) throws Exception;

    BATTERY_STATUS setFakeBatteryStatus(String udid, BATTERY_STATUS status) throws Exception;

    int setFakeBatteryLevel(String udid, int level) throws Exception;

//    boolean isDisplayOnButLocked(String udid) throws Exception;

    boolean isDisplayOnAndUnlocked(String udid) throws Exception;

    boolean isDisplayOffAndLocked(String udid) throws Exception;

    void doDisplayOffAndLock(String udid) throws Exception;

    void doDisplayOnButLock(String udid) throws Exception;

    void doDisplayOnAndUnlock(String udid) throws Exception;

    void swipeToUnlock(String udid) throws Exception;

    void enableGPS(String udid, boolean doEnable) throws Exception;

    void clearAndForceStop(String udid, String packageName) throws Exception;

    void forceStop(String udid, String packageName) throws Exception;

    void launchApplication(String udid, String packageName) throws Exception;

    void launchActivity(String udid, String packageName, String activityNameWithFullPackage) throws Exception;

    String getOsVersion(String udid) throws Exception;

    String getApiLevel(String udid) throws Exception;

    boolean isWifiEnable(String udid) throws Exception;

    boolean isBlueToothEnable(String udid) throws Exception;

    boolean isAirplaneModeEnable(String udid) throws Exception;

    boolean isNetworkProviderAllowed(String udid) throws Exception;

    boolean isGpsProviderAllowed(String udid) throws Exception;

    boolean isSpecificServiceRunning(String udid, String packageName, String serviceNameWithFullPackage) throws Exception;
}
