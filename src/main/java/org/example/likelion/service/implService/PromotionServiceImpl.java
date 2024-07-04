package org.example.likelion.service.implService;

import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IPromotionMapper;
import org.example.likelion.dto.request.PromotionRequest;
import org.example.likelion.model.Category;
import org.example.likelion.model.Promotion;
import org.example.likelion.repository.PromotionRepository;
import org.example.likelion.service.CategoryService;
import org.example.likelion.service.PromotionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final CategoryService categoryService;

    @Override
    public List<Promotion> gets() {
        return promotionRepository.findAll();
    }

    @Override
    public Page<Promotion> gets(Pageable pageable) {
        return null;
    }

    @Override
    public Promotion get(String id) {
        return null;
    }

    @Override
    public Promotion create(PromotionRequest request) {
        Set<Category> categories = new HashSet<>();
        for (String categoryId : request.getCategory_id()) {
            categories.add(categoryService.get(categoryId));
        }
        request.setCategories(categories);
        return promotionRepository.save(IPromotionMapper.INSTANCE.toEntity(request));
    }

    @Override
    public Promotion update(String id, Promotion promotion) {
        promotionRepository.findById(id).orElse(null);

        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion delete(String id) {
        Promotion pro = promotionRepository.findById(id).orElse(null);
        pro.setActive(false);
        return promotionRepository.save(pro);
    }
}
