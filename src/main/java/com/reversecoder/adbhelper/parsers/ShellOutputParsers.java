package com.reversecoder.adbhelper.parsers;

import java.util.Arrays;
import java.util.List;

import com.reversecoder.adbhelper.data.ADBBatteryState;
import com.reversecoder.adbhelper.data.ADBBatteryState.BATTERY_HEALTH;
import com.reversecoder.adbhelper.data.ADBBatteryState.BATTERY_STATUS;
import com.reversecoder.adbhelper.data.ADBDisplayPowerState;
import com.reversecoder.adbhelper.data.ADBRunningServiceInfo;

/**
 * @author Md. Rashsadul Alam
 *
 */
public class ShellOutputParsers {

    public static ADBBatteryState batteryStateParser(String input) {
        ADBBatteryState adbBatteryState = new ADBBatteryState();
        List<String> lines = Arrays.asList(input.split("\r\n"));
        for (int i = 1; i < lines.size(); i++) { // skip first line
            String line = lines.get(i);
            if (line.contains("AC powered: ")) {
                adbBatteryState.setAcPowered(Boolean.parseBoolean(line.split(": ")[1]));
            }
            if (line.contains("USB powered: ")) {
                adbBatteryState.setUsbPowered(Boolean.parseBoolean(line.split(": ")[1]));
            }
            if (line.contains("Wireless powered: ")) {
                adbBatteryState.setWirelessPowered(Boolean.parseBoolean(line.split(": ")[1]));
            }
            if (line.contains("status: ")) {
                adbBatteryState.setStatus(BATTERY_STATUS.getBatteryStatusName(Integer.parseInt(line.split(": ")[1])));
            }
            if (line.contains("health: ")) {
                adbBatteryState.setHealth(BATTERY_HEALTH.getBatteryHealthName(Integer.parseInt(line.split(": ")[1])));
            }
            if (line.contains("present: ")) {
                adbBatteryState.setPresent(Boolean.parseBoolean(line.split(": ")[1]));
            }
            if (line.contains("level: ")) {
                adbBatteryState.setLevel(Integer.parseInt(line.split(": ")[1]));
            }
            if (line.contains("scale: ")) {
                adbBatteryState.setScale(Integer.parseInt(line.split(": ")[1]));
            }
            if (line.contains("voltage: ")) {
                adbBatteryState.setVoltage(Integer.parseInt(line.split(": ")[1]));
            }
            if (line.contains("current now: ")) {
                adbBatteryState.setCurrentNow(Integer.parseInt(line.split(": ")[1]));
            }
            if (line.contains("temperature: ")) {
                adbBatteryState.setTemperature(Integer.parseInt(line.split(": ")[1]));
            }
            if (line.contains("technology: ")) {
                adbBatteryState.setTechnology((line.split(": ")[1]));
            }
        }

        return adbBatteryState;
    }

    public static ADBDisplayPowerState displayPowerStateParser(String input) {
        ADBDisplayPowerState adbDisplayPowerState = new ADBDisplayPowerState();
        List<String> lines = Arrays.asList(input.split("\r\n"));
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains("mHoldingWakeLockSuspendBlocker=")) {
                adbDisplayPowerState.setHoldingWakeLockSuspendBlocker(Boolean.parseBoolean(line.split("=")[1]));
            }

            if (line.contains("mHoldingDisplaySuspendBlocker=")) {
                adbDisplayPowerState.setHoldingDisplaySuspendBlocker(Boolean.parseBoolean(line.split("=")[1]));
            }
        }

        return adbDisplayPowerState;
    }

    public static ADBRunningServiceInfo getRunningServiceInfo(String input) {
        ADBRunningServiceInfo mRunningService = new ADBRunningServiceInfo();
        List<String> lines = Arrays.asList(input.split("\r\n"));
        for (int i = 2; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains("intent=")) {
                mRunningService.setIntent(line.split("ent=")[1]);
            }

            if (line.contains("packageName=")) {
                mRunningService.setPackageName(line.split("=")[1]);
            }

            if (line.contains("processName=")) {
                mRunningService.setProcessName(line.split("=")[1]);
            }

            if (line.contains("baseDir=")) {
                mRunningService.setBaseDir(line.split("=")[1]);
            }

            if (line.contains("dataDir=")) {
                mRunningService.setDataDir(line.split("=")[1]);
            }

            if (line.contains("app=")) {
                mRunningService.setApp(line.split("=")[1]);
            }

        }

        return mRunningService;
    }
}
