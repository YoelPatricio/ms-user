package com.smartjob.challange.model.request;

import com.smartjob.challange.constants.RegularExpresions;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserRequest {

  @Schema(description = "Nombre completo del usuario", example = "Juan Rodriguez")
  @NotBlank(message = "El nombre es obligatorio")
  private String name;

  @Schema(description = "Correo electrónico del usuario", example = "juan@rodriguez.cl")
  @Pattern(regexp = RegularExpresions.REGEX_EMAIL, message = "El correo no es válido")
  @NotBlank(message = "El correo es obligatorio")
  private String email;

  @Schema(description = "Contraseña del usuario", example = "hunter25")
  @NotBlank(message = "La contraseña es obligatoria")
  private String password;

  @Valid
  private List<PhoneRequest> phones;

}
