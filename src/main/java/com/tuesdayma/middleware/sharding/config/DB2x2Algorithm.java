package com.tuesdayma.middleware.sharding.config;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigDecimal;
import java.util.Collection;

public class DB2x2Algorithm implements PreciseShardingAlgorithm<String> {
    public static final int TABLE_SIZE_PER_DB = 2;

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        int hashVal = Hashing.md5().hashString(shardingValue.getValue(), Charsets.UTF_8).asInt();
        int value = BigDecimal.valueOf(hashVal)
                .remainder(BigDecimal.valueOf(Table2x2Algorithm.TABLE_COUNT))
                .divide(BigDecimal.valueOf(TABLE_SIZE_PER_DB))
                .abs()
                .intValue();
        for (String targetName : availableTargetNames) {
            if (targetName.endsWith(String.valueOf(value))) {
                return targetName;
            }
        }
        return null;
    }

}