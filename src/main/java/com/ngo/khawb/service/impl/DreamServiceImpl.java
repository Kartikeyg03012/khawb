package com.ngo.khawb.service.impl;

import com.ngo.khawb.model.Dreams;
import com.ngo.khawb.repository.DreamsRepository;
import com.ngo.khawb.service.DreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DreamServiceImpl implements DreamService {

  @Autowired private DreamsRepository dreamsRepository;

  @Override
  public Dreams addDreams(Dreams dreams) {
    return dreamsRepository.save(dreams);
  }

  @Override
  public List<Dreams> getAllDreams(long userId) {
    return dreamsRepository.getAllDreamsByUserId(userId);
  }

  @Override
  public Dreams getDreamById(long id) {
    return dreamsRepository.getById(id);
  }

  @Override
  public Dreams updateDream(Dreams dream) {
    return dreamsRepository.save(dream);
  }

  @Override
  public void deleteDream(Dreams dreams) {
    dreamsRepository.delete(dreams);
  }

  @Override
  public List<Dreams> getWishListProducts(Iterable<Long> ids) {
    List<Dreams> findAllById = dreamsRepository.findAllById(ids);
    return findAllById;
  }

  @Override
  public Page<Dreams> getAll(Pageable pageable) {
    return dreamsRepository.findAll(pageable);
  }

  @Override
  public Page<Dreams> findAllDreamsRandomly(Boolean verify, Boolean archive, Pageable pageable) {
    return dreamsRepository.findAllProductsRandomly(verify,archive,pageable);
  }
}
