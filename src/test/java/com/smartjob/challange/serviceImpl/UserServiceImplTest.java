package com.smartjob.challange.serviceImpl;

import com.smartjob.challange.model.request.PhoneRequest;
import com.smartjob.challange.model.request.UserRequest;
import com.smartjob.challange.model.response.UserResponse;
import com.smartjob.challange.repository.UserRepository;
import com.smartjob.challange.repository.entity.User;
import com.smartjob.challange.service.impl.UserServiceImpl;
import com.smartjob.challange.util.JwtUtil;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private JwtUtil jwtUtil;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    ReflectionTestUtils.setField(userService, "passwordRegex", passwordRegex);
  }

  @Test
  void register_Successful() {

    UserRequest userRequest = buildMockDataUserRequest();

    when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
    when(jwtUtil.generateToken(any(User.class))).thenReturn("fake-jwt-token");
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    UserResponse result = userService.register(userRequest);

    assertNotNull(result.getCreated());
    assertNotNull(result.getModified());
    assertNotNull(result.getLastLogin());
    assertEquals("fake-jwt-token", result.getToken());
    assertTrue(result.getIsActive());

    verify(userRepository).findByEmail(userRequest.getEmail());
    verify(jwtUtil).generateToken(any(User.class));
    verify(userRepository).save(any(User.class));

  }

  @Test
  void register_EmailAlreadyExists() {

    UserRequest user = buildMockDataUserRequest();

    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(new User()));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      userService.register(user);
    });

    assertEquals("El correo ya registrado", exception.getMessage());

    verify(userRepository, never()).save(any(User.class));
    verify(jwtUtil, never()).generateToken(any(User.class));

  }

  @Test
  void register_InvalidPasswordFormat() {

    UserRequest user = buildMockDataUserRequest();
    user.setPassword("123"); // Contraseña inválida

    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      userService.register(user);
    });

    assertEquals("La contraseña no cumple con el formato requerido", exception.getMessage());

    verify(userRepository, never()).save(any(User.class));
    verify(jwtUtil, never()).generateToken(any(User.class));

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

