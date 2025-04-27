# 手动导入

以下针对IntelliJ

- 下载 jar（如 `mysql-connector-java-8.0.33.jar`）
    
- 拷到 `lib/` 目录
    
- 右键 -> `Add as Library`（或者在 build path 中添加）
    
- 后续你用到别的第三方功能（如 Apache Commons、JSON 解析等），也都需要手动找 jar 包，一个个复制 + 添加（已经添加成library就不用重复了，直接放就可以）
# 使用MAVEN

使用XML配置管理包

IDEA内置Maven，但也可以自己下载

# 使用Gradle

更进阶的方法