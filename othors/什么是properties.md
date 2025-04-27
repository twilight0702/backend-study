## 什么是 `Properties`？

`Properties` 是 Java 中一个用来**读取配置文件**的类，它本质上是一个继承了 `Hashtable` 的**键值对集合**。

你可以把它想成：

> “一个专门用来存储 `.properties` 配置文件 的字典”。

---

## 用途

用来加载配置文件，比如 `db.properties`、`config.properties`、`log4j.properties` 这种。

这些配置文件一般长这样

```properties
username=root
password=123456
url=jdbc:mysql://localhost:3306/test
```

然后 Java 用 `Properties` 类来加载它，就能用 key 取值：

```java
Properties props = new Properties();
props.load(new FileInputStream("db.properties"));

String user = props.getProperty("username"); // root
```


---

## 应用场景有哪些？

|场景|示例|
|---|---|
|读取数据库配置|数据库连接地址、用户名密码|
|读取日志配置|`log4j.properties`|
|读取自定义设置|主题颜色、端口号、开关配置等|

---

## 注意事项

- 默认只能读取纯文本 `.properties` 文件（不能读取 yaml）
- key 和 value 都是字符串类型
- 不支持嵌套结构（不像 JSON、YAML 那么复杂）

---

## 示例代码（完整）

**`db.properties`**

```properties
username=root
password=123456
```

**Java读取：**

```java
Properties props = new Properties();
InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties");
props.load(is);

String username = props.getProperty("username");
String password = props.getProperty("password");

System.out.println("用户名：" + username);
System.out.println("密码：" + password);
```


