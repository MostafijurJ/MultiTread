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
    long startTime = System.currentTimeMillis();
    log.info("Async task started at {}", startTime, "------------------------------- Thread name: ", Thread.currentThread().getName());
    var infos = parseCSVFile(file);
    log.info("parsed files in {}",infos);

    try{
      informationRepo.saveAll(infos);
    }catch (Exception e){
      log.error("Failed to save informations {}", e);
    }
    long endTime = System.currentTimeMillis();
    log.info("Async task ended at {}", (endTime-startTime), "------------------------------- Thread name: ", Thread.currentThread().getName());
    var resp = CompletableFuture.completedFuture(infos);
    return mapper.map(resp);
  }


  public CompletableFuture<List<Information>> getInformations() {
    return CompletableFuture.completedFuture(informationRepo.findAll());
  }


  private List<Information> parseCSVFile(final MultipartFile file) throws Exception {
    final List<Information> Informations = new ArrayList<>();
    try {
      try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
        // skip header line
        br.readLine();
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
  
  
}
