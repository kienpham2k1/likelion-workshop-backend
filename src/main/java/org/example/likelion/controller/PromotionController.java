package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.request.PromotionRequest;
import org.example.likelion.service.PromotionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promotion")
@Tag(name = "Promotion Resource")
public class PromotionController {
    private final PromotionService promotionService;


    @Operation(summary = "Create Promotion")
    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody @Valid PromotionRequest request) {
        long daysBetween = ChronoUnit.DAYS.between(request.getStartedDate(), request.getExpiredDate());
        if (daysBetween <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Start and end dates must be within a 1-day range");
        }
        promotionService.create(request);
        return null;
    }
}
