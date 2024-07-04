package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IPromotionMapper;
import org.example.likelion.dto.request.PromotionRequest;
import org.example.likelion.dto.response.PromotionResponse;
import org.example.likelion.model.Promotion;
import org.example.likelion.service.PromotionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promotion")
@Tag(name = "Promotion Resource")
public class PromotionController {
    private final PromotionService promotionService;

    @Operation(summary = "Get Available Promotion")
    @GetMapping(value = "/get")
    public ResponseEntity<List<Promotion>> getAvailablePromotion() {

        return null;
    }

    @Operation(summary = "Get All Promotion")
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<PromotionResponse>> getAllPromotion() {
        return ResponseEntity.status(HttpStatus.OK).body(promotionService.gets().stream().map(IPromotionMapper.INSTANCE::toDtoResponse).toList());
    }

    @Operation(summary = "Get Promotion By Id")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<PromotionResponse> getPromotionById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(IPromotionMapper.INSTANCE.toDtoResponse(promotionService.get(id)));
    }

    @Operation(summary = "Get Promotion By Category Id")
    @GetMapping(value = "/getFromCate")
    public ResponseEntity<List<Promotion>> getPromotionByCateId(@RequestParam String categoryId) {
        return null;
    }

    @Operation(summary = "Update Status Promotion ")
    @PostMapping(value = "/updateStatus")
    public ResponseEntity<PromotionResponse> updateActivateStatus(@RequestParam String id) {
        return ResponseEntity.status(HttpStatus.OK).body(IPromotionMapper.INSTANCE.toDtoResponse(promotionService.delete(id)));
    }

    @Operation(summary = "Create Promotion")
    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@RequestBody @Valid PromotionRequest request) {
        long daysBetween = ChronoUnit.DAYS.between(request.getStartedDate(), request.getExpiredDate());
        if (daysBetween < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Start and end dates must be within a 1-day range");
        }
        promotionService.create(request);
        return ResponseEntity.status(HttpStatus.OK).body("Promotion created");
    }
}
