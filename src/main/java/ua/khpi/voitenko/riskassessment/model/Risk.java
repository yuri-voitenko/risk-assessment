package ua.khpi.voitenko.riskassessment.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "risks")
public class Risk {
    @Id
    @GeneratedValue
    private int id;

    @JoinColumn(name = "risk_group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RiskGroup name;

    private String description;

    @JoinColumn(name = "creator_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    public Risk() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RiskGroup getName() {
        return name;
    }

    public void setName(RiskGroup name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Risk{" +
                "id=" + id +
                ", name=" + name +
                ", description='" + description + '\'' +
                ", creator=" + creator +
                '}';
    }
}
