package br.com.challenge.employee_checkin.controllers.docs;

import br.com.challenge.employee_checkin.dtos.CheckResponse;
import br.com.challenge.employee_checkin.dtos.WorkRecordReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface WorkControllerDocs {
    @Operation(
            summary = "Realiza o check-in do usuário logado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Check-in registrado com sucesso",
                            content = @Content(schema = @Schema(implementation = CheckResponse.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "500", description = "Erro inesperado")
            }
    )
    CheckResponse checkIn(@RequestHeader("Authorization") String tokenHeader);

    @Operation(
            summary = "Realiza o check-out do usuário logado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Check-out registrado com sucesso",
                            content = @Content(schema = @Schema(implementation = CheckResponse.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "500", description = "Erro inesperado")
            }
    )
    CheckResponse checkOut(@RequestHeader("Authorization") String tokenHeader);

    @Operation(
            summary = "Lista registros de trabalho com filtros opcionais",
            description = """
            Permite filtrar por nome, data inicial, data final e controlar paginação.
            Datas devem estar no formato: yyyy-MM-dd'T'HH:mm:ss (ex: 2025-12-01T00:00:00)
        """,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Registros encontrados",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    Page<WorkRecordReport> getWorkRecords(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader("Authorization") String tokenHeader
    );
}
