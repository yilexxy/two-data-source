package io.ilss.transaction.twodatasource.service;

import io.ilss.transaction.twodatasource.entities.OrderInfoDO;

/**
 * @author feng
 */
public interface OrderService {
    String createOrder(OrderInfoDO orderInfoDO);

    String createOrderCode(OrderInfoDO orderInfoDO);

}
