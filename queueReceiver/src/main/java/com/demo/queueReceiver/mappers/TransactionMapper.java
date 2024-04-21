package com.demo.queueReceiver.mappers;


import com.demo.queueReceiver.models.Transaction;
import com.demo.queueReceiver.utils.enums.Currency;
import com.demo.queueReceiver.utils.enums.TransactionDirection;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TransactionMapper {

    @Select("INSERT INTO transactions (account_id, amount, currency, transaction_direction, description) " +
            "VALUES (#{accountId}, #{amount}, #{currency}, #{direction}, #{description}) " +
            "RETURNING transaction_id, account_id, amount, currency, transaction_direction, description")
    @Options(useGeneratedKeys = true, keyColumn = "transaction_id")
    @Results({
            @Result(property = "transactionId", column = "transaction_id"),
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "currency", column = "currency"),
            @Result(property = "direction", column = "transaction_direction"),
            @Result(property = "description", column = "description")
    })
    Transaction createNewTransaction(@Param("accountId") long accountId,
                                     @Param("amount") double amount,
                                     @Param("currency") String currency,
                                     @Param("direction") String direction,
                                     @Param("description") String description);

}
