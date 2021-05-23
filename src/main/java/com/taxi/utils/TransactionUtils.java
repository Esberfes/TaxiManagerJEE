package com.taxi.utils;

import javax.transaction.Transactional;
import java.util.function.Supplier;

public class TransactionUtils {

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public static <T> T executeInTransaction(Supplier<T> producer) {
        if (producer != null)
            return producer.get();
        return null;
    }
}
