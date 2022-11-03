package com.example.subscriptionservice.entities;

import com.example.subscriptionservice.model.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull
    private double amount;

/*    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_chef_id", nullable = false)
    @JsonIgnore
    private Profile chef;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_reader_id", nullable = false)
    @JsonIgnore
    private Profile reader;*/

    @NotNull
    private Date endDate;

    @NotNull
    private Date subscriptionDate;

    @Column(name = "chef_id")
    private Long chefId;

    @Transient
    private Profile chef;

    @Column(name = "reader_id")
    private Long readerId;

    @Transient
    private Profile reader;

    public Long getChefId() {
        return chefId;
    }

    public Subscription setChefId(Long chefId) {
        this.chefId = chefId;
        return this;
    }

    public Profile getChef() {
        return chef;
    }

    public Subscription setChef(Profile chef) {
        this.chef = chef;
        return this;
    }

    public Long getReaderId() {
        return readerId;
    }

    public Subscription setReaderId(Long readerId) {
        this.readerId = readerId;
        return this;
    }

    public Profile getReader() {
        return reader;
    }

    public Subscription setReader(Profile reader) {
        this.reader = reader;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

   /* public Profile getChef() {
        return chef;
    }

    public void setChef(Profile chef) {
        this.chef = chef;
    }

    public Profile getReader() {
        return reader;
    }

    public void setReader(Profile reader) {
        this.reader = reader;
    }*/

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
