package com.ngo.khawb.service;

import com.ngo.khawb.model.Dreams;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DreamService {

  Dreams addDreams(Dreams dreams);

  List<Dreams> getAllDreams();

  Dreams getDreamById(long id);
}
