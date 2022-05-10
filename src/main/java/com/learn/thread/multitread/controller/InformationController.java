package com.learn.thread.multitread.controller;

import com.learn.thread.multitread.models.Information;
import com.learn.thread.multitread.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class InformationController {

  @Autowired
  private InformationService informationService;

  @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
  public ResponseEntity<List<Information>> saveInformation(@RequestParam("file") MultipartFile information) throws Exception {

    return ResponseEntity.ok(informationService.saveInformations(information));
  }

  @GetMapping(value = "/upload", produces = "application/json")
  public CompletableFuture<ResponseEntity> getInformation() {
    return informationService.getInformations().thenApply(ResponseEntity::ok);
  }

}
