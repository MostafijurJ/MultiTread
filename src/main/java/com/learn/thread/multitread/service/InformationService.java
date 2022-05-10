package com.learn.thread.multitread.service;

import com.learn.thread.multitread.mapper.CommonObjectMapper;
import com.learn.thread.multitread.models.Information;
import com.learn.thread.multitread.repo.InformationRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class InformationService {

  @Autowired private InformationRepo informationRepo;
  @Autowired private CommonObjectMapper mapper;

  @Async
  public List<Information> saveInformations(MultipartFile file) throws Exception {
    var infos = parseCSVFile(file);
    var entities = informationRepo.saveAll(infos);
    var resp = CompletableFuture.completedFuture(entities);
    return mapper.map(resp);
  }


  public CompletableFuture<List<Information>> getInformations() {
    return CompletableFuture.completedFuture(informationRepo.findAll());
  }


  private List<Information> parseCSVFile(final MultipartFile file) throws Exception {
    final List<Information> Informations = new ArrayList<>();
    try {
      try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
        String line;
        while ((line = br.readLine()) != null) {
          final String[] data = line.split(",");
          final Information Information = new Information();
          Information.setName(data[0]);
          Information.setEmail(data[1]);
          Information.setPhone(data[2]);
          Information.setAddress(data[3]);
          Informations.add(Information);
        }
        return Informations;
      }
    } catch (final IOException e) {
      log.error("Failed to parse CSV file {}", e);
      throw new Exception("Failed to parse CSV file {}", e);
    }
  }


  public void primeNumberto100() {
    for (int i = 1; i <= 100; i++) {
      if (i % 2 == 0) {
        System.out.println(i);
      }
    }
  }
  
//  private List<Information> extractInformation(MultipartFile file) {
//    List<Information> informationList = null;
//    try {
//
//      try {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
//
//        String line = reader.readLine();
//        while (line != null) {
//          String[] data = line.split(",");
//          Information information = new Information();
//          information.setName(data[0]);
//          information.setEmail(data[1]);
//          information.setPhone(data[2]);
//          information.setAddress(data[3]);
//
//          informationList.add(information);
//        }
//      } catch (Exception e) {
//        log.error("Error while reading file", e);
//      }
//    } catch (Exception e) {
//      log.error("Error while reading file", e);
//    }finally{
//      return informationList;
//    }
//    return informationList;
//  }
  
  
}
