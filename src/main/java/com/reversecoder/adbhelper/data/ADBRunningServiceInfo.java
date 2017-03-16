package com.reversecoder.adbhelper.data;

/**
 * @author Md. Rashsadul Alam
 *
 */
public class ADBRunningServiceInfo {

    private String intent;
    private String packageName;
    private String processName;
    private String baseDir;
    private String dataDir;
    private String app;

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    @Override
    public String toString() {
        return "ADBRunningServiceInfo [intent=" + intent + ", packageName=" + packageName + ", processName="
                + processName + ", baseDir=" + baseDir + ", dataDir=" + dataDir + ", app=" + app + "]";
    }
}
