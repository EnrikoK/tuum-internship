package com.tuum.demo.mappers;

import com.tuum.demo.models.Balance;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountBalancesMapper {


    @Select("SELECT * from account_balances WHERE account_id = #{account_id}")
    @Results({
            @Result(property = "amount", column = "amount"),
            @Result(property = "currency", column = "currency")
    })
    List<Balance> getUserBankAccountByUserId(@Param("account_id") long account_id);
}
