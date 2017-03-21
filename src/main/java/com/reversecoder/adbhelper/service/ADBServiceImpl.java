package com.reversecoder.adbhelper.service;

import com.reversecoder.adbhelper.data.ADBBatteryState;
import com.reversecoder.adbhelper.data.ADBDisplayOrientation;
import com.reversecoder.adbhelper.data.ADBDisplayPowerState;
import com.reversecoder.adbhelper.data.ADBProcess;
import com.reversecoder.adbhelper.data.ADBRemoteFile;
import com.reversecoder.adbhelper.data.ADBRunningServiceInfo;
import com.reversecoder.adbhelper.data.ADBScreenshotType;
import com.reversecoder.adbhelper.data.ADBBatteryState.BATTERY_STATUS;
import com.reversecoder.adbhelper.engine.*;
import com.reversecoder.adbhelper.parsers.ShellOutputParsers;

import java.io.File;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Md. Rashsadul Alam
 *
 */
public class ADBServiceImpl implements ADBService {

    private static ADBService mADBServiceImpl;

    private ADBServiceImpl() {
    }

    public static ADBService getInstance(){
        if(mADBServiceImpl == null){
            mADBServiceImpl = new ADBServiceImpl();
        }

        return mADBServiceImpl;
    }

    @Override
    public Map getPropertiesForDevice(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        Map map = adbDevice.getProperties();
        map.putAll(adbDevice.getDisplayInformation());
        adbConnection.close();
        return map;
    }

    @Override
    public void executeShellCommand(String udid, String command) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        adbDevice.executeShell(command);
        adbConnection.close();
    }

    @Override
    public List<String> getConnectedDevicesUdid() throws Exception {
        ADBConnection adbConnection = createConnection();
        List<ADBDevice> adbDevices = adbConnection.getDevices();
        List<String> udids = adbDevices.stream().map(ADBDevice::getUdid).collect(Collectors.toList());
        adbConnection.close();
        return udids;
    }

    public String getConnectedDeviceUdid() throws Exception {
        String mUdid="";
        ADBConnection adbConnection = createConnection();
        List<ADBDevice> adbDevices = adbConnection.getDevices();
        List<String> udids = adbDevices.stream().map(ADBDevice::getUdid).collect(Collectors.toList());
        if(udids.size()>0){
            mUdid = udids.get(0);
        }
        adbConnection.close();
        return mUdid;
    }

    @Override
    public byte[] getScreenshot(String udid, ADBScreenshotType screenshotType) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        byte[] screenshot = new byte[0];
        if (screenshotType == ADBScreenshotType.RAW) {
            screenshot = adbDevice.getRAWScreenshot();
        }
        if (screenshotType == ADBScreenshotType.PNG) {
            screenshot = adbDevice.getPNGScreenshot();
        }
        adbConnection.close();
        return screenshot;
    }

    @Override
    public List<ADBRemoteFile> getFileList(String udid, String path) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        List<ADBRemoteFile> files = adbDevice.getFileList(path);
        adbConnection.close();
        return files;
    }

    @Override
    public void pushFile(String udid, File localeFile, ADBRemoteFile remoteFile) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        adbDevice.push(localeFile, remoteFile);
        adbConnection.close();
    }

    @Override
    public void pullFile(String udid, File localeFile, ADBRemoteFile remoteFile) throws Exception {
        byte[] file = pullFile(udid, remoteFile);
        FileOutputStream fos = new FileOutputStream(localeFile);
        fos.write(file);
        fos.close();
    }

    @Override
    public byte[] pullFile(String udid, ADBRemoteFile remoteFile) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        byte[] file = adbDevice.pull(remoteFile);
        adbConnection.close();
        return file;
    }

    @Override
    public void rotate(String udid, ADBDisplayOrientation orientation) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        adbDevice.executeShell(ADBShellCommands.DISABLE_ACCELEROMETER_ROTATION);

        if (orientation == ADBDisplayOrientation.LANDSCAPE_ORIENTATION_LEFT) {
            adbDevice.executeShell(ADBShellCommands.LANDSCAPE_ORIENTATION_LEFT);
        }
        if (orientation == ADBDisplayOrientation.LANDSCAPE_ORIENTATION_RIGHT) {
            adbDevice.executeShell(ADBShellCommands.LANDSCAPE_ORIENTATION_RIGHT);
        }
        if (orientation == ADBDisplayOrientation.PORTRAIT_ORIENTATION_TOP) {
            adbDevice.executeShell(ADBShellCommands.PORTRAIT_ORIENTATION_TOP);
        }
        if (orientation == ADBDisplayOrientation.PORTRAIT_ORIENTATION_BOTTOM) {
            adbDevice.executeShell(ADBShellCommands.PORTRAIT_ORIENTATION_BOTTOM);
        }
        adbConnection.close();
    }

    @Override
    public void sendKeys(String udid, String text) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        text = text.replaceAll(" ", "%s");
        String shellCommand = ADBShellCommands.INPUT_TEXT + " \'" + text + "\'";
        adbDevice.executeShell(shellCommand);
        adbConnection.close();
    }

    @Override
    public List<ADBProcess> getProcessList(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        List<ADBProcess> adbProcesses = adbDevice.getProcessList();
        adbConnection.close();
        return adbProcesses;
    }

    @Override
    public ADBBatteryState getBatteryInfo(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String batteryStatusText = adbDevice.executeShell(ADBShellCommands.DUMPSYS_BATTERY);
        adbConnection.close();
        ADBBatteryState adbBatteryStatus = ShellOutputParsers.batteryStateParser(batteryStatusText);
        return adbBatteryStatus;
    }

    @Override
    public BATTERY_STATUS setFakeBatteryStatus(String udid, BATTERY_STATUS status) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String fakeShellCommand = ADBShellCommands.setBatteryStatus(status);
        adbDevice.executeShell(fakeShellCommand);
        String batteryStatusText = adbDevice.executeShell(ADBShellCommands.DUMPSYS_BATTERY);
        ADBBatteryState adbBatteryStatus = ShellOutputParsers.batteryStateParser(batteryStatusText);
        adbConnection.close();
        resetBatteryState(udid);
        return BATTERY_STATUS.getBatteryStatusName(adbBatteryStatus.getStatus());
    }

    @Override
    public int setFakeBatteryLevel(String udid, int level) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String fakeShellCommand = ADBShellCommands.setBatteryLevel(level);
        adbDevice.executeShell(fakeShellCommand);
        String batteryStatusText = adbDevice.executeShell(ADBShellCommands.DUMPSYS_BATTERY);
        ADBBatteryState adbBatteryStatus = ShellOutputParsers.batteryStateParser(batteryStatusText);
        adbConnection.close();
        resetBatteryState(udid);
        return adbBatteryStatus.getLevel();
    }

//    @Override
//    public boolean isDisplayOnButLocked(String udid) throws Exception {
//        ADBConnection adbConnection = createConnection();
//        ADBDevice adbDevice = adbConnection.getDevice(udid);
//        String displayPowerState = adbDevice.executeShell(ADBShellCommands.DISPLAY_POWER_STATE);
//        adbConnection.close();
//        ADBDisplayPowerState adbdisplayPowerState = ShellOutputParsers.displayPowerStateParser(displayPowerState);
//        if(!adbdisplayPowerState.getHoldingWakeLockSuspendBlocker() && adbdisplayPowerState.getHoldingDisplaySuspendBlocker()){
//            return true;
//        }
//        return false;
//    }

    @Override
    public boolean isDisplayOnAndUnlocked(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String displayPowerState = adbDevice.executeShell(ADBShellCommands.DISPLAY_POWER_STATE);
        adbConnection.close();
        ADBDisplayPowerState adbdisplayPowerState = ShellOutputParsers.displayPowerStateParser(displayPowerState);
        if(adbdisplayPowerState.getHoldingDisplaySuspendBlocker()){
            return true;
        }
        return false;
    }

    @Override
    public boolean isDisplayOffAndLocked(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String displayPowerState = adbDevice.executeShell(ADBShellCommands.DISPLAY_POWER_STATE);
        adbConnection.close();
        ADBDisplayPowerState adbdisplayPowerState = ShellOutputParsers.displayPowerStateParser(displayPowerState);
        if(!adbdisplayPowerState.getHoldingDisplaySuspendBlocker()){
            return true;
        }
        return false;
    }

    @Override
    public void doDisplayOffAndLock(String udid) throws Exception {
        if(isDisplayOnAndUnlocked(udid)){
            ADBConnection adbConnection = createConnection();
            ADBDevice adbDevice = adbConnection.getDevice(udid);
            adbDevice.executeShell(ADBShellCommands.TOGGLE_DISPLAY_POWER);
            adbConnection.close();
        }
    }

    @Override
    public void doDisplayOnButLock(String udid) throws Exception {
        if(isDisplayOffAndLocked(udid)){
            ADBConnection adbConnection = createConnection();
            ADBDevice adbDevice = adbConnection.getDevice(udid);
            adbDevice.executeShell(ADBShellCommands.TOGGLE_DISPLAY_POWER);
            adbConnection.close();
        }
    }

    @Override
    public void doDisplayOnAndUnlock(String udid) throws Exception {
        if(isDisplayOffAndLocked(udid)){
            ADBConnection adbConnection = createConnection();
            ADBDevice adbDevice = adbConnection.getDevice(udid);
            adbDevice.executeShell(ADBShellCommands.TOGGLE_DISPLAY_POWER);
            adbDevice.executeShell(ADBShellCommands.SWIPE_TO_UNLOCK);
            adbConnection.close();
        }
    }

    @Override
    public void swipeToUnlock(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        adbDevice.executeShell(ADBShellCommands.SWIPE_TO_UNLOCK);
        adbConnection.close();
    }

    @Override
    public void enableGPS(String udid, boolean doEnable) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        if(doEnable){
            adbDevice.executeShell(ADBShellCommands.ENABLE_GPS);
        } else {
            adbDevice.executeShell(ADBShellCommands.DISABLE_GPS);
        }
        adbConnection.close();
    }

    @Override
    public void clearAndForceStop(String udid, String packageName) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        adbDevice.executeShell(ADBShellCommands.getClearAndForceStop(packageName));
        adbConnection.close();
    }

    @Override
    public void forceStop(String udid, String packageName) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        adbDevice.executeShell(ADBShellCommands.getForceStop(packageName));
        adbConnection.close();
    }

    @Override
    public void launchApplication(String udid, String packageName) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        adbDevice.executeShell(ADBShellCommands.getLaunchApplication(packageName));
        adbConnection.close();
    }

    @Override
    public void launchActivity(String udid, String packageName, String activityNameWithFullPackage) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        adbDevice.executeShell(ADBShellCommands.getLaunchActivity(packageName, activityNameWithFullPackage));
        adbConnection.close();
    }

    @Override
    public String getOsVersion(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String osVersion = adbDevice.executeShell(ADBShellCommands.OS_VERSION);
        adbConnection.close();
        return osVersion;
    }

    @Override
    public String getApiLevel(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String apiLevel = adbDevice.executeShell(ADBShellCommands.API_LEVEL);
        adbConnection.close();
        return apiLevel;
    }

    @Override
    public boolean isWifiEnable(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String isWifi = adbDevice.executeShell(ADBShellCommands.IS_WIFI_ENABLE);
        adbConnection.close();
        if(isWifi.contains("1")){
            return true;
        }
        return false;
    }

    @Override
    public boolean isBlueToothEnable(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String isBluetooth = adbDevice.executeShell(ADBShellCommands.IS_BLUETOOTH_ENABLE);
        adbConnection.close();
        if(isBluetooth.contains("1")){
            return true;
        }
        return false;
    }

    @Override
    public boolean isAirplaneModeEnable(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String isAirplaneMode = adbDevice.executeShell(ADBShellCommands.IS_AIRPLANE_MODE_ENABLE);
        adbConnection.close();
        if(isAirplaneMode.contains("1")){
            return true;
        }
        return false;
    }

    @Override
    public boolean isNetworkProviderAllowed(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String isNetworkProvider = adbDevice.executeShell(ADBShellCommands.IS_NETWORK_PROVIDER_ALLOWED);
        adbConnection.close();
        if(isNetworkProvider.contains("network")){
            return true;
        }
        return false;
    }

    @Override
    public boolean isGpsProviderAllowed(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String isGpsProvider = adbDevice.executeShell(ADBShellCommands.IS_GPS_PROVIDER_ALLOWED);
        adbConnection.close();
        if(isGpsProvider.contains("gps")){
            return true;
        }
        return false;
    }

    @Override
    public boolean isSpecificServiceRunning(String udid, String packageName, String serviceNameWithFullPackage) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String serviceInfo = adbDevice.executeShell(ADBShellCommands.getSpecificService(packageName, serviceNameWithFullPackage));
        adbConnection.close();
        ADBRunningServiceInfo mRunningService = ShellOutputParsers.getRunningServiceInfo(serviceInfo);
        if(mRunningService.getApp() != null){
            return true;
        }
        return false;
    }

    @Override
    public String getLocaleLanguage(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String localeLanguage = adbDevice.executeShell(ADBShellCommands.LOCALE_LANGUAGE);
        String localeLanguageCode = ShellOutputParsers.getLocaleLanguageCode(localeLanguage);
        adbConnection.close();
        return localeLanguageCode;
    }

    @Override
    public String getLocaleCountry(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        String localeCountry = adbDevice.executeShell(ADBShellCommands.LOCALE_COUNTRY);
        String localeCountryCode = ShellOutputParsers.getLocaleCountryCode(localeCountry);
        adbConnection.close();
        return localeCountryCode;
    }

    public void resetBatteryState(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        adbDevice.executeShell(ADBShellCommands.RESET_BATTERY_FAKE_STATE);
        adbConnection.close();
    }

    public ADBConnection createConnection() throws Exception {
        ADBConnection adbConnection;
        try {
            adbConnection = new ADBConnection();
        } catch (ConnectException e) {
            throw new ConnectException("Unable to connect to ADB server. Please check that adb is running");
        }
        return adbConnection;
    }
}
