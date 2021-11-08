package com.ngo.khawb.repository;

import com.ngo.khawb.model.Testimonials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonials, Integer> {}
