package com.ngo.khawb.service.impl;

import com.ngo.khawb.model.Testimonials;
import com.ngo.khawb.repository.TestimonialRepository;
import com.ngo.khawb.service.TestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestimonialServiceImpl implements TestimonialsService {

  @Autowired private TestimonialRepository testimonialRepository;

  @Override
  public Testimonials addTestimonials(Testimonials testimonials) {
    return null;
  }

  @Override
  public List<Testimonials> getAllTestimonials() {
    return null;
  }
}
