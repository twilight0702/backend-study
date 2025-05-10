## ✅ 一、Java 常见技术栈（后端开发为主）

### 🌱 基础层（语言和标准库）

- **Java SE（Standard Edition）**：Java 标准版，包含基础语法、集合、IO、多线程、网络编程等。
    
- 常用库：`java.util`、`java.io`、`java.net`、`java.lang` 等。
    

---

### 🧱 框架层（企业级开发）

#### 🔹 核心框架

- **Spring**：IOC/AOP核心框架，最基础组件。
    
- **Spring Boot**：快速构建项目。
    
- **Spring MVC**：处理 Web 请求。
    
- **Spring Cloud**：微服务架构下的服务注册、网关、配置中心等。
    

#### 🔹 持久层框架

- **MyBatis**：轻量 ORM 框架，SQL 灵活。
    
- **Hibernate / JPA**：全自动 ORM 框架，适合数据结构稳定的场景。
    
- **Spring Data JPA**：对 JPA 的封装，简化操作。
    

#### 🔹 数据库

- MySQL、PostgreSQL、Oracle、MongoDB、Redis（缓存）
    

#### 🔹 Web 开发

- Servlet / JSP（传统）
    
- Thymeleaf（模板引擎）
    
- JSON、Rest API、WebSocket、JWT 等
    

#### 🔹 构建与部署

- Maven、Gradle：构建工具
    
- Tomcat、Jetty：Web 容器
    
- Jenkins、Docker、Kubernetes：持续集成与部署
    

---

### 🧪 测试相关

- JUnit、Mockito、Spring Test
    

---

### 📱 前端常用配合

- Vue、React（前后端分离开发中常见）
    
- Axios（请求库）
    

---

## ✅ 二、Java SE 和 Java EE 的区别

| 对比项  | Java SE（Standard Edition） | Java EE（Enterprise Edition）    |
| ---- | ------------------------- | ------------------------------ |
| 定义   | Java 标准版，面向桌面/基础开发        | Java 企业版，面向大型企业级应用（Web、分布式）    |
| 包含内容 | 基本语法、集合、多线程、IO、JVM、类库等    | 在 SE 的基础上加上：Web 开发、事务、消息等      |
| 应用范围 | 桌面应用、小工具、基础算法、学习使用        | 网站、企业级系统、电商平台、OA、CRM等          |
| 框架支持 | 不强依赖框架                    | 依赖大量框架（Spring、Hibernate、JPA 等） |
| 学习顺序 | 基础必学（先学 SE）               | 进阶学习（熟练 SE 后学 EE）              |

> **简单说：Java SE 是基础，Java EE 是扩展。EE 更适合做实际项目。**

---

如果你是做 Java Web 或企业应用开发，那你用到的大多属于 Java EE 技术栈（虽然现在官方把 Java EE 改名为 Jakarta EE 了）。

你目前在开发中使用的是 Spring Boot 吗，还是基于 SSM 手动搭建的后端项目？