# 1. 用于不知道具体类名

用于在运行的时候不知道具体类名的情况（比如说类名存在一个配置文件中）
使用反射进行读取

比如你的项目支持插件，用户可以在配置文件中写：

```properties
service.class = com.example.MyServiceImpl
```

你在代码中：

```java
String className = config.get("service.class");
Class<?> clazz = Class.forName(className);
Object obj = clazz.getDeclaredConstructor().newInstance();
```

> 🔍 **此时你在写代码时并不知道 `MyServiceImpl` 是什么类！**

---

# 2. 构造方法参数不确定或需要多个重载时，反射更灵活

你可能需要选择性调用不同构造器：

```java
Constructor<?> constructor = clazz.getConstructor(String.class, int.class);
Object obj = constructor.newInstance("Alice", 25);
```

如果你用 `new`，只能写死一个构造函数的调用方式：

```java
new Person("Alice", 25); // 固定了参数个数和类型
```

---

# 3. 反射能创建**私有构造方法**的对象（通过 setAccessible）

比如单例类：

```java
class Singleton {
    private Singleton() {}
}
```

用 `new` 是不行的，会报错，但反射可以：

```java
Constructor<Singleton> cons = Singleton.class.getDeclaredConstructor();
cons.setAccessible(true); // 暴力破解私有构造
Singleton instance = cons.newInstance();
```

| 方法名                           | 是否只获取 public 构造方法 | 是否能获取 private 构造方法 | 是否能获取 protected/default 构造方法 |
| ----------------------------- | ----------------- | ------------------ | ---------------------------- |
| `getConstructor(...)`         | ✅ 是               | ❌ 否                | ❌ 否                          |
| `getDeclaredConstructor(...)` | ❌ 否（可获取所有）        | ✅ 是                | ✅ 是                          |

---

## 🧪 二、举例对比

---

# 4.**解耦**框架和具体实现类（IoC、DI、Spring）

在 **Spring 框架** 中，你写的是：

```java
@Autowired
MyService myService;
```

Spring 内部通过反射调用：

```java
Class<?> clazz = Class.forName("com.example.MyServiceImpl");
Object bean = clazz.getConstructor().newInstance();
```

> ✅ 开发者只依赖接口，框架用反射去生成真正的实现类，从而实现了 **控制反转 IoC**。

---

# 5. 你写的是框架/工具代码

比如你自己做一个 ORM 框架，读取数据库的一行数据：

```json
{ "name": "Tom", "age": 18 }
```

你不可能为每个实体类写死：

```java
new User("Tom", 18);
new Student("Tom", 18);
```

你需要通用代码：

```java
Class<?> entityClass = ...; // 动态决定
Object obj = entityClass.getDeclaredConstructor().newInstance();
```

