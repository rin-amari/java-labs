package com.bsuir.lr.demo.models;


import jakarta.persistence.*;

@Entity
@Table(name = "chemistrytable")
public class DryMass {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "Solution mass")
    private final Double solutionMass;
    @Column(name = "Dry percentage")
    private final Double dryPercentage;
    @Column(name = "Dry mass")
    private final Double dryMass;

    public DryMass(Double dryMass, Double solutionMass, Double dryPercentage) {
        this.dryPercentage = dryPercentage;
        this.solutionMass = solutionMass;
        this.dryMass = dryMass;
    }
    public DryMass() {
        dryPercentage = (double) 0;
        solutionMass = (double) 0;
        dryMass = (double) 0;
    }

    public static DryMass calculate(Double sm, Double dp) {
        Double mass = sm * dp / 100;
        return new DryMass(mass, sm, dp);
    }

    public Double getDryMass() {
        return dryMass;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
