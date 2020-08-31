package io.ilss.transaction.twodatasource.web;

import io.ilss.transaction.twodatasource.entities.OrderInfoDO;
import io.ilss.transaction.twodatasource.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author feng
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public String create() {
        OrderInfoDO orderInfoDO = new OrderInfoDO();
        orderInfoDO.setAccountId(1L);
        orderInfoDO.setAmount(new BigDecimal(10L));
        orderInfoDO.setDetail("buy something");
        orderInfoDO.setCompleted(0);
        orderInfoDO.setOrderState(0);
        orderInfoDO.setUpdateTime(LocalDateTime.now());
        orderInfoDO.setCreateTime(LocalDateTime.now());

        return orderService.createOrder(orderInfoDO);
    }

    @GetMapping("/create/code")
    public String createCode() {
        OrderInfoDO orderInfoDO = new OrderInfoDO();
        orderInfoDO.setAccountId(1L);
        orderInfoDO.setAmount(new BigDecimal(20L));
        orderInfoDO.setDetail("buy book");
        orderInfoDO.setCompleted(0);
        orderInfoDO.setOrderState(0);
        orderInfoDO.setUpdateTime(LocalDateTime.now());
        orderInfoDO.setCreateTime(LocalDateTime.now());

        return orderService.createOrderCode(orderInfoDO);
    }

}
