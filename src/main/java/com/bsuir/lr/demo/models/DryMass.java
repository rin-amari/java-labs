package com.bsuir.lr.demo.models;

public class DryMass {
    private final Double dryMass;

    public DryMass(Double dryMass) {
        this.dryMass = dryMass;
    }

    public static DryMass calculate(Double sm, Double dp) {
        Double mass = sm * dp / 100;
        DryMass dryMass1 = new DryMass(mass);
        return dryMass1;
    }

    public Double getDryMass() {
        return dryMass;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
