package com.example.subscriptionservice.services;

import com.example.subscriptionservice.entities.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubscriptionService {

    List<Subscription> findAll() throws Exception;

    Subscription createSubscription(Subscription subscription);

    Subscription getSubscriptionById(Long subscriptionId);

    List<Subscription> getAllSubscriptionsByChefId(Long chefId);
}
