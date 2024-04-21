package com.tuum.demo.mappers;

import com.tuum.demo.models.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Select("SELECT * FROM accounts where account_id = #{accountId};")
    @Results({
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "balances", javaType = List.class, column = "account_id",
                    many = @Many(select = "com.tuum.demo.mappers.AccountBalancesMapper.getUserBankAccountByUserId"))
    })
    Account getUserAccountDetailsByAccountId(@Param("accountId") long accountId);

}

