package com.dayoo.huodong;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

public class WxUserShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {
	
	private Properties props = new Properties();
	
	public Collection<String> doSharding(Collection<String> availableTableNames, ComplexKeysShardingValue<String> shardingValues) {
		HashSet<String> routTables = new HashSet<String>();
		Map<String, Collection<String>> paramsMap = shardingValues.getColumnNameAndShardingValuesMap();
		//Here is the logic of sharding params counter,I use "wxId" and "openId" params in this project,so it is 2.
		//这里的数字为参与分表规则字段的个数，此处用到"wxId,openId"两个字段，因此是2
		if(paramsMap.size() < 2) {
			//Not satisfied,want to query all tables.
			//不满足条件，全库查询
			return availableTableNames;
		}
		//build complex key
		//使用多字段组合键
		StringBuffer combinedIndexKey = new StringBuffer();
		paramsMap.forEach((key, value) -> {
			combinedIndexKey.append(value.toString());
		});
		//Modular operation
		//求模
		int hashCode = combinedIndexKey.toString().hashCode() & Integer.MAX_VALUE;
		String modVal = String.valueOf(hashCode % 3);
		for(String targetName : availableTableNames) {
			if(targetName.endsWith(modVal)) {
				routTables.add(targetName);
			}
		}
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
