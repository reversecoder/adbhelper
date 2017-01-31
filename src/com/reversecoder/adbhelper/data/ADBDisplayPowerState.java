package com.reversecoder.adbhelper.data;

/**
 * @author Md. Rashsadul Alam
 *
 */
public class ADBDisplayPowerState {

    private Boolean mHoldingWakeLockSuspendBlocker;
    private Boolean mHoldingDisplaySuspendBlocker;

    public Boolean getHoldingWakeLockSuspendBlocker() {
        return mHoldingWakeLockSuspendBlocker;
    }

    public void setHoldingWakeLockSuspendBlocker(Boolean mHoldingWakeLockSuspendBlocker) {
        this.mHoldingWakeLockSuspendBlocker = mHoldingWakeLockSuspendBlocker;
    }

    public Boolean getHoldingDisplaySuspendBlocker() {
        return mHoldingDisplaySuspendBlocker;
    }

    public void setHoldingDisplaySuspendBlocker(Boolean mHoldingDisplaySuspendBlocker) {
        this.mHoldingDisplaySuspendBlocker = mHoldingDisplaySuspendBlocker;
    }
}
