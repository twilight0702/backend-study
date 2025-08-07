# 使用

用于快速在entity和dto、vo之间映射

```java
import org.mapstruct.Mapper;  //注意不要导入错了
import org.mapstruct.Mapping;  
import org.mapstruct.factory.Mappers;  
  
import java.util.List;  
  
@Mapper(componentModel = "spring")  //注册为组件，可以使用spring的注入
public interface PopUpMapStruct {  
    @Mapping(target = "id", ignore = true)  
    @Mapping(target = "updateBy", ignore = true)  
    @Mapping(target = "updateDate", ignore = true)  
    @Mapping(target = "delFlag", ignore = true)  
    PopUp fromPopUpCreateDTOToEntity(PopUpCreateDTO dto);  
  
    @Mapping(target = "updateBy", ignore = true)  
    @Mapping(target = "updateDate", ignore = true)  
    @Mapping(target = "delFlag", ignore = true)  
    PopUp fromPopUpUpdateDTOToEntity(PopUpUpdateDTO dto);  
  
    PopUpVO toVO(PopUp entity);  
  
}
```

对于名字不同的，也可以手动指定映射

# Maven配置

依赖配置
```xml
<!-- MapStruct 核心依赖 -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>
</dependency>

<!-- MapStruct 编译器插件 -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>1.5.5.Final</version>
    <scope>provided</scope>
</dependency>

<!-- 如果使用 Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.28</version>
    <scope>provided</scope>
</dependency>

```

插件配置
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
                <source>17</source> <!-- 或你的 Java 版本 -->
                <target>17</target>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>1.5.5.Final</version>
                    </path>
                    <path>
                        <groupId>org.projectlombok</groupId>
                        <artifactId>lombok</artifactId>
                        <version>1.18.28</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>

```

entity、DTO、VO都需要加@Data
都需要Lombok