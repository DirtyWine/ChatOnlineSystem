# ChatOnlineSystem
A test demo for Java Web final exam <br>
Powered by Vue + Springboot + MyBatis + websocket <br>
目前只是基本框架，已经搭建Vue+Springboot，配好了mybatis。没有WebSocket
# Requirement
* Node.js / MySql / Idea 
* 为了可以成功运行这个测试案例，你需要先完成以下配置
  * 安装lombok插件(十分便捷的getter/setter工具)<br>
  Edit->settings->Plugins->Browse Repositories,搜索lombok，安装并重启
  * 确保你的mysql的root账号密码为123456，并创建一个名为test的数据库，在数据库中建立一个customer表如下：
  ```SQL
  create table customer (
  name     varchar(20) not null
    primary key,
  password varchar(20) null,
  gender   varchar(2)  null )
  ```
  然后插入一条数据（'123','123','男'），这么做的目的是你不需要更改代码就可以进行测试
# How to use
## Backend 后端/服务端
* 用idea打开项目，会自动加载pom里配置的依赖，没有自动加载的话，可以 View->Tool Windows->Maven projects,在右边出来的Maven窗口里点一下左上角的刷新
* build完成后，点击启动按钮，启动后端
## Frontend 前端/客户端
* 进入frontend目录<br>
```
cd frontend
```
* 安装项目依赖包<br>
```
npm install
```
* 安装axios（用于发送http请求）
```
npm install axios
```
* 启动前端
```
npm run dev
```
* 访问localhost：8081进行登录模块测试
