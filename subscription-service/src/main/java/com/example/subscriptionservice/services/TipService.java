package com.example.subscriptionservice.services;

import com.example.subscriptionservice.entities.Subscription;
import com.example.subscriptionservice.entities.Tip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TipService {

    List<Tip> findAll() throws Exception;

    Tip createTip(Tip tip);

    Tip getTipById(Long tipId);

    List<Tip> getAllTipsByChefId(Long chefId);
}
