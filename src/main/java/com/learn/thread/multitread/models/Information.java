package com.learn.thread.multitread.models;

import lombok.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Information {

  @Id
  private Integer id;

  private String name;

  private String email;

  private String phone;

  private String address;

}
