# Introduction of OpenMsg
	OpenMsg is an opensource social app messages handler. It can do some helpful
	functions such as auto reply, auto group message sending, and so on.
	In the past, we do group message sending by using some kinds of web api or Restful api.
	But we find it easy to freeze your account. So we need a method to simulate human's action.
	
# OpenMsg 简介
OpenMsg是一个开源社交应用消息处理程序。它可以做一些有用的
自动回复，自动组信息发送等功能。
过去，我们使用某种web api或Restful api对消息发送进行分组。
但我们发现冻结您的帐户很容易。所以我们需要一种模拟人类行为的方法。

# 开发日志

## 2018/10/2

* 开发基本完成，核心功能已经实现。
* 由于暂无方法在非ROOT下实现跨进程模拟点击事件，所以改用CoolQ协议发送消息。
* 日后会将方案修正为模拟触控。
* 如果您了解实现跨进程模拟点击的方法.
* 下面是测试图：

## 2018/9/30

* 项目基本模块成功架构完毕。
* TODO：模拟触控，实现QQ群发功能
