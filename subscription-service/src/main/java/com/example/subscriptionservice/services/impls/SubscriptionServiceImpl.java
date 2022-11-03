package com.example.subscriptionservice.services.impls;

import com.example.subscriptionservice.client.ProfileClient;
import com.example.subscriptionservice.entities.Subscription;
import com.example.subscriptionservice.exception.ResourceNotFoundException;
import com.example.subscriptionservice.model.Profile;
import com.example.subscriptionservice.repositories.SubscriptionRepository;
import com.example.subscriptionservice.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Qualifier("com.example.subscriptionservice.client.ProfileClient")
    @Autowired
    ProfileClient profileClient;


    @Override
    public List<Subscription> findAll() throws Exception
    {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription createSubscription(Subscription subscription) {
       return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getSubscriptionById(Long aLong){
        Subscription subscription = subscriptionRepository.findById(aLong).orElse(null);
        if (null!=subscription){
            Profile chef = profileClient.getProfile(subscription.getChefId()).getBody();
            subscription.setChef(chef);

            Profile reader = profileClient.getProfile(subscription.getReaderId()).getBody();
            subscription.setReader(reader);
        }
        return subscription;
    }

    @Override
    public List<Subscription> getAllSubscriptionsByChefId(Long chefId) {
        return subscriptionRepository.findSubscriptionsByChefId(chefId).orElse(null);
    }
}
