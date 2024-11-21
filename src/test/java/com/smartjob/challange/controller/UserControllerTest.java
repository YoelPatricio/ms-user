package com.smartjob.challange.controller;

import com.smartjob.challange.exception.ErrorResponse;
import com.smartjob.challange.model.request.PhoneRequest;
import com.smartjob.challange.model.request.UserRequest;
import com.smartjob.challange.model.response.UserResponse;
import com.smartjob.challange.repository.entity.User;
import com.smartjob.challange.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  private UserRequest userRequest;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    userRequest = buildMockDataUserRequest();
  }

  @Test
  void registerUser_Successful() {

    UserResponse userResponse = UserResponse.builder().build();
    userResponse.setId("uuid-generado");

    when(userService.register(any(UserRequest.class))).thenReturn(userResponse);

    ResponseEntity<?> response = userController.registerUser(userRequest);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(userResponse, response.getBody());
    verify(userService).register(any(UserRequest.class));

  }

  @Test
  void registerUser_EmailAlreadyExists() {

    when(userService.register(any(UserRequest.class)))
            .thenThrow(new IllegalArgumentException("El correo ya registrado"));

    ResponseEntity<?> response = userController.registerUser(userRequest);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody() instanceof ErrorResponse);
    assertEquals("El correo ya registrado", ((ErrorResponse) response.getBody()).getMensaje());
    verify(userService).register(any(UserRequest.class));

  }

  private UserRequest buildMockDataUserRequest() {
    return UserRequest.builder()
            .name("Juan Rodriguez")
            .email("juan@rodriguez.org")
            .password("hunter2A")
            .phones(Collections.singletonList(PhoneRequest.builder()
                    .number("1234567")
                    .citycode("1")
                    .countrycode("57")
                    .build()))
            .build();
  }

}

