package com.ngo.khawb.service;

import com.ngo.khawb.model.Testimonials;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TestimonialsService {

  Testimonials addTestimonials(Testimonials testimonials);

  List<Testimonials> getAllTestimonials();
}
