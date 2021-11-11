package com.ngo.khawb.service;

import com.ngo.khawb.model.Dreams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DreamService {

  Dreams addDreams(Dreams dreams);

  List<Dreams> getAllDreams(long userId);

  Dreams getDreamById(long id);

  Dreams updateDream(Dreams dream);

  void deleteDream(Dreams dream);

  List<Dreams> getWishListProducts(Iterable<Long> ids);

  Page<Dreams> getAll(Pageable pageable);
}
