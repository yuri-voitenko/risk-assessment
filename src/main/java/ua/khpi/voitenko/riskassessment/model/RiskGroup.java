package ua.khpi.voitenko.riskassessment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "risk_groups")
public class RiskGroup {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private boolean mandatory;

    public RiskGroup() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Override
    public String toString() {
        return "RiskGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mandatory=" + mandatory +
                '}';
    }
}
