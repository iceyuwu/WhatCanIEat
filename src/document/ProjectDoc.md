# What Can I Eat

> 一个吃饭的网站

## 项目技术栈

### 前端

- Vue3

### 后端

- Spring
- SpringBoot
- SpringMVC
- SpringAI(待定)
- Mybatis
- MybatisPlus
- Swagger(接口文档)
- MySQL

## 开发工具

- VSCode（前端开发）
- IntelliJ IDEA（Java开发）
- Maven（依赖管理）
- Postman（接口测试工具）
- DataGrip（数据库开发）
- Git/GitHub（版本管理/代码托管平台）

## 接口设计
> http://localhost:8080/swagger-ui/index.html

### 用户模块
#### 1.登录
入参：
- account 
- password

返回：
- 用户信息

#### 2.注册
入参：
- account
- password


返回：
- 用户信息

#### 3.查看个人信息
入参：
- id（用户）

返回：
- 用户信息
#### 4.注销
入参：
- account
- password

返回：
- 执行结果
#### 5.修改个人信息
入参：
- 用户信息
返回：
- 用户信息

#### 6.找回密码
入参：
返回：


### 菜谱模块
#### 1.添加菜谱
入参:
- 菜谱信息

返回：
- 创建结果
- 菜谱信息

#### 2.修改菜谱
入参:
- 菜谱信息

返回：
- 菜谱信息

#### 3.删除菜谱
入参:
- 菜谱id

返回：
- 执行结果
#### 4.按id查找菜谱
入参:
- id

返回：
- 菜谱信息
#### 5.按条件查找菜谱
入参:
- 条件

返回：
- 菜谱信息
#### 6.分页查询
入参:
- 空

返回：
- 菜谱信息列表（分页）
#### 7.随机推荐一道菜
入参:
- 空

返回：
- 随机id的菜谱信息



## 数据库设计
