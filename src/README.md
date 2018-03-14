#  JNIO: A Network Framework Inspired by Netty

## 简介

fancy是一个基于事件驱动(epoll)的网络应用框架. 其原理与Netty类似，即 **Reactor模式**.，主要特性有：

- epoll+nonblocking IO. epoll是编写高性能服务器的基础设施

- 支持future,promise特性

- 使用内存池来优化GC开销

- API与Netty保持一致

## 使用

```
导入根目录下的Jar包
```
