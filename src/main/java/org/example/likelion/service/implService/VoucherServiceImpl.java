package org.example.likelion.service.implService;

import lombok.RequiredArgsConstructor;
import org.example.likelion.constant.ErrorMessage;
import org.example.likelion.exception.EntityNotFoundException;
import org.example.likelion.model.Voucher;
import org.example.likelion.repository.VoucherRepository;
import org.example.likelion.service.VoucherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;

    @Override
    public List<Voucher> gets() {
        return voucherRepository.findAll();
    }

    @Override
    public Page<Voucher> gets(Pageable pageable) {
        return voucherRepository.findAllByIsDeleteIsFalse(pageable);
    }

    @Override
    public Voucher get(String id) {
        return voucherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.VOUCHER_DETAIL_NOT_FOUND));
    }

    @Override
    public Voucher create(Voucher voucher) {
        voucher.setCreate_date(LocalDate.now());
        voucher.setDeleted(false);
        voucher.setActive(true);
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher update(String id, Voucher voucher) {
        voucherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.VOUCHER_DETAIL_NOT_FOUND));
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher delete(String id) {
        Voucher vo = voucherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.VOUCHER_DETAIL_NOT_FOUND));
        vo.setDeleted(true);
        return voucherRepository.save(vo);
    }

    @Override
    public Voucher updateStatus(String id) {
        Voucher vo = voucherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.VOUCHER_DETAIL_NOT_FOUND));
        // Change status by negative of status
        vo.setActive(!vo.isActive());
        return voucherRepository.save(vo);
    }

    @Override
    public void reduce(String id) {
        Voucher vo = voucherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.VOUCHER_DETAIL_NOT_FOUND));
        vo.setQuantity(vo.getQuantity() - 1);
        voucherRepository.save(vo);
    }
}
