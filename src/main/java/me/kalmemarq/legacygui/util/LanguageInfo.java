package me.kalmemarq.legacygui.util;

public class LanguageInfo {
    public String code;
    public String name;
    public String region;

    public LanguageInfo(String code, String name, String region) {
        this.code = code;
        this.name = name;
        this.region = region;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return this.name + " (" + this.region + ")";
    }

    public String getRegion() {
        return region;
    }
}
