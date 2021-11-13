package com.ngo.khawb.service.impl;

import com.ngo.khawb.model.ContactUsModel;
import com.ngo.khawb.repository.ContactRepository;
import com.ngo.khawb.service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactUsServiceImpl implements ContactUsService {

  @Autowired ContactRepository contactRepository;

  @Override
  public ContactUsModel addQuery(ContactUsModel contactUsModel) {
    return contactRepository.save(contactUsModel);
  }

  @Override
  public Page<ContactUsModel> getAllQueriesOfCustomer(Pageable pageable) {
    return contactRepository.findAll(pageable);
  }

  @Override
  public ContactUsModel getQueryDetailById(int id) {
    return contactRepository.getById(id);
  }

  @Override
  public Page<ContactUsModel> sortQueryByStatus(String status, Pageable pageable) {
    return contactRepository.getContactUsModelByStatus(status, pageable);
  }
}
