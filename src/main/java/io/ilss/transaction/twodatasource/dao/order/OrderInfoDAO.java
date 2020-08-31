package io.ilss.transaction.twodatasource.dao.order;

import io.ilss.transaction.twodatasource.entities.OrderInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderInfoDAO {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfoDO record);

    int insertSelective(OrderInfoDO record);

    OrderInfoDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfoDO record);

    int updateByPrimaryKey(OrderInfoDO record);
}