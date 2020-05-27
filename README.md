# Sharding-Jdbc集成mybatis-plus实现分库分表解决方案

## 一、运行测试
* 新建两个数据库分别为seal_sharding、seal_sharding1
* 分别导入db目录下sql文件
* 打开项目，配置环境变量MYSQL_HOST、MYSQL_PORT以及用户名和密码
* 运行测试文件（test包下）

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

## 三、只分表不分库
> 本项目为两个数据源实现分库和分表，如果只分表不分库，只需要添加一个数据源即可

## 四、实现注意点
* 需要复写MybaitsPlus配置
* 排除自动数据源加载
```java
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, DataSourceAutoConfiguration.class})
```
