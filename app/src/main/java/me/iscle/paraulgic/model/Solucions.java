package me.iscle.paraulgic.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Solucions implements Serializable {
    private final List<String> lletres;
    private final Map<String, String> paraules;
    private final int min;

    public Solucions(List<String> lletres, Map<String, String> paraules, int min) {
        this.lletres = lletres;
        this.paraules = paraules;
        this.min = min;
    }

    public List<String> getLletres() {
        return lletres;
    }

    public Map<String, String> getParaules() {
        return paraules;
    }

    public int getMin() {
        return min;
    }
}
