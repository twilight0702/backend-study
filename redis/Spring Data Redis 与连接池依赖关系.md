
### 1. Spring Data Redis 的默认客户端

- **Spring Boot 3.x** 默认使用 **Lettuce** 作为 Redis 客户端（`spring-boot-starter-data-redis` 会自动引入 `lettuce-core`）。
    
- 如果想用 **Jedis**，需要手动引入依赖，并在配置中切换 `spring.redis.client-type=jedis`。
    

---

### 2. 连接池的作用

- 复用已建立的 Redis 连接，减少频繁创建/释放的开销。
    
- 控制最大连接数，防止连接数暴涨或资源不足。
    
- 对于 **Jedis（阻塞 I/O）**，高并发场景下必须依赖连接池；  
    对于 **Lettuce（异步多路复用）**，连接池是可选的。
    

---

### 3. commons-pool2 的角色

- **不是 Redis 客户端**，而是连接池的实现库（`GenericObjectPool` 等）。
    
- Lettuce、Jedis 在开启连接池时都会依赖 commons-pool2。
    
- Spring Boot **不会默认引入 commons-pool2**，因为连接池是可选功能；如果开启连接池但没引入，会 `ClassNotFoundException`。
    

---

### 4. Jedis 与 Lettuce 对比

|特性|Jedis|Lettuce|
|---|---|---|
|I/O 模型|阻塞 I/O（BIO）|异步 NIO（Netty）|
|单连接多线程|不支持|支持|
|连接池|必须（默认启用）|可选|
|commons-pool2 依赖|必须|仅开启连接池时需要|

---

### 5. Spring Boot 配置示例

- **Jedis**（默认连接池模式）：
    
    ```yaml
    spring:
      redis:
        client-type: jedis
        jedis:
          pool:
            max-active: 8
    ```
    
- **Lettuce**（可选连接池）：
    
    ```yaml
    spring:
      redis:
        client-type: lettuce
        lettuce:
          pool:
            max-active: 8
    ```
    

[[4. redis Java客户端#Jedis和Lettuce的区别|关于jedis和lettus的对比（连接池方面)]]
