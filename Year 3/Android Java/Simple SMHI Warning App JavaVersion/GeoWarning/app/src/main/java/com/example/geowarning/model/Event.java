package com.example.geowarning.model;

public class Event {
    private String en;
    private String sv;
    private String code;
    private MhoClassification mhoClassification;

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getSv() {
        return sv;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MhoClassification getMhoClassification() {
        return mhoClassification;
    }

    public void setMhoClassification(MhoClassification mhoClassification) {
        this.mhoClassification = mhoClassification;
    }
}
