package com.smartjob.challange.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {

  private String id;
  private LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime lastLogin;
  private String token;
  private Boolean isActive;

}
