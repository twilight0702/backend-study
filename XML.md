---
视频地址: https://www.bilibili.com/video/BV1UN411x7xe?spm_id_from=333.788.videopod.episodes&vd_source=a503248b608b8da9614b6dd7eb24901d&p=53
---
# 什么是XML

可扩展标记语言

和html都是标记语言，基本语法都是标签

但是不能随便写

多数作为配置文件，不只是java，各个文件都可以用

之前用properties（JDBC中做数据库连接信息），也可以作为配置文件，但是无法表示复杂的配置
用的是key=value
如果是可能有多个环境（测试环境和生产环境），就需要重复多个，比较混乱，容易错，格式也有要求
xml就有优势，层次结构就会比较清晰

会有约束，不能随意写标签
约束两种，无需深入了解，idea可以自己生成，实际就只有写具体内容

# XML解析（DOM4J）

（感觉不太重要不细看了）
