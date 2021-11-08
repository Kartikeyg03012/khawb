package com.ngo.khawb.service.impl;

import com.ngo.khawb.model.Dreams;
import com.ngo.khawb.repository.DreamsRepository;
import com.ngo.khawb.service.DreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DreamServiceImpl implements DreamService {

  @Autowired private DreamsRepository dreamsRepository;

  @Override
  public Dreams addDreams(Dreams dreams) {
    return null;
  }

  @Override
  public List<Dreams> getAllDreams() {
    return null;
  }

  @Override
  public Dreams getDreamById(long id) {
    return null;
  }
}
