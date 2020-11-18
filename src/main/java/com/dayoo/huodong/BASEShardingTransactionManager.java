package com.dayoo.huodong;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.shardingsphere.infra.database.type.DatabaseType;
import org.apache.shardingsphere.transaction.core.ResourceDataSource;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.spi.ShardingTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BASEShardingTransactionManager implements ShardingTransactionManager {

	private static Logger logger = LoggerFactory.getLogger(BASEShardingTransactionManager.class);
	
	@Override
	public void close() throws Exception {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void init(DatabaseType databaseType, Collection<ResourceDataSource> resourceDataSources) {
		// TODO 自动生成的方法存根
		logger.info("databaseType " + databaseType);
		logger.info("resourceDataSources " + resourceDataSources.toString());
	}

	@Override
	public TransactionType getTransactionType() {
		return TransactionType.BASE;
	}

	@Override
	public boolean isInTransaction() {
		return true;
	}

	@Override
	public Connection getConnection(String dataSourceName) throws SQLException {
		// TODO 自动生成的方法存根
		logger.info("dataSourceName:"+ dataSourceName);
		return null;
	}

	@Override
	public void begin() {
		// TODO 自动生成的方法存根
		logger.info("user begin");
	}

	@Override
	public void commit() {
		// TODO 自动生成的方法存根
		logger.info("user commit");
	}

	@Override
	public void rollback() {
		// TODO 自动生成的方法存根
		logger.info("user rollback");
	}

}
