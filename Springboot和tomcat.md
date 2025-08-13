# 1 springboot项目运行流程

## 1.1 启动入口执行

- Spring Boot 项目的入口方法通常是：

```java
@SpringBootApplication
public class MyApp {
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }
}
```

- 你点击运行按钮（IDEA、Eclipse）后，本质是执行 `main()` 方法。

---

## 1.2 Spring Boot 启动流程

`SpringApplication.run()` 会完成一系列动作：

1. **创建 SpringApplication 对象**
    - 解析启动类上的注解（`@SpringBootApplication`）
    - 确定是 **Web 应用**（Servlet / Reactive）还是普通应用。
        
2. **准备环境（Environment）**
    - 读取配置文件（`application.properties` / `application.yml`）
    - 解析启动参数
    - 设置系统环境变量和应用环境变量。
        
3. **创建 Spring 容器（ApplicationContext）**
    - 对 Web 项目来说，是 `AnnotationConfigServletWebServerApplicationContext`
    - 注册各种 Bean 定义。
        
4. **执行自动配置（AutoConfiguration）**
    - 根据类路径中的依赖，自动配置对应的组件，比如：
        - Spring MVC
        - 数据源（DataSource）
        - JPA / MyBatis
        - 内置 Web 服务器（Tomcat / Jetty / Undertow）

---

## 1.3 内置 Tomcat 启动

- Spring Boot 检测到是 Web 应用 → 创建并启动 **内置 Tomcat**（默认）。
- 主要步骤：
    1. 创建 Tomcat 对象
    2. 设置端口（默认 8080，来自配置文件）
    3. 注册 Servlet 容器（Spring MVC 的 `DispatcherServlet`）
    4. 初始化 **Acceptor / Poller / Worker 线程池**
    5. 绑定端口 → 开始监听 HTTP 请求

---

## 1.4 Spring 容器完成 Bean 初始化

- 根据配置和依赖，创建并注入所有 Bean（`@Component`、`@Service`、`@Repository`、`@Controller` 等）。
- 初始化拦截器、过滤器、中间件。
- 启动时会调用 `@PostConstruct`、`InitializingBean` 等生命周期方法。

---

## 1.5 应用进入就绪状态

- 控制台打印：

```
Started MyApp in X.XXX seconds
```

- 此时 Tomcat 已经在指定端口监听，线程池待命。
- 你的 API 接口、静态资源等都可以被访问。

---

## 1.6 接收请求并处理

- 请求到来时：
    - **Acceptor 线程** 接收连接
    - **Poller 线程** 监听 socket 事件
    - 从 **Worker 线程池** 取一个空闲线程处理
    - Tomcat 调用 `DispatcherServlet` → 控制器方法
    - 返回结果（HTML / JSON / 文件等）

---

## 1.7 总结：启动过程的时间线

```
main() 方法执行
   ↓
Spring Boot 创建 SpringApplication
   ↓
准备环境 & 读取配置
   ↓
创建 Spring 容器
   ↓
自动配置 & 加载 Bean
   ↓
启动内置 Tomcat（绑定端口，线程池就绪）
   ↓
容器完成初始化
   ↓
应用就绪（控制台打印 Started）
   ↓
等待请求 & 按线程池模型处理
```


# 2 SprngBoot中集成tomcat

## 2.1 集成的方式

- Spring Boot 通过依赖 **`spring-boot-starter-web`** 引入 Tomcat：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

- 这个 Starter 会自动依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
</dependency>
```

- `spring-boot-starter-tomcat` 里包含了 **Tomcat 核心类库**（如 `org.apache.catalina.*`），直接在你的应用进程中运行。

---

## 2.2 内嵌（Embedded）Tomcat

- Spring Boot **不是**在外部启动 Tomcat 再部署 WAR，而是**把 Tomcat 当作一个 Java 库**嵌入到应用进程里。
- 当你运行 `SpringApplication.run()` 时，Spring Boot 会：
    1. 创建一个 `TomcatServletWebServerFactory`
    2. 调用它的 `getWebServer()` 创建 `Tomcat` 对象
    3. 配置端口、上下文、Servlet、过滤器
    4. 调用 `tomcat.start()` 直接启动
- 这样，Tomcat 就和你的业务代码跑在同一个 JVM 进程里。

---
## 2.3 为什么要集成

- **方便部署**：只需要一个 JAR 包（`java -jar app.jar`），不用提前安装 Tomcat。
- **统一管理**：Spring Boot 直接控制 Tomcat 的生命周期。
- **可替换**：可以改用 Jetty 或 Undertow，只需换依赖。
---

## 2.4 外部 Tomcat vs 内嵌 Tomcat

| 特点   | 外部 Tomcat                         | 内嵌 Tomcat（Spring Boot 默认） |
| ---- | --------------------------------- | ------------------------- |
| 部署方式 | 打包成 WAR 部署到 Tomcat 的 `webapps` 目录 | 打包成 JAR 直接运行              |
| 启动   | 启动 Tomcat → 加载应用                  | 运行应用 → 启动 Tomcat          |
| 管理   | Tomcat 独立管理                       | Spring Boot 管理            |
| 灵活性  | 可以部署多个应用                          | 单应用为主，独立运行                |

# 3 我的代码和tomcat的关系？

## 3.1 你写的代码和 Tomcat 的“接触点”

你在 Spring Boot 里写的 `@RestController`、`@Controller`、`@RequestMapping` 这些，其实最终会注册成 **Servlet 规范里的组件**。

在 Servlet 规范中：

- **Servlet**：处理 HTTP 请求的类（必须实现 `javax.servlet.Servlet` 接口）
- **Filter**：请求/响应的拦截器
- **Listener**：监听 Web 应用生命周期事件

Spring MVC 中的 `DispatcherServlet` 就是一个标准 Servlet，负责接收 Tomcat 转发的所有请求，然后调用你写的 Controller 方法。

---

## 3.2 Spring Boot 帮你做的“注册”

传统做法（外部 Tomcat + web.xml）：

```xml
<servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
</servlet>
<servlet-mapping>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

这会告诉 Tomcat：所有路径 `/` 的请求，都交给 DispatcherServlet。

Spring Boot 内嵌 Tomcat 的做法：

- 启动时创建 Tomcat 对象
- 代码里**直接调用 API**：

```java
tomcat.addServlet(contextPath, "dispatcherServlet", new DispatcherServlet(applicationContext));
context.addServletMappingDecoded("/", "dispatcherServlet");
```

- 这样就等于帮你在内嵌 Tomcat 中注册好了 DispatcherServlet。

---

## 3.3 **执行链路（请求是怎么到你代码的）**

当你运行 Spring Boot：

1. Spring Boot 启动内嵌 Tomcat → Tomcat 在 JVM 里监听 8080 端口
2. 你写的 Controller 方法（比如 `/hello`）被 Spring MVC 注册到 HandlerMapping
3. 当请求到来：
    ```
    浏览器 --> Tomcat（内嵌） --> DispatcherServlet --> Spring MVC 找到对应 Controller --> 执行方法
    ```
4. 方法返回的对象/字符串，由 Spring MVC 处理成 HTTP 响应，Tomcat 再发给浏览器。

---

## 3.4 **为什么看起来像“部署”**

在外部 Tomcat 部署时，你是把 `war` 放到 `webapps`，Tomcat 启动时加载它。  
在 Spring Boot 内嵌模式下，其实是：

- 你的代码（Controller、Service 等）和 Tomcat 的类文件打包在**同一个 JAR**里。
- 启动时，这个 JAR 里的 Tomcat 会**主动加载你写的代码**到 Servlet 容器中。
- 所以逻辑上等价于“部署到 Tomcat”，只是部署动作是在你应用启动时自动完成的。

---

✅ **一句话总结**  
你的 Spring Boot 项目里有一个内嵌的 Tomcat，它在启动时会把 Spring MVC 的 DispatcherServlet 注册进去，DispatcherServlet 再把你的 Controller 注册到路由表中。Tomcat 收到请求后会交给 DispatcherServlet，最终调用到你的业务代码。

# 4 SpringBoot如何知道是否是web项目？

> SpringBoot除了写常规的web项目，还有比如 Reactive Web 项目 等

> Spring Boot 通过**自动配置（Auto-Configuration）机制**来判断你的项目类型（比如 Web 项目、非 Web 项目、Reactive Web 项目等）。具体来说，Spring Boot 主要依据**类路径中的依赖和配置**来推断应用类型。
## 4.1 依据类路径判断

- **Web 项目**：  
    如果类路径中存在 **`spring-webmvc`**（即 Spring MVC 相关依赖），Spring Boot 会认为这是一个 Servlet Web 应用。  
    例如，引入 `spring-boot-starter-web` 依赖时，里面包含了 `spring-webmvc`。
- **Reactive Web 项目**：  
    如果类路径中包含 Reactor Netty 或 WebFlux 相关依赖（如 `spring-boot-starter-webflux`），Spring Boot 会认为这是响应式 Web 应用。
- **非 Web 应用**：  
    如果既没有以上依赖，也没有其他 Web 框架相关依赖，Spring Boot 会推断这是一个非 Web 应用，比如命令行工具、批处理程序等。
    
---

## 4.2 Auto-Configuration 条件判断

Spring Boot 的自动配置类通常带有条件注解，如：
- `@ConditionalOnClass`：判断某个类是否在类路径中
- `@ConditionalOnMissingBean`：判断某个 Bean 是否存在
- `@ConditionalOnWebApplication`：判断当前是否为 Web 应用
- `@ConditionalOnNotWebApplication`：判断非 Web 应用

Spring Boot 根据这些条件决定加载哪些自动配置类，从而启动对应类型的应用上下文。

---

## 4.3 启动时上下文类型的区分

- **`AnnotationConfigServletWebServerApplicationContext`**  
    用于 Servlet Web 应用，自动配置内嵌 Tomcat/Jetty，加载 DispatcherServlet 等。
- **`AnnotationConfigReactiveWebServerApplicationContext`**  
    用于响应式 Web 应用。
- **`AnnotationConfigApplicationContext`**  
    用于非 Web 应用。

SpringApplication 在启动时会根据自动配置判断，选择合适的 ApplicationContext 类型。

---

# 5 传统web和响应式异步对比

## 5.1 表格对比

Spring MVC 和 Spring WebFlux 的区别，主要体现在编程模型、线程模型、服务器支持和适用场景等方面。以下是详细对比说明：

| 方面        | Spring MVC（传统同步模型）       | Spring WebFlux（响应式异步模型）               |
| --------- | ------------------------ | ------------------------------------- |
| **编程模型**  | 基于阻塞的同步调用                | 基于异步非阻塞的响应式流（Reactive Streams）        |
| **请求处理**  | 每个请求占用一个线程，直到处理完成        | 事件驱动，线程可复用，不阻塞请求线程                    |
| **核心类型**  | 返回普通对象或 `Callable` 等     | 返回 `Mono`（0或1个元素）或 `Flux`（0到多个元素）     |
| **服务器支持** | 内嵌 Tomcat、Jetty、Undertow | 内嵌 Netty（推荐）、Jetty，支持 Servlet 3.1+ 异步 |
| **性能特点**  | 简单易用，适合低到中等并发            | 适合高并发、大量长连接，资源利用更高                    |
| **开发复杂度** | 逻辑清晰，易理解                 | 学习曲线稍陡，需理解响应式编程范式                     |
| **适用场景**  | 传统 Web 应用、企业级应用          | 高并发 I/O 密集型应用，如实时通信、流处理等              |
| **生态兼容性** | 与 Spring 生态兼容，支持多种传统组件   | 逐渐完善，支持响应式数据库驱动、消息驱动等                 |

## 5.2 具体区别总结：

- **线程模型不同**：  
    Spring MVC 是阻塞模型，处理请求时线程会被占用直到完成；  
    WebFlux 是非阻塞模型，线程不会被阻塞，适合处理大量并发。
    
- **返回类型不同**：  
    MVC 返回普通对象（同步），WebFlux 返回 `Mono` 或 `Flux`，支持响应式流。
    
- **服务器实现不同**：  
    MVC 依赖 Servlet 容器（Tomcat 等），WebFlux 推荐基于 Netty 的反应式服务器。
    
- **适用场景差异**：  
    MVC 适合大部分传统业务场景；WebFlux 更适合对响应时间和吞吐量要求极高的场景。
