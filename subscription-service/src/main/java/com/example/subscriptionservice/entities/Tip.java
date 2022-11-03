package com.example.subscriptionservice.entities;

import com.example.subscriptionservice.model.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tips")
@Data
public class Tip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull
    private double amount;

   /* @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_receiver_id", nullable = false)
    @JsonIgnore
    private Profile receiver;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_sender_id", nullable = false)
    @JsonIgnore
    private Profile sender;*/

    @Column(name = "chef_id")
    private Long chefId;

    @Transient
    private Profile chef;

    @Column(name = "reader_id")
    private Long readerId;

    @Transient
    private Profile reader;

    public Tip setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Long getChefId() {
        return chefId;
    }

    public Tip setChefId(Long chefId) {
        this.chefId = chefId;
        return this;
    }

    public Profile getChef() {
        return chef;
    }

    public Tip setChef(Profile chef) {
        this.chef = chef;
        return this;
    }

    public Long getReaderId() {
        return readerId;
    }

    public Tip setReaderId(Long readerId) {
        this.readerId = readerId;
        return this;
    }

    public Profile getReader() {
        return reader;
    }

    public Tip setReader(Profile reader) {
        this.reader = reader;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

   /* public Profile getReceiver() {
        return receiver;
    }

    public void setReceiver(Profile receiver) {
        this.receiver = receiver;
    }

    public Profile getSender() {
        return sender;
    }

    public void setSender(Profile sender) {
        this.sender = sender;
    }*/
}
