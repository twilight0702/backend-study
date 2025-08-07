

下面详细说明 MyBatis 和 MyBatis-Plus 的标准写法，包括如何定义基础 CRUD、如何添加自定义查询方法，以及 MyBatis-Plus 支持的常用扩展查询方法。

---

# 一、MyBatis 标准写法

## 1. Mapper 接口（Java）

```java
package com.example.mapper;

import com.example.entity.PopUp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PopUpMapper {

    PopUp selectById(@Param("id") String id);

    List<PopUp> selectAll();

    int insert(PopUp popUp);

    int updateById(PopUp popUp);

    int deleteById(@Param("id") String id);

    // 自定义查询示例
    List<PopUp> selectByStatus(@Param("status") Integer status);
}
```

## 2. Mapper XML 文件（resources/mapper/PopUpMapper.xml）

```xml
<mapper namespace="com.example.mapper.PopUpMapper">

    <select id="selectById" resultType="com.example.entity.PopUp">
        SELECT * FROM t_pop_up WHERE id = #{id}
    </select>

    <select id="selectAll" resultType="com.example.entity.PopUp">
        SELECT * FROM t_pop_up
    </select>

    <insert id="insert" parameterType="com.example.entity.PopUp">
        INSERT INTO t_pop_up (id, title, content, status)
        VALUES (#{id}, #{title}, #{content}, #{status})
    </insert>

    <update id="updateById" parameterType="com.example.entity.PopUp">
        UPDATE t_pop_up SET title = #{title}, content = #{content}, status = #{status}
        WHERE id = #{id}
    </update>

    <delete id="deleteById">
        DELETE FROM t_pop_up WHERE id = #{id}
    </delete>

    <!-- 自定义查询 -->
    <select id="selectByStatus" resultType="com.example.entity.PopUp">
        SELECT * FROM t_pop_up WHERE status = #{status}
    </select>

</mapper>
```

---

# 二、MyBatis-Plus 标准写法

## 1. Mapper 接口（Java）

```java
package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.PopUp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface PopUpMapper extends BaseMapper<PopUp> {

    // 自定义查询示例（注解方式）
    @Select("SELECT * FROM t_pop_up WHERE status = #{status}")
    List<PopUp> selectByStatus(Integer status);

    // 或者声明方法，配合 XML 使用（需要写 XML 文件）
    List<PopUp> selectByRegion(Integer region);
}
```

## 2. 自定义查询实现方式

### 方式一：注解方式（适合简单 SQL）

- 在接口方法上用 `@Select`、`@Update`、`@Insert`、`@Delete` 注解直接写 SQL。
    

```java
@Select("SELECT * FROM t_pop_up WHERE region = #{region}")
List<PopUp> selectByRegion(Integer region);
```

### 方式二：XML 映射方式（适合复杂 SQL）

- 新建 XML 文件（`PopUpMapper.xml`），声明自定义 SQL。
    

```xml
<mapper namespace="com.example.mapper.PopUpMapper">

    <select id="selectByRegion" resultType="com.example.entity.PopUp">
        SELECT * FROM t_pop_up WHERE region = #{region}
    </select>

</mapper>
```

- 在 Mapper 接口声明对应方法即可。
    

---

# 三、MyBatis-Plus 常用查询方法

|方法名|功能描述|备注|
|---|---|---|
|`selectById(Serializable)`|根据主键查询|基础 CRUD|
|`selectBatchIds(Collection)`|根据主键集合批量查询||
|`selectOne(Wrapper)`|根据条件查询单条|如果有多条数据会报错|
|`selectList(Wrapper)`|根据条件查询列表|无分页|
|`selectPage(Page, Wrapper)`|分页查询|需配合 `Page` 对象使用|
|`insert(T entity)`|插入数据||
|`updateById(T entity)`|根据主键更新||
|`update(T entity, Wrapper)`|根据条件更新||
|`deleteById(Serializable)`|根据主键删除||
|`delete(Wrapper)`|根据条件删除||

---

# 四、自定义查询的补充说明

- **Wrapper** 是 MyBatis-Plus 提供的查询条件构造器，可以方便拼接复杂条件，无需写 SQL。
    

```java
QueryWrapper<PopUp> query = new QueryWrapper<>();
query.eq("status", 2).like("title", "优惠");
List<PopUp> list = popUpMapper.selectList(query);
```

- **LambdaQueryWrapper** 支持类型安全的条件构造。
    

```java
LambdaQueryWrapper<PopUp> lambdaQuery = new LambdaQueryWrapper<>();
lambdaQuery.eq(PopUp::getStatus, 2);
List<PopUp> list = popUpMapper.selectList(lambdaQuery);
```

- 如果复杂 SQL 无法用 Wrapper 表达，建议使用注解或 XML 自定义查询。
    

---

# 五、总结

|功能点|MyBatis|MyBatis-Plus|
|---|---|---|
|基础 CRUD|接口 + XML|继承 BaseMapper 自动生成|
|简单自定义查询|XML 或注解|注解、XML 或 Wrapper|
|复杂查询|XML 灵活|XML 灵活 + Wrapper 部分支持|
|条件构造器|手写 SQL|提供 QueryWrapper、LambdaQueryWrapper|
|分页|需手写 SQL + 插件|内置分页支持，简单易用|

---

如果需要，我可以帮你写一个 MyBatis-Plus 的自定义查询完整示例，或者帮你写 Wrapper 复杂条件示例。需要告诉我。