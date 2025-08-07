---
视频地址: https://www.bilibili.com/video/BV1UN411x7xe?spm_id_from=333.788.videopod.episodes&vd_source=a503248b608b8da9614b6dd7eb24901d&p=55
---
# 为什么需要Tomcat

| 类型                                     | 运行方式                             | 是否需要 Tomcat             |
| -------------------------------------- | -------------------------------- | ----------------------- |
| **普通 Java 应用（如控制台工具）**                 | `java -jar xxx.jar` 或运行 `.class` | ❌ 不需要                   |
| **Web 应用（如 Java Web / Spring MVC 网站）** | 需要一个能处理 HTTP 请求的容器               | ✅ 需要（如 Tomcat）          |
| **Spring Boot 应用**                     | 内置了 Tomcat                       | ❌ 不需要外部 Tomcat，直接运行 jar |

---
你的情况：**你开发的是 Web 应用（如 JSP、Servlet、Spring MVC）**

这类应用不是普通的桌面 app，而是服务器处理 HTTP 请求返回网页的“网站后端”程序，需要一个 **Web 容器** 来运行。

Web 容器的作用（如 Tomcat）：

- 接收 HTTP 请求；
    
- 调用你的 Servlet 或 Controller；
    
- 返回 HTML/JSON 响应。
    `
Tomcat也是用Java写的，运行也需要JRE
是一个JavaWeb服务器软件，专门运行Web应用软件
应用最广

要把app装到Tomcat中，再放到JRE中才能运行

java本身也可以直接进行一些http请求的收发，但是：
**Java 程序默认不会监听网络端口，也不懂 HTTP 协议**。  
你写的是普通程序，不是网络服务程序。浏览器访问需要你：

- **打开一个网络端口**（监听）
    
- **能理解浏览器发过来的 HTTP 协议**
    
- **返回 HTTP 格式的响应**
    

而你写的 Java 程序没有这些能力。

---

## 🎯 那 Tomcat 是什么？

> Tomcat 是一个“懂 HTTP 协议”的 Java 程序。

它做了三件事：

| 能力               | 作用                               |
| ---------------- | -------------------------------- |
| 1️⃣ 监听网络端口       | 监听浏览器访问的 8080 端口                 |
| 2️⃣ 解析 HTTP 请求   | 能看懂浏览器发来的 HTTP 报文                |
| 3️⃣ 执行你写的代码并返回结果 | 把请求交给你写的 Servlet 或 Controller 方法 |

你写的 Web 程序，其实是交给 Tomcat 去“托管”的。

---

## 🎯 Java 本身也能做 Web 吗？

能，但要你自己做“服务员”：

1. 写 socket 监听端口；
    
2. 用 Java 读字节流，手动解析 HTTP 报文（请求头、请求体）；
    
3. 手动拼 HTTP 响应报文；
    
4. 做多线程处理，防止用户同时访问时崩掉。
    

这些事很麻烦，**Tomcat 都帮你做好了**。

---

## ✅ 用图来简单表示：

```txt
        ──HTTP请求──►       ──────►
浏览器                Tomcat         你写的 Controller/Servlet           
		◄─HTTP响应──       ◄──结果──
		
```


你只需要关心“你写的处理逻辑”，Tomcat 会自动：

- 把请求打包成 `HttpServletRequest`
    
- 把响应封装为 `HttpServletResponse`
    
- 把请求路由给正确的方法（如 `/login` → loginController）

现代Spring Boot 已经集成了 Tomcat，日常开发不需要再手动配置 Tomcat。但如果你想搞懂 Web 请求从浏览器到 Java 是怎么一步步处理的，可以自己玩一玩原始 Servlet + Tomcat

# 什么是Tomcat

更新迅速，开源免费

注意Tomcat的8和9可以互换，但是Tomcat10包名后缀更换（javaEE换Eclipse维护），因此不互通，但是一般不会出现开发到一半更换Tomcat的情况

不同版本需要的JRE的版本不同

# 下载和启动

直接官网下载解压zip
然后双击bin\startup.bat启动
可以关闭黑框关闭Tomcat，或者是使用shutup.bat关闭
后续是让IDE关联自己开关

中文乱码，要调配置，调打印日志的字符集（如下）"D:\Program4java\apache-tomcat-10.1.40\conf\logging.properties"
```properties
java.util.logging.ConsoleHandler.level = ALL

java.util.logging.ConsoleHandler.formatter = org.apache.juli.OneLineFormatter

java.util.logging.ConsoleHandler.encoding = GBK
```

[[bin目录|关于bin目录]]

配置环境变量（和java一样的）CATALINA_HOME
就可以cmd里面用startup.bat启动Tomcat
但是不配置也可以（后续都用IDE），而且如果配置了环境变量，电脑里面有多个Tomcat，即使点击另一个的startup.bat，运行的也是环境变量中的那个
在linux上可能会需要配置