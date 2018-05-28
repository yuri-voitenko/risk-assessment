package ua.khpi.voitenko.riskassessment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "filled_risks")
public class FilledRisk {
    @Id
    @GeneratedValue
    private int id;

    @JoinColumn(name = "risk_id")
    @ManyToOne
    private Risk risk;

    private int probability;

    private int damage;

    public FilledRisk() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int calculateImpact() {
        return probability * damage;
    }

    @Override
    public String toString() {
        return "FilledRisk{" +
                "id=" + id +
                ", risk=" + risk +
                ", probability=" + probability +
                ", damage=" + damage +
                ", impact=" + calculateImpact() +
                '}';
    }
}
