package com.tuum.demo.mappers;

import com.tuum.demo.models.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Select("SELECT * FROM transactions WHERE account_id = #{accountId}")
    @Results({
            @Result(property = "transactionId", column = "transaction_id"),
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "currency", column = "currency"),
            @Result(property = "direction", column = "transaction_direction"),
            @Result(property = "description", column = "description")
    })
    List<Transaction> getAllTransactionsByAccountId(@Param("accountId")long accountId);
}
