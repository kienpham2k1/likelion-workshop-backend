package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.likelion.dto.mapper.IOrderMapper;
import org.example.likelion.dto.request.OrderRequest;
import org.example.likelion.dto.response.OrderResponse;
import org.example.likelion.enums.OrderStatus;
import org.example.likelion.model.Order;
import org.example.likelion.service.OrderDetailService;
import org.example.likelion.service.OrderService;
import org.example.likelion.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@Tag(name = "Order Resource")
public class OrderController {
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final ProductService productService;

    @Operation(summary = "Get Order List")
    @GetMapping("/getList")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getProducts() {
        return orderService.gets().stream().map(IOrderMapper.INSTANCE::toDtoResponse).toList();
    }

    @Operation(summary = "Get Order List Filter")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderResponse> getProducts(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "asc") String sortDirection,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return orderService.gets(pageable).map(IOrderMapper.INSTANCE::toDtoResponse);
    }

    @Operation(summary = "Get Order by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getCategory(@PathVariable String id) {
        return IOrderMapper.INSTANCE.toDtoResponse(orderService.get(id));
    }

    @Operation(summary = "Create Order")
    @PostMapping("/create")
    public Order create(@RequestBody @Valid OrderRequest request) {
        return orderService.create(IOrderMapper.INSTANCE.toEntity(request));
    }

//    @Operation(summary = "Update Order")
//    @PutMapping("/update/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void update(@PathVariable String id, @RequestBody @Valid OrderRequest request) {
//        orderService.update(id, IOrderMapper.INSTANCE.toEntity(request));
//    }

    @Operation(summary = "Update Order")
    @PutMapping("/update-status/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStatus(@PathVariable String id, @RequestBody @Valid OrderStatus status) {
        orderService.updateStatus(id, status);
    }

    @Operation(summary = "Cancel Order")
    @DeleteMapping("/cancel/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void create(@PathVariable String id) {
        orderService.cancel(id);
    }

    @Operation(summary = "Export report")
    @GetMapping("/export-report")
    @ResponseStatus(HttpStatus.OK)
    public void exportMonthlyReport(HttpServletResponse response) {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=order_completed_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Order> orderList = orderService.gets();
        try {
            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            String[] csvHeader = {"Order ID", "User ID", "Total cost", "Phone Number", "Address", "Create Date", "Status", "Is Cancel", "Is Online Payment", "Is Paid"};
            String[] nameMapping = {"id", "userId", "total", "phoneNumber", "addressLine", "createDate", "orderStatus", "cancel", "cancel", "paid",};
            csvWriter.writeHeader(csvHeader);

            for (Order order : orderList) {
                csvWriter.write(order, nameMapping);
            }

            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
