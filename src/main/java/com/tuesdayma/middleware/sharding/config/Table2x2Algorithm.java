package com.tuesdayma.middleware.sharding.config;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigDecimal;
import java.util.Collection;

public class Table2x2Algorithm implements PreciseShardingAlgorithm<String> {

    public static final int TABLE_COUNT = 4;

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        int hashVal = Hashing.md5().hashString(shardingValue.getValue(), Charsets.UTF_8).asInt();
        int value = BigDecimal.valueOf(hashVal)
                .remainder(BigDecimal.valueOf(TABLE_COUNT))
                .abs()
                .intValue();
        StringBuilder sb = new StringBuilder(shardingValue.getLogicTableName());
        sb.append("_" + value);
        return sb.toString();
    }
}