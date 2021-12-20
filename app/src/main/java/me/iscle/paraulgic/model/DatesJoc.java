package me.iscle.paraulgic.model;

import java.io.Serializable;

public class DatesJoc implements Serializable {
    private final String dataJoc;
    private final String dataJocAnterior;

    public DatesJoc(String dataJoc, String dataJocAnterior) {
        this.dataJoc = dataJoc;
        this.dataJocAnterior = dataJocAnterior;
    }

    public String getDataJoc() {
        return dataJoc;
    }

    public String getDataJocAnterior() {
        return dataJocAnterior;
    }
}
