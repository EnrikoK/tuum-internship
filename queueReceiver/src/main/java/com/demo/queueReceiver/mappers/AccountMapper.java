package com.demo.queueReceiver.mappers;

import com.demo.queueReceiver.models.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Select("SELECT * FROM accounts where account_id = #{accountId};")
    @Results({
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "balances", javaType = List.class, column = "account_id",
                    many = @Many(select = "com.demo.queueReceiver.mappers.AccountBalancesMapper.getUserBankAccountByUserId"))
    })
    Account getUserAccountDetailsByAccountId(@Param("accountId") long accountId);

    @Select("SELECT COUNT(*) from accounts where customer_id = #{customerId};")
    @Results({
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "balances", javaType = List.class, column = "account_id",
                    many = @Many(select = "com.demo.queueReceiver.mappers.AccountBalancesMapper.getUserBankAccountByUserId"))
    })
    Account getUserAccountByCustomerId(@Param("customerId") String customerId);


    @Select("INSERT INTO accounts (customer_id,country) VALUES (#{customerId}, #{country}) RETURNING account_id;")
    @Options(useGeneratedKeys = true, keyColumn = "account_id")
    Long createUserAccount(@Param("customerId") String customerId, @Param("country") String country);
}
