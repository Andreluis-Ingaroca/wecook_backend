package com.example.subscriptionservice.services.impls;

import com.example.subscriptionservice.client.ProfileClient;
import com.example.subscriptionservice.entities.Subscription;
import com.example.subscriptionservice.entities.Tip;
import com.example.subscriptionservice.exception.ResourceNotFoundException;
import com.example.subscriptionservice.model.Profile;
import com.example.subscriptionservice.repositories.TipRepository;
import com.example.subscriptionservice.services.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipServiceImpl implements TipService {

    @Autowired
    TipRepository tipRepository;

    @Qualifier("com.example.subscriptionservice.client.ProfileClient")
    @Autowired
    ProfileClient profileClient;


    @Override
    public List<Tip> findAll() throws Exception
    {
        return tipRepository.findAll();
    }

    @Override
    public Tip createTip(Tip tip) {
        return tipRepository.save(tip);
    }

    @Override
    public Tip getTipById(Long aLong){
        Tip tip = tipRepository.findById(aLong).orElse(null);
        if (null!=tip){
            Profile chef = profileClient.getProfile(tip.getChefId()).getBody();
            tip.setChef(chef);

            Profile reader = profileClient.getProfile(tip.getReaderId()).getBody();
            tip.setReader(reader);
        }
        return tip;
    }

    @Override
    public List<Tip> getAllTipsByChefId(Long chefId) {
        return tipRepository.findTipsByChefId(chefId).orElse(null);
    }
}
