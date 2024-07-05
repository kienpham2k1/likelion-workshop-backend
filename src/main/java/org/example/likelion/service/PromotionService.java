package org.example.likelion.service;


import org.example.likelion.dto.request.PromotionRequest;
import org.example.likelion.model.Promotion;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface PromotionService {
    List<Promotion> gets();

    Page<Promotion> gets(Pageable pageable);

    Promotion get(String id);

    Promotion create(PromotionRequest request);

    Promotion update(String id, Promotion promotion);

    Promotion delete(String id);
}
