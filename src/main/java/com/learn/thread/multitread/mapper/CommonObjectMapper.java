package com.learn.thread.multitread.mapper;

import com.learn.thread.multitread.models.Information;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class CommonObjectMapper {

  public List<Information> map(CompletableFuture<List<Information>> informationList) {
    List<Information> infos = new ArrayList<>();
    for (var check : informationList.join()) {
      infos.add(check);
    }
    return infos;
  }
}
