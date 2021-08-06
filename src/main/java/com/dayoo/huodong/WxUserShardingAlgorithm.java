package com.dayoo.huodong;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WxUserShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    private static Logger logger = LoggerFactory.getLogger(WxUserShardingAlgorithm.class);
    private Properties props = new Properties();

    @Override
    public Collection<String> doSharding(Collection<String> availableTableNames,
        ComplexKeysShardingValue<String> shardingValues) {
        HashSet<String> routTables = new HashSet<String>();
        Map<String, Collection<String>> paramsMap = shardingValues.getColumnNameAndShardingValuesMap();
        // Here is the logic of sharding params counter,I use "wxid" and "openid" params in this project,so it is 2.
        // 这里的数字为参与分表规则字段的个数，此处用到"wxid,openid"两个字段，因此是2
        if (paramsMap.size() < 2) {
            // Not satisfied,want to query all tables.
            // 不满足条件，全库查询
            return availableTableNames;
        }
        if (!paramsMap.containsKey("wxid") || !paramsMap.containsKey("openid")) {
            // 不满足条件，全库查询
            return availableTableNames;
        }
        // build complex key
        // 使用多字段组合键
        logger.debug("==========================paramsMap: {}", paramsMap);

        Collection<String> wxids = paramsMap.get("wxid");
        if (wxids.size() <= 0 || wxids.size() > 1) {
            // wxid为空或者wxid使用in查询，不满足条件，全库查询
            return availableTableNames;
        }
        Object wxid = wxids.iterator().next();

        Collection<String> openids = paramsMap.get("openid");
        if (openids.size() <= 0) {
            // openid为空，不满足条件，全库查询
            return availableTableNames;
        }
        openids.forEach(openid -> {
            String complexKey = wxid + "-" + openid;
            logger.debug("===========================complexKey: {}", complexKey);
            // 求模
            int hashCode = complexKey.hashCode() & Integer.MAX_VALUE;
            String modVal = String.valueOf(hashCode % 3);
            for (String targetName : availableTableNames) {
                if (targetName.endsWith(modVal)) {
                    routTables.add(targetName);
                }
            }
        });
        logger.debug("==================routTables: {}", routTables);
        return routTables;
    }

    @Override
    public Properties getProps() {
        return props;
    }

    @Override
    public String getType() {
        return "WxUserShardingAlgorithm";
    }

    @Override
    public void setProps(Properties props) {
        this.props = props;
    }

    @Override
    public void init() {

    }

}
