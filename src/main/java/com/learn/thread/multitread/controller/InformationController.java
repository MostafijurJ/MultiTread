package com.learn.thread.multitread.controller;

import com.learn.thread.multitread.models.Information;
import com.learn.thread.multitread.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class InformationController {

  @Autowired
  private InformationService informationService;

  @PostMapping(value = "/upload", consumes = "multipart/csv", produces = "application/json")
  public ResponseEntity<List<Information>> saveInformation(@RequestParam("file") MultipartFile information) throws Exception {

    return ResponseEntity.ok(informationService.saveInformations(information));
  }

}
