# 什么是注解

**注解（Annotation**是 Java 提供的一种**元数据机制**，用来给代码添加额外的信息。它本质上是一些**标记（标注）**，不会直接影响程序运行逻辑，但可以被：

- **编译器**用来做检查或生成代码
- **工具或框架**用来做配置和控制逻辑
- **运行时**通过[[反射机制|反射机制]]读取注解，实现一些自动化功能

你可以把注解理解成代码上的“说明标签”，告诉编译器或框架：“这里的代码需要特别对待”。

# 注解的作用

Java 注解的主要作用可以总结为以下 **四大类功能**：

### 1. ✅ 编译检查（提升代码规范）

比如：

```java
@Override
public void toString() { }  // 如果方法签名错了，编译器会报错
```

- `@Override` 告诉编译器“我在重写父类的方法”，如果你写错方法名，编译器立刻报错。
- `@Deprecated` 可以标记不推荐使用的代码，提醒开发者。

---

### 2. ✅ 运行时读取（通过反射获得信息）

比如：

```java
@Author(name = "Alice")
public class MyClass { }
```

你可以在程序运行时通过反射获取 `@Author` 注解的信息，进行权限校验、记录作者等用途。

---

### 3. ✅ 替代 XML 配置（用于框架自动化）

在 Spring 框架中，很多注解替代了传统 XML 配置：

```java
@Component
public class UserService { }

@Autowired
private UserRepository repository;
```

框架可以通过注解自动扫描、自动注入、自动初始化对象，大大减少了手动配置工作。

---

### 4. ✅ 自动代码生成（APT、Lombok等）

一些工具（如 Lombok）利用注解实现代码自动生成，减少重复劳动：

```java
@Data  // 自动生成 getter/setter/toString/hashCode 等
public class User {
    private String name;
    private int age;
}
```

还有注解处理器（APT），可以在编译时生成类、方法等代码。

注解的应用场景总结：

| 场景      | 描述                                |
| ------- | --------------------------------- |
| 编译检查    | 如 `@Override`, `@Deprecated`      |
| 自动注入    | Spring 的 `@Autowired`             |
| 配置驱动开发  | 如 `@Component`, `@RequestMapping` |
| AOP切面标记 | `@Transactional`, `@Log`          |
| 测试框架标记  | `@Test`, `@BeforeEach`            |
| 自动代码生成  | Lombok、MapStruct等                 |

# 注解的基本语法

注解使用 `@` 符号开头，位于类、方法、字段等前面：

```java
@Override public String toString() {     return "Example"; }
```

# 常见注解

## Java 内置注解（最常用）

### 1. `@Override`

- 用于重写父类方法。
- 编译器检查是否正确重写。

### 2. `@Deprecated`

- 表示该方法或类已过时，调用时编译器会发出警告。

### 3. `@SuppressWarnings`

- 用于抑制编译器警告。
- 常见参数：`"unchecked"`, `"deprecation"`

```java
@SuppressWarnings("unchecked")
List list = new ArrayList();
```

### 4. `@SafeVarargs`

- 用于声明方法中的泛型参数是安全的（Java 7+）。

### 5. `@FunctionalInterface`

- 声明一个接口是函数式接口（只能有一个抽象方法）。


## 元注解（用于定义注解的注解）

Java 提供了 5 个元注解，用于描述自定义注解的行为：

|注解名|作用描述|
|---|---|
|`@Target`|指定注解作用的位置（类、方法等）|
|`@Retention`|指定注解保留级别（源码/类文件/运行时）|
|`@Documented`|是否将注解包含在 Javadoc 中|
|`@Inherited`|子类是否继承父类的注解|
|`@Repeatable`|是否支持重复注解（Java 8+）|

示例：

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String value();
}
```

## 自定义注解

### 定义：

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Author {
    String name();
    String date();
}
```

### 使用：

```java
@Author(name = "Alice", date = "2025-04-21")
public class MyClass {
}
```

---

## 常见第三方注解（框架中常见）

### Spring 框架

| 注解                | 说明                                |
| ----------------- | --------------------------------- |
| `@Component`      | 声明一个 Bean 组件                      |
| `@Autowired`      | 自动注入依赖                            |
| `@Service`        | 声明业务逻辑层组件                         |
| `@Repository`     | 声明 DAO 组件                         |
| `@Controller`     | 声明控制器层组件（Web）                     |
| `@RequestMapping` | 映射 URL 路径                         |
| `@RestController` | 等价于 `@Controller + @ResponseBody` |

### JUnit 测试

|注解|说明|
|---|---|
|`@Test`|表示一个测试方法|
|`@BeforeEach`|每个测试前执行|
|`@AfterEach`|每个测试后执行|
|`@BeforeAll`|所有测试前执行|
|`@AfterAll`|所有测试后执行|

---

# 注解的运行时解析（反射）

可以使用 Java 反射 API 获取注解信息：

```java
Class<?> clazz = MyClass.class;
if (clazz.isAnnotationPresent(Author.class)) {
    Author author = clazz.getAnnotation(Author.class);
    System.out.println(author.name());
}
```

[[Java运行时|运行时是什么]]
[[反射机制|反射是什么]]

---

# 注解处理器（APT，Annotation Processing Tool）

用于在编译期扫描、处理注解，生成代码（如 Lombok、ButterKnife、Dagger 等库的实现方式）。

使用方式：

- 实现 `javax.annotation.processing.AbstractProcessor`
- 使用 `@SupportedAnnotationTypes`、`@SupportedSourceVersion`
- 编译器会在编译时调用处理器自动处理标注的注解
    

---

