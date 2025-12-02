package br.com.challenge.employee_checkin.controllers.docs;

import br.com.challenge.employee_checkin.dtos.LoginRequest;
import br.com.challenge.employee_checkin.dtos.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerDocs {
    @Operation(
            summary = "Autentica um usuário e cria uma sessão",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login realizado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "loginSuccess",
                                                    value = """
                            {
                              "id": 1,
                              "name": "Employee 01",
                              "email": "employee@grupomoura.com",
                              "role": "USER",
                              "lastWorkRecord": {
                                "checkInTime": "2025-12-01T10:40:04.132101",
                                "checkOutTime": null
                              }
                            }
                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
                    @ApiResponse(responseCode = "500", description = "Erro inesperado")
            }
    )
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest);
}
