package org.example.likelion.repository;

import org.example.likelion.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {

//    @Query("SELECT p FROM Promotion p join p.categories c " +
//            "where p.active = true and c.id = :category_id " +
//            "and :today BETWEEN p.startedDate AND p.expiredDate")
//    Promotion findPromotionByTodayBetweenStartedDateAndExpiredDate
//            (@Param("today") LocalDate today, @Param("categoryId") LocalDate categoryId);

//    @Query("SELECT p FROM Promotion p join p.categories c " +
//            "where c.id = :category_id")
//    List<Promotion> findPromotionsByCategoryId(@Param("categoryId") String categorId);
}
