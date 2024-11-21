package com.smartjob.challange.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PhoneRequest {

  @Schema(description = "Número de teléfono", example = "980797726")
  @NotBlank(message = "El número es obligatorio")
  private String number;

  @Schema(description = "Código de ciudad", example = "1")
  @NotBlank(message = "El código de ciudad es obligatorio")
  private String citycode;

  @Schema(description = "Código de país", example = "57")
  @NotBlank(message = "El código de país es obligatorio")
  private String countrycode;

}
