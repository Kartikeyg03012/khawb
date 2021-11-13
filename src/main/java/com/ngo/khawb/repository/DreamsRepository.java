package com.ngo.khawb.repository;

import com.ngo.khawb.model.Dreams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DreamsRepository extends JpaRepository<Dreams, Long> {

  @Query("from Dreams as d where d.user.id=:userId ORDER by id DESC")
  public List<Dreams> getAllDreamsByUserId(@Param("userId") long userId);

  @Query("from Dreams as p ORDER by id DESC")
  public Page<Dreams> findAll(Pageable pageAble);

  @Query(
      "SELECT p FROM Dreams p WHERE p.adminVerified=:verify AND p.archive=:archive ORDER BY RAND()")
  public Page<Dreams> findAllProductsRandomly(
      @Param("verify") Boolean verify, @Param("archive") Boolean archive, Pageable pageAble);
}
