## 1. 背景

在 Spring Boot 项目中，引入 `spring-boot-starter-data-redis` 后，会自动根据配置文件生成默认的 `RedisTemplate` Bean。  
在使用时，`@Autowired` 注入的 `RedisTemplate` 如果不指定泛型，通常可以直接使用；但一旦指定了泛型，可能会注入失败。

---

## 2. 自动配置的默认 RedisTemplate

Spring Boot 会在 `RedisAutoConfiguration` 中自动注册两个 Bean：

```java
@Bean
@ConditionalOnMissingBean(name = "redisTemplate")
public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
}

@Bean
@ConditionalOnMissingBean
public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    return new StringRedisTemplate(redisConnectionFactory);
}
````

- 默认 Bean 类型是 **`RedisTemplate<Object, Object>`**
    
- 会根据 `application.yml` / `.properties` 中的连接信息（host、port、password 等）自动配置
    

---

## 3. 无泛型参数时的注入

```java
@Autowired
private RedisTemplate redisTemplate; // 或 RedisTemplate<Object, Object>
```

- Spring 按类型匹配 Bean
    
- 默认生成的 `RedisTemplate<Object, Object>` 类型能直接匹配
    
- 因此能直接注入，并使用到配置文件中的 Redis 连接信息
    

---

## 4. 指定泛型参数时的问题

```java
@Autowired
private RedisTemplate<String, Object> redisTemplate;
```

- Spring 在查找 Bean 时，会同时考虑泛型信息（`ResolvableType`）
    
- 自动配置生成的 Bean 类型是 `RedisTemplate<Object, Object>`  
    与 `RedisTemplate<String, Object>` 不完全一致
    
- Spring 认为没有匹配的 Bean，导致无法注入
    

---

## 5. 解决方法

如果需要特定泛型（例如 `RedisTemplate<String, Object>`），需要手动定义 Bean：

```java
@Bean
public RedisTemplate<String, Object> stringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return template;
}
```

这样 Spring 在注入时会匹配到自定义的 Bean。

---

## 6. 总结

- **无泛型** → 使用默认自动配置的 `RedisTemplate<Object, Object>` Bean
    
- **有泛型** → 若泛型与默认 Bean 不一致，需要手动定义 Bean
    
- Spring 的自动配置**不会生成泛型特化版本**的 RedisTemplate