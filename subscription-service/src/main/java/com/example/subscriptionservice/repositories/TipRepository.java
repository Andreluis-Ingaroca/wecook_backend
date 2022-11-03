package com.example.subscriptionservice.repositories;

import com.example.subscriptionservice.entities.Subscription;
import com.example.subscriptionservice.entities.Tip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipRepository extends JpaRepository<Tip, Long> {

    public Optional<List<Tip>> findTipsByChefId(long chefId);
}
