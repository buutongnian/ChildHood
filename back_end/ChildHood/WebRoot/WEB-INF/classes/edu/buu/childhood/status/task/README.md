edu.buu.childhood.status.task
===
### (童年项目)[status.task包]

---
## 包内容
* #### Quartz相关类
* #### 与openfire插件通信类

## 作用
* #### 每五分钟调度任务更新数据库中用户在线状态
* #### 与openfire插件通信获取用户在线状态

## 包内类
* #### edu.buu.childhood.login.task.LoginStatusTask：
> ##### Quartz定时任务，每隔五分钟定时刷新openfire服务器端所有用户在线状态到数据库中

* #### edu.buu.childhood.login.task.UserStatus：
> ##### 与openfire服务器Presence Service插件通信，利用URL请求自动判断数据库中用户登录状态，若登录状态与openfire服务器端状态不一致，则更新数据库内用户状态
