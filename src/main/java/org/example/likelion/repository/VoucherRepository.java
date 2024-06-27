package org.example.likelion.repository;

import org.example.likelion.model.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {
    @Query("select v from Voucher v where v.deleted = false")
    Page<Voucher> findAllByIsDeleteIsFalse(Pageable pageable);

    @Query("select v from Voucher v where v.id = :id and v.active = true and v.quantity > 0")
    Voucher findVoucherByIdAndActiveIsTrueAndQuantityGreaterThan(@Param("id") String id);
}