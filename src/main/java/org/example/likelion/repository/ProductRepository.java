package org.example.likelion.repository;

import org.example.likelion.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("select new Product ( p.name, p.description, p.price, cast(sum(p.quantity) as int), min(p.imgLink), p.categoryId, p.category)" +
            "from Product p " +
            "where (:name is null or upper(CAST(p.name as string)) like upper(CAST(concat('%', :name, '%')as string))) " +
            "and (:categoryId is null or p.categoryId = :categoryId) " +
            "and (:sizes is null or p.size in  :sizes) " +
            "and (:colors is null or upper(p.color) in :colors) " +
            "and (p.price  >= :priceMin or :priceMin is null)" +
            "and (p.price  <= :priceMax OR :priceMax is null)" +
            "group by p.name, p.description, p.price, p.categoryId, p.category")
    Page<Product> findAllByFilter(@Param("name") String name,
                                  @Param("categoryId") String categoryId,
                                  @Param("sizes") List<Integer> sizes,
                                  @Param("colors") List<String> colors,
                                  @Param("priceMin") Double priceMin,
                                  @Param("priceMax") Double priceMax,
                                  Pageable pageable);

    @Query("select p " +
            "from Product p " +
            "where (:categories is null or upper(p.category.name) in :categories) " +
            "and (:colors is null or upper(p.color) in :colors)")
    List<Product> findAllByFilter(@Param("categories") List<String> categories,
                                  @Param("colors") List<String> colors,
                                  Pageable pageable);

    List<Product> findAllByName(String productName);

    @Query(value = "select p.category.name from Product p group by p.category.name")
    List<String> findProductCategories();

    @Query(value = "select p.color from Product p group by p.color")
    List<String> findProductColors();
}
