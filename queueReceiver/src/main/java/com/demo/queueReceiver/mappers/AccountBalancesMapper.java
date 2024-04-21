package com.demo.queueReceiver.mappers;

import com.demo.queueReceiver.models.Balance;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface AccountBalancesMapper {


    @Select("SELECT * from account_balances WHERE account_id = #{account_id};")
    @Results({
            @Result(property = "amount", column = "amount"),
            @Result(property = "currency", column = "currency")
    })
    List<Balance> getUserBankAccountByUserId(@Param("account_id") long account_id);

    @Insert("INSERT INTO account_balances (account_id,currency, amount) VALUES (#{accountId}, #{currency}, 0);")
    void createEmptyUserBalance(@Param("accountId") long accountId, @Param("currency") String currency);


    @Select("UPDATE account_balances SET amount = #{amount} WHERE account_id = #{accountId} AND currency = #{currency} RETURNING amount;")
    Double updateBalance(@Param("accountId") long accountId, @Param("amount") double amount, @Param("currency") String currency);

    @Select("SELECT amount FROM account_balances WHERE account_id = #{accountId} AND currency = #{currency};")
    Double getCurrencyAmountForAccount(@Param("accountId") long accountId, @Param("currency") String currency);
}
