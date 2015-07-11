package com.example.anthony.damagecalculator.Data;

/**
 * Created by Thomas on 7/11/2015.
 */
public class OrbMatch {
    private int orbsLinked;
    private int numOrbPlus;
    private Color color;

    public OrbMatch(int orbsLinked, int numOrbPlus, Color color) {
        this.orbsLinked = orbsLinked;
        this.numOrbPlus = numOrbPlus;
        this.color = color;
    }

    public int getOrbsLinked() {
        return orbsLinked;
    }

    public void setOrbsLinked(int orbsLinked) {
        this.orbsLinked = orbsLinked;
    }

    public int getNumOrbPlus() {
        return numOrbPlus;
    }

    public void setNumOrbPlus(int numOrbPlus) {
        this.numOrbPlus = numOrbPlus;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
