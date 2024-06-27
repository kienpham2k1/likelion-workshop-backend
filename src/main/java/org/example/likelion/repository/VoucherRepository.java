package org.example.likelion.repository;

import org.example.likelion.model.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {
    @Query("select v from Voucher v where v.isDelete    = false")
    Page<Voucher> findAllByIsDeleteIsFalse(Pageable pageable);
}
