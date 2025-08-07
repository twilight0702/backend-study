# 使用

需要先定义好entity类

然后创建对应mapper类
```java
import org.apache.ibatis.annotations.Mapper; // 注意不要导入错了

@Mapper
public interface PopUpMapper extends BaseMapper<对应Entity> {  
  
}
```

但如果有配置类配置了自动扫描，也可以不用@Mapper

比如配置类 `MybatisPlusConfig` 中：

```java
@Configuration
@MapperScan("com.jeeplus.**.mapper.**")
public class MybatisPlusConfig {
    ...
}
```

这段

```java
@MapperScan("com.jeeplus.**.mapper.**")
```

会告诉 Spring Boot **扫描这个包路径下所有的 Mapper 接口并自动注册为 Mapper Bean**。

因此，你的 `PopUpMapper` 接口即使没有加 `@Mapper` 注解，也会被自动扫描并注入到 Spring 容器中，且 MyBatis-Plus 会帮你生成代理实现。


# 自定义查询

Mybatis-plus本身已经提供了很多预制好的查询函数，但也可以自定义查询sql

## 使用注解

```java
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE age > #{age}")
    List<User> selectByAgeGreaterThan(@Param("age") Integer age);
}
```
`@Param("xxx")`给参数起别名，用于 SQL 绑定

注意返回类型不一定是entity，可以自定义返回类型（比如dto）

- **SQL 查询结果** 是一张表格，列名（字段名）对应数据库字段，如 `name`, `age`。
    
- MyBatis 会根据查询结果列名，**通过反射调用 DTO 类中对应的 setter 方法**，完成赋值。
    
- **字段名和 DTO 属性名对应**：
    
    - 默认情况下，MyBatis 会根据**列名（字段名）和 DTO 类中属性名（或对应 getter/setter）做匹配**（不区分大小写，且支持下划线转驼峰）。
        
    - 例如，数据库列 `name` 会自动映射到 `UserDTO` 中的 `name` 属性。
        
- 如果字段名和属性名不匹配，可以用注解或者 XML 显式指定映射。

注意：
- DTO 必须有**无参构造器**（`@Data` 默认会生成）
    
- DTO 中字段名需要与数据库列名保持对应，或者数据库列名符合驼峰转换规则
    
- 如果数据库列名与 DTO 字段名不一致，可以：
    
    - 使用 SQL 别名，如 `SELECT user_name AS username FROM user`
        
    - 使用 MyBatis 的 `@Results` 注解或 XML `<resultMap>` 做映射

```java
@Select("SELECT user_name, user_age FROM user WHERE status = #{status}")
@Results({
    @Result(column = "user_name", property = "name"),
    @Result(column = "user_age", property = "age")
})
List<UserDTO> selectNameAndAge(@Param("status") Integer status);

```
## 使用xml配置

接口：
```java
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> selectActiveUsers(@Param("status") Integer status);
}

```

配置xml:
```xml
<mapper namespace="com.example.mapper.UserMapper">
    <select id="selectActiveUsers" resultType="com.example.entity.User">
        SELECT * FROM user WHERE status = #{status}
    </select>
</mapper>
```

配置xml路径：
```yaml
mybatis-plus:
  mapper-locations: classpath:/mapper/**/*.xml
```

## 动态条件查询wrapper

动态构建where部分语句，用于查询

```java
QueryWrapper<User> query = new QueryWrapper<>();
query.eq("status", 1)
     .gt("age", 18)
     .like("name", "张");

List<User> list = userMapper.selectList(query);
```

lambda风格：
```java
LambdaQueryWrapper<User> query = Wrappers.lambdaQuery();
query.eq(User::getStatus, 1)
     .gt(User::getAge, 18)
     .like(User::getName, "张");

List<User> list = userMapper.selectList(query);
```


# 应用

## 分页查询

```java
Page<User> page = new Page<>(1, 10); // 当前页，每页条数
LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
wrapper.eq(User::getStatus, 1);

IPage<User> result = userMapper.selectPage(page, wrapper);
```

# Entity类

示例写法：

```java
@Data  
@TableName("t_pop_up")  
public class PopUp{ 
  
    /**  
     * 实体主键  
     */  
    @ExcelIgnore  
    @TableId    private String id;  
  
    /**  
     * 创建日期  
     */  
    @ExcelIgnore  
    @IgnoreSwaggerParameter    @ApiModelProperty(hidden = true)  
    @TableField(fill = FieldFill.INSERT)  
    private Date createDate;  
  
    /**  
     * 创建人  
     */  
    @ExcelIgnore  
    @IgnoreSwaggerParameter    @ApiModelProperty(hidden = true)  
    @TableField(fill = FieldFill.INSERT)  
    private String createBy;  
  
    /**  
     * 更新日期  
     */  
    @ExcelIgnore  
    @IgnoreSwaggerParameter    @ApiModelProperty(hidden = true)  
    @TableField(fill = FieldFill.INSERT_UPDATE)  
    private Date updateDate;  
  
    /**  
     * 更新人  
     */  
    @ExcelIgnore  
    @IgnoreSwaggerParameter    @ApiModelProperty(hidden = true)  
    @TableField(fill = FieldFill.INSERT_UPDATE)  
    private String updateBy;  
  
    /**  
     * 逻辑删除标记  
     */  
    @ExcelIgnore  
    @IgnoreSwaggerParameter    @ApiModelProperty(hidden = true)  
    @TableLogic  
    @TableField(fill = FieldFill.INSERT)  
    private Integer delFlag;  
  
  
    /**  
     * 默认构造函数  
     */  
  
    public BaseEntity() {  
  
    }  
  
    /**  
     * 构造函数  
     */  
    public BaseEntity(String id) {  
        this.id = id;  
    }  

    @TableField("img_url")  
    private String imgUrl; // 广告图  
  
    @TableField("title")  
    private String title; // 标题  
  
    @TableField("link_url")  
    private String linkUrl; // 广告链接  
  
    @TableField("content")  
    private String content; // 广告内容  
  
    @TableField("sorted")  
    private Integer sorted; // 排序号  
  
    @TableField("region")  
    private Integer region; // 弹窗位置：1：首页；2：发现；3：我的  
  
    @TableField("type")  
    private Integer type; // 弹窗类型：1：只弹窗一次；2：每天弹窗一次  
  
    @TableField("status")  
    private Integer status; // 状态：1：待发布；2：已发布；3：已下架  
  
    @TableField("setup_type")  
    private Integer setupType; // 发布方式：1：即时发布；2：定时发布；3：暂不发布  
  
    @TableField("seller_id")  
    private Long sellerId; // 商家id  
  
    @TableField("link_type")  
    private Integer linkType; // 链接方式：1：跳转；2：详情；3：无  
  
    @TableField("setup_time")  
    private Date setupTime; // 发布时间  
  
    @TableField("over_time")  
    private Date overTime; // 下架/失效时间  
  
    @TableField("forever")  
    private Boolean forever = false; // 是否永久有效  
  
    @TableField("marketing_id")  
    private String marketingId; // 活动唯一标识  
  
    @TableField("activity_type")  
    private Integer activityType; // 活动类型  
  
    @TableField("mobile_path")  
    private String mobilePath; // 移动端路径  
  
    @TableField("show_channal")  
    private String showChannal; // 展示渠道  
  
    @TableField("sort")  
    private Integer sort; // 岗位排序  
  
    @TableField("remarks")  
    private String remarks; // 备注信息  
}
```

注意以entity为中心，统一字段命名

# Service

 `@Transactional`

- Spring 的事务管理注解。
    
- 作用是该类中所有公共方法默认开启事务。
    
- 保证业务操作的一致性和完整性，出现异常时自动回滚。
    
- 可以在方法上覆盖配置，灵活控制事务传播行为。

也预制好了很多方法，简单crud直接用，也可以正常自定义方法，使用mapper操作数据

```java
@Service  
@Transactional  
public class PopUpService extends ServiceImpl<PopUpMapper,PopUp> {  
  
}
```

# Maven 配置

示例：
```xml
<!-- MyBatis-Plus Spring Boot Starter -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.3.1</version>  <!-- 请根据需要替换为最新版本 -->
</dependency>

<!-- MySQL 驱动（如果使用 MySQL） -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>  <!-- 根据实际版本替换 -->
</dependency>
```