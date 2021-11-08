package com.ngo.khawb.repository;

import com.ngo.khawb.model.Dreams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DreamsRepository extends JpaRepository<Dreams, Integer> {}
