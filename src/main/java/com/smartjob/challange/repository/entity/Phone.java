package com.smartjob.challange.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "phones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String number;

  private String citycode;

  private String countrycode;

}

