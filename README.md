# Sharding-Jdbc集成mybatis-plus实现分库分表解决方案

项目地址：
* [Github](https://github.com/silianpan/seal-sharding)
* [Gitee](https://gitee.com/twofloor/seal-sharding)

官方文档：
* [Sharding-JDBC](https://shardingsphere.apache.org/document/legacy/3.x/document/cn/overview/)
* [Mybatis-Plus](https://mp.baomidou.com/)

## 一、运行测试
* Step 1：新建两个数据库分别为**seal_sharding、seal_sharding1**
* Step 2：分别导入db目录下sql文件
* Step 3：打开项目，配置环境变量**MYSQL_HOST、MYSQL_PORT**以及用户名和密码
* Step 4：运行测试文件（test包下）

## 二、租户ID的获取
> 修改config包下MybaitsPlusConfig.java，获取当前登录用户的租户ID，这里默认写1
```java
@Override
public Expression getTenantId() {
    try {
        // 获取当前登录用户租户ID
        String tenantId = "1";
        if (!StrUtil.isEmpty(tenantId)) {
            return new LongValue(tenantId);
        }
        throw new Exception(RetBack.errorJson(4001, "该租户不存在"));
    } catch (Exception e) {
        e.printStackTrace();
    }
    return new LongValue(-1L);
}
```

## 三、分库分表策略
* 分库：根据表字段 **租户ID（tenant_id）** 模2（奇偶数）进行分库
* 分表：根据表字段 **id** 去hascode模2（奇偶数）进行发分表，分布式主键采用**雪花算法（SNOWFLAKE）**，详见：[Sharding-JDBC](https://shardingsphere.apache.org/document/legacy/3.x/document/cn/features/sharding/other-features/key-generator/)

## 三、只分表不分库
本项目为两个数据源实现分库和分表，如果只分表不分库，只需要添加一个数据源即可

## 四、实现注意点
* [Sharding-JDBC](https://shardingsphere.apache.org/document/legacy/3.x/document/cn/overview/)采用**3.x**版本
* 需要复写MybaitsPlus配置
* 排除自动数据源加载
```java
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, 
DataSourceAutoConfiguration.class})
```

## 附、Mybatis-Plus多数据源

如果不使用Sharding-JDBC，而需要**多数据源**，可以采用**Mybaits-Plus多数据源方案**，详见[官方文档](https://mp.baomidou.com/guide/dynamic-datasource.html)，后面我会把**示例项目**分享给大家，敬请期待。可以加我互相交流。

**<center style="font-size:18px">开源不易，且用且珍惜！</center>**

<br>
<center style="font-size:16px">赞助作者，互相交流</center>
<br>

<div style="display:flex;justify-content:center;align-item:center">
<img src="https://user-gold-cdn.xitu.io/2020/5/29/1725e23e1228866e?w=1080&h=1080&f=png&s=342366" style="width:180px;height:180px;padding:20px"/>

<img src="https://user-gold-cdn.xitu.io/2020/5/29/1725e23e121cbaa2?w=300&h=299&f=gif&s=20783" style="width:180px;height:180px;padding:20px"/>
</div>
