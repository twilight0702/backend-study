# 接收参数

在 Spring Boot（Spring MVC）中，Controller 方法接收前端请求参数的方式多种多样，主要取决于：

- HTTP 方法（GET / POST）
    
- 请求参数的格式（URL query / JSON / 表单 / 路径参数）
    
- 你希望绑定的数据结构（普通字段 / 对象 / List / Map 等）
    

---

## 一、常见接收参数方式总结

| 类型         | 注解/写法                     | 使用场景                     | 示例                 |
| ---------- | ------------------------- | ------------------------ | ------------------ |
| 路径参数       | `@PathVariable`           | URL 路径中的变量               | `/user/{id}`       |
| 查询参数       | `@RequestParam`           | URL 查询参数（GET / POST 表单）  | `/search?name=xxx` |
| 表单/JSON 对象 | `@RequestBody`            | 请求体是 JSON                | `application/json` |
| 表单对象绑定     | 无注解/`@ModelAttribute`（默认） | 表单提交或 GET 查询封装为 JavaBean |                    |
| 多参数对象分页等   | 自定义类如 `Page`、普通对象         | 查询列表、分页请求等               |                    |

---

## 二、每种写法详细解释与示例

### 1. `@PathVariable` 路径参数绑定

```java
@GetMapping("/user/{id}")
public ResponseEntity getUserById(@PathVariable String id) {
    return ResponseEntity.ok("用户ID：" + id);
}
```

请求：

```
GET /user/123
```

说明：

- 参数来自 URL 路径
    
- 常用于 REST 风格接口：增删改查等资源定位
    
前端：
```js
const userId = 123;
axios.get(`/user/${userId}`)
  .then(response => {
    console.log(response.data);
  })
  .catch(error => {
    console.error('请求失败:', error);
  });

```

---

### 2. `@RequestParam` 查询参数或表单参数绑定

```java
@GetMapping("/search")
public ResponseEntity search(@RequestParam String keyword,
                              @RequestParam(required = false, defaultValue = "1") int page) {
    return ResponseEntity.ok("关键词：" + keyword + ", 页码：" + page);
}
```

请求：

```
GET /search?keyword=abc&page=2
```

说明：

- 适用于 URL 查询参数或 POST 表单 `x-www-form-urlencoded`
    
- `required=false` 可避免参数缺失时报错
	
- 通常只用于单一参数，如果是接收一个自定义类的对象，使用@ModelAttribute（即不添加注解）
    
对应前端（使用Anxios）：

```js
axios.get('/api/users', {
  params: { id: 123 }
})
.then(response => {
  console.log(response.data);
})
.catch(error => {
  console.error('请求失败：', error);
});
```

---

### 3. `@RequestBody` 请求体接收 JSON 数据

```java
@PostMapping("/user")
public ResponseEntity create(@RequestBody UserDTO user) {
    return ResponseEntity.ok("收到用户：" + user.getName());
}
```

请求体（JSON）：

```json
{
  "name": "Alice",
  "age": 20
}
```

说明：

- 用于 POST/PUT 等接收 JSON 格式请求体
    
- 参数必须有 `@RequestBody`
    
- 前端 Content-Type 需为 `application/json`
	
- 根据名字对应填充，有差异不会报错
    
对应前端（Axios）
```js
axios.post('/user', {
  name: 'Alice',
  age: 20
})
.then(response => {
  console.log(response.data);
})
.catch(error => {
  console.error('请求失败:', error);
});

```

---

### 4. JavaBean 对象接收（默认 `@ModelAttribute`）

```java
@GetMapping("/post/list")
public ResponseEntity list(Post post, Page<Post> page) {
    // 自动将 ?title=xxx&status=1&current=2&size=10 绑定到对象
    return ResponseEntity.ok(post.getTitle());
}
```

请求：

```
GET /post/list?title=xxx&status=1&current=2&size=10
```

说明：

- 支持多个字段绑定到对象中
    
- Spring 会将请求参数与字段名自动匹配
    
- 无需显式加 `@ModelAttribute`（加了也行）
	
- 前后端参数按照名字对应
	
- 前后端设置参数数量不一致可以，只会根据名字去对应填充

**问题：重名字段**
多个参数名相同但是目标不同
```java
@GetMapping("/test")
public String test(User user, Post post) { ... }
```
`User` 和 `Post` 都有 `title` 字段

-> 请求url使用请求参数前缀
```java
?user.title=xxx&post.title=yyy
```



---

### 5. 接收 Map

```java
@GetMapping("/config")
public ResponseEntity getConfig(@RequestParam Map<String, String> params) {
    return ResponseEntity.ok(params);
}
```

请求：

```
GET /config?env=dev&version=1.0
```

说明：

- 所有请求参数封装成一个 Map
    
- 不适合复杂结构，但方便做通用处理
    

---

### 6. 多种方式混用

```java
@PostMapping("/user/{id}/update")
public ResponseEntity update(
    @PathVariable String id,
    @RequestParam String type,
    @RequestBody UserDTO user
) {
    // 综合使用路径参数、查询参数、请求体
    return ResponseEntity.ok(user);
}
```

---

## 三、区别总结

|注解|来源|类型|支持请求方式|特点|
|---|---|---|---|---|
|`@PathVariable`|URL 路径|字段|GET/POST/PUT 等|RESTful 风格路径变量|
|`@RequestParam`|URL 查询 / 表单字段|字段|GET/POST|常用于普通参数|
|`@RequestBody`|请求体|对象|POST/PUT 等|接收 JSON，必须指定 Content-Type|
|`@ModelAttribute`（默认）|URL 查询 / 表单|JavaBean|GET/POST|自动绑定同名字段|
|无注解 JavaBean|同上|JavaBean|GET/POST|简化写法|

---

## 实战建议

- 查询条件多时 → 用 JavaBean + `@ModelAttribute`
    
- 提交表单 JSON → 用 `@RequestBody`
    
- REST 风格路由 → 用 `@PathVariable`
    
- 混合参数建议拆开写清楚
    

## 最佳实践

- **字段统一协商好**，尽量避免不一致（使用接口文档、TS 类型、Swagger 等）
    
- 对可选字段使用 `@RequestParam(required = false)` 或 `defaultValue`
    
- 对 DTO 添加参数校验（`@NotNull`, `@NotBlank`）明确必填项
    
- 对 `@RequestBody` 的 JSON 请求，使用 `@JsonProperty` 显式映射字段，提升健壮性
    
- 避免 primitive 类型（如 `int`），改用包装类型（如 `Integer`）便于区分“未传”与“0”

---
---
# 返回参数

## 概述

### 如何做

一般建议使用ResponseEntity+自定义返回Result类

无论后端处理成功与否，HTTP 状态码始终返回 `200 OK`，实际的业务处理结果（成功/失败/原因）通过响应体中的 `code/message/data` 三元结构表示。

### 为什么

1. **让前端处理逻辑更统一、简化**

如果你用 HTTP 状态码来区分所有情况（如 400/403/404/500）：

- 前端必须写 `.then` 处理成功 + `.catch` 处理失败；
    
- `.catch` 中还可能没结构体（是字符串），不好统一处理；
    
- 有些浏览器或代理对非 2xx 状态码会直接拦截（如 401/403）。
    

但如果统一使用 `200`，前端只需：

```js
axios.get(...).then(res => {
  if (res.data.code === 200) {
    // 处理成功
  } else {
    // 弹出错误提示
  }
});
```

2. **区分“请求成功”与“业务成功”**

- HTTP 200 表示“请求这个 URL 成功了”（技术层面的成功）；
    
- body 中的 `code` 表示“业务是否成功”（逻辑层面的成功）；
    

也就是说：

|类型|成功|失败|
|---|---|---|
|HTTP 层|200|200（固定）|
|业务逻辑层|code = 200|code != 200（如 400、401、500 等自定义码）|

如果不这样做，会遇到的问题

- `status != 200` 时，Axios 会自动进入 `.catch` 分支；
    
- `.catch` 拿不到标准结构（可能是纯字符串）；
    
- `.catch` 中不适合处理业务错误，应该只处理网络/系统级错误（如断网、跨域等）；
    
- 前后端必须写两套判断逻辑，维护复杂度升高。

### 实际业界做法（包括阿里、字节、腾讯）：

统一使用 HTTP 200 + body.code 来表示业务状态，规范示例如：

|code|含义|
|---|---|
|200|操作成功|
|400|参数错误|
|401|未登录或登录过期|
|403|无权限|
|500|系统异常|
|1001|自定义业务错误码|

但有一些例外（合理使用非 200 状态码）

建议以下情况使用 HTTP 状态码：

|状态码|场景|
|---|---|
|401|未登录，前端可跳登录页|
|403|没有权限，前端可跳无权限页|
|404|接口未找到，提示资源不存在|
|415|文件格式错误|
|429|请求频繁，需限流处理|

这些情况属于技术层级错误，**前端可以根据状态码做路由跳转或中断处理**。

### 最佳实践建议总结

|项目|建议|
|---|---|
|HTTP 状态码|正常业务请求全部返回 200|
|业务状态码（code）|用于表示逻辑处理结果|
|异常处理|用统一封装结构返回错误码与提示|
|特殊错误|少量使用如 401、403、404 等技术错误码|
