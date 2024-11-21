package com.smartjob.challange.controller;

import com.smartjob.challange.exception.ErrorResponse;
import com.smartjob.challange.model.request.UserRequest;
import com.smartjob.challange.model.response.UserResponse;
import com.smartjob.challange.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "API para operaciones relacionadas con usuarios")
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  @Operation(
          summary = "Registrar un nuevo usuario",
          description = "Crea un nuevo usuario en el sistema",
          responses = {
                  @ApiResponse(
                          responseCode = "201",
                          description = "Usuario creado exitosamente",
                          content = @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = UserResponse.class))
                  ),
                  @ApiResponse(
                          responseCode = "400",
                          description = "Solicitud inválida",
                          content = @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = ErrorResponse.class))
                  )
          }
  )
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Datos del usuario a registrar",
          required = true,
          content = @Content(schema = @Schema(implementation = UserRequest.class))
  )
  public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest) {
    try {
      UserResponse createdUser = userService.register(userRequest);
      return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}

