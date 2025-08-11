>参考内容来源：
>视频
>1. [【API技术核心原理】REST | GraphQL | gRPC | tRPC](https://www.bilibili.com/video/BV1yL41167fD/?share_source=copy_web&vd_source=082d632aa56c8a143515b66648c7e6a4) By 技术蛋老师
>2. [RPC是什么？HTTP是什么？RPC和HTTP有什么区别？](https://www.bilibili.com/video/BV1Qv4y127B4/?share_source=copy_web&vd_source=082d632aa56c8a143515b66648c7e6a4) By 小白Debug
>    备注：该视频有一些说法不太准确的地方，弹幕和评论均有说明

# 关于层级问题

```mermaid
flowchart TD
  subgraph L4_传输层
    D1[TCP]
    D2[UDP]
  end

  subgraph L5_L7_传输+应用层
    C1[HTTP]
    C2[HTTPS]
    C3[WebSocket]
  end

  subgraph 应用层抽象
    B1[REST API]
    B2[gRPC]
    B3[tRPC]
    B4[GraphQL]
    B5[OpenAPI/Swagger]
    B6[JSON-RPC]
    B7[Thrift]
    B8[WebSocket API]
    B9[Socket编程]
    B10["MessageQueue<br>(Kafka/RabbitMQ/MQTT)"]
    B11[ZeroMQ/Nanomsg]
  end

  %% 底层依赖
  C1 --> D1
  C2 --> D1
  C3 --> C1

  %% 应用层依赖关系
  B1 --> C1
  B2 --> C2
  B3 --> C2
  B4 --> C1
  B5 --> B1
  B6 --> D1
  B7 --> D1
  B8 --> C3
  B9 --> D1 & D2
  B10 --> D1
  B11 --> D1

  %% 类别分组
  subgraph RPC框架
    B2
    B3
    B6
    B7
  end

  subgraph HTTP API
    B1
    B4
    B5
  end

  subgraph 消息/流式通信
    B8
    B9
    B10
    B11
  end
```
![[Pasted image 20250807115347.png]]