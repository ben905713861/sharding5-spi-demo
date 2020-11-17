# sharding5-spi-demo
In sharding-5+, we can not write classname in sharding config. This demo shows how to use newest SPI style to add a customer configuation in sharding-proxy-5.0.0

在sharding5以上的版本中，将不再允许以添加类名的方式来配置分片规则，这个demo展示如何使用全新的SPI风格来添加sharding-proxy5的自定义配置。

After packaging the above items, copy them to the EXT "ext-lib/" folder of the sharding-proxy5 project, and modify the partition rule configuration file config-sharding.yaml

将上面的项目打包后，复制到sharding-proxy5项目的ext-lib/文件夹下，并修改分片规则配置文件config-sharding.yaml

rules:
- !SHARDING
  tables:
    wx_user:
      actualDataNodes: huodong-user.wx_user_${0..2}
      tableStrategy:
        complex:
          shardingColumns: wxId,openId
          shardingAlgorithmName: wx_user_complex
      keyGenerateStrategy:
        column: wxUserId
        keyGeneratorName: snowflake
  shardingAlgorithms:
    wx_user_complex:
      type: WxUserShardingAlgorithm
      