package ua.khpi.voitenko.riskassessment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "risk_group_rates")
public class RiskGroupRate {
    @Id
    @GeneratedValue
    private int id;

    @JoinColumn(name = "risk_group_id")
    @ManyToOne
    private RiskGroup riskGroup;

    private int rate;

    @JoinColumn(name = "owner_id")
    @ManyToOne
    private User owner;

    public RiskGroupRate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RiskGroup getRiskGroup() {
        return riskGroup;
    }

    public void setRiskGroup(RiskGroup riskGroup) {
        this.riskGroup = riskGroup;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "RiskGroupRate{" +
                "id=" + id +
                ", riskGroup=" + riskGroup +
                ", rate=" + rate +
                ", owner=" + owner +
                '}';
    }
}
