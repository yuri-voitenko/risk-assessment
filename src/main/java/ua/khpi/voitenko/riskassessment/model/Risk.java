package ua.khpi.voitenko.riskassessment.model;

import javax.persistence.Entity;
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
    @ManyToOne
    private RiskGroup group;

    private String description;

    @JoinColumn(name = "creator_id")
    @ManyToOne
    private User creator;

    public Risk() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RiskGroup getGroup() {
        return group;
    }

    public void setGroup(RiskGroup group) {
        this.group = group;
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
                ", group=" + group +
                ", description='" + description + '\'' +
                ", creator=" + creator +
                '}';
    }
}
