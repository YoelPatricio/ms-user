package com.smartjob.challange.service;

import com.smartjob.challange.model.request.UserRequest;
import com.smartjob.challange.model.response.UserResponse;

public interface UserService {
  UserResponse register(UserRequest userRequest);

}

