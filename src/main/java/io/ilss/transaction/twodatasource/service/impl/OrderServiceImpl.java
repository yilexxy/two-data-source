package io.ilss.transaction.twodatasource.service.impl;

import io.ilss.transaction.twodatasource.dao.account.AccountDAO;
import io.ilss.transaction.twodatasource.dao.order.OrderInfoDAO;
import io.ilss.transaction.twodatasource.entities.AccountDO;
import io.ilss.transaction.twodatasource.entities.OrderInfoDO;
import io.ilss.transaction.twodatasource.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.config.JtaTransactionManagerBeanDefinitionParser;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 测试分布式事务-定单业务
 * @author yile
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderInfoDAO orderInfoDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    PlatformTransactionManager transactionManager;

    /**
     * 注入的方式测试分布式事务
     * @param orderInfoDO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createOrder(OrderInfoDO orderInfoDO) {
        AccountDO accountDO = accountDAO.selectByPrimaryKey(orderInfoDO.getAccountId());
        if (null == accountDO) {
            return "用户不存在！";
        }
        // 用户费用扣除
        accountDO.setBalance(accountDO.getBalance().subtract(orderInfoDO.getAmount()));
        accountDAO.updateByPrimaryKey(accountDO);
        error("createOrder error");

        orderInfoDAO.insertSelective(orderInfoDO);

        return "成功(-10)";
    }

    /**
     * 非注入的方式测试分布式事务
     * @param orderInfoDO
     * @return
     */
    @Override
    public String createOrderCode(OrderInfoDO orderInfoDO) {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // 获取事务 开始业务执行
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);
        try {
            AccountDO accountDO = accountDAO.selectByPrimaryKey(orderInfoDO.getAccountId());
            if (null == accountDO) {
                return "用户不存在！";
            }
            // 用户费用扣除
            accountDO.setBalance(accountDO.getBalance().subtract(orderInfoDO.getAmount()));
            accountDAO.updateByPrimaryKey(accountDO);
            error("createOrderCode error");

            orderInfoDAO.insertSelective(orderInfoDO);
            transactionManager.commit(transaction);

            return "成功(-20)";
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            return "失败";
        }

    }

    public static void error(String  msg) {
        throw new RuntimeException(msg);
    }
}
