package org.example.likelion.service;

import org.example.likelion.model.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoucherService {
    List<Voucher> gets();

    Page<Voucher> gets(Pageable pageable);

    Voucher get(String id);

    Voucher create(Voucher voucher);

    Voucher update(String id, Voucher voucher);

    Voucher delete(String id);

    Voucher updateStatus(String id);

    void reduce(String id);
}
