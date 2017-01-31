package com.reversecoder.adbhelper.data;

/**
 * @author Md. Rashsadul Alam
 *
 */
public class ADBBatteryState {

    private Boolean acPowered;
    private Boolean usbPowered;
    private Boolean wirelessPowered;
    private Integer status;
    private Integer health;
    private Boolean present;
    private Integer level;
    private Integer scale;
    private Integer voltage;
    private Integer currentNow;
    private Integer temperature;
    private String technology;

    public enum BATTERY_STATUS {
        UNKNOWN(1), CHARGING(2), DISCHARGING(3), NOT_CHARGING(4), FULL(5);

        private final int type;

        BATTERY_STATUS(int type) {
            this.type = type;
        }

        public int getBatteryStatusValue() {
            return type;
        }

        public static BATTERY_STATUS getBatteryStatusName(int statusValue) {
            for (BATTERY_STATUS e : BATTERY_STATUS.values()) {
                if (e.getBatteryStatusValue() == statusValue) {
                    return e;
                }
            }
            return null;// not found
        }
    }

    public enum BATTERY_HEALTH {
        UNKNOWN(1), GOOD(2), OVERHEAT(3), DEAD(4), OVERVOLTAGE(5), UNSPECIFIED_FAILURE(6), COLD(7);

        private final int type;

        BATTERY_HEALTH(int type) {
            this.type = type;
        }

        int getBatteryHealthValue() {
            return type;
        }

        public static BATTERY_HEALTH getBatteryHealthName(int healthValue) {
            for (BATTERY_HEALTH e : BATTERY_HEALTH.values()) {
                if (e.getBatteryHealthValue() == healthValue) {
                    return e;
                }
            }
            return null;// not found
        }
    }

    public Boolean getAcPowered() {
        return acPowered;
    }

    public void setAcPowered(Boolean acPowered) {
        this.acPowered = acPowered;
    }

    public Boolean getUsbPowered() {
        return usbPowered;
    }

    public void setUsbPowered(Boolean usbPowered) {
        this.usbPowered = usbPowered;
    }

    public Boolean getWirelessPowered() {
        return wirelessPowered;
    }

    public void setWirelessPowered(Boolean wirelessPowered) {
        this.wirelessPowered = wirelessPowered;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(BATTERY_STATUS status) {
        this.status = status.getBatteryStatusValue();
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(BATTERY_HEALTH health) {
        this.health = health.getBatteryHealthValue();
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Integer getVoltage() {
        return voltage;
    }

    public void setVoltage(Integer voltage) {
        this.voltage = voltage;
    }

    public Integer getCurrentNow() {
        return currentNow;
    }

    public void setCurrentNow(Integer currentNow) {
        this.currentNow = currentNow;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    @Override
    public String toString() {
        return "ADBBatteryStatus [acPowered=" + acPowered + ", usbPowered=" + usbPowered + ", wirelessPowered="
                + wirelessPowered + ", status=" + status + ", health=" + health + ", present=" + present + ", level="
                + level + ", scale=" + scale + ", voltage=" + voltage + ", currentNow=" + currentNow + ", temperature="
                + temperature + ", technology=" + technology + "]";
    }
}
