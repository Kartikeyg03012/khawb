package com.ngo.khawb.service;

import com.ngo.khawb.model.ContactUsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface ContactUsService {
  public ContactUsModel addQuery(ContactUsModel contactUsModel);

  public Page<ContactUsModel> getAllQueriesOfCustomer(Pageable pageable);

  public ContactUsModel getQueryDetailById(int id);

  public Page<ContactUsModel> sortQueryByStatus(String status, Pageable pageable);
}
