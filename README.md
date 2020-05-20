# spring-boot-vue-element-admin

#### 介绍
spring-boot-vue-element-admin 是使用 vue-element-admin 使用后台java语言实现

#### 软件架构
1.spring-boot、mybatis、mysql

#### 安装教程

1. 需要下载MySQL数据库服务，创建 testdb 数据库
2. 执行 admin.sql 
3. 修改admin项目application.yml
![数据源配置](https://images.gitee.com/uploads/images/2020/0520/093423_ae8028a7_4764922.png "屏幕截图.png")
4. 启动成功后输入：http://localhost:8080  (这是预先编译好的只是为了预览方便)
![登录](https://images.gitee.com/uploads/images/2020/0520/093116_28977fb3_4764922.png "屏幕截图.png")
#### [vue-element-admin](https://gitee.com/liguanfeng/vue-element-admin) (前端源码,默认为dev分支)
1. 前端源码安装需要依赖 nodejs
2. `npm run install`  如果觉得慢的话使用淘宝镜像
3. 修改接口访问地址
![输入图片说明](https://images.gitee.com/uploads/images/2020/0520/094235_656ff355_4764922.png "屏幕截图.png")

#### 使用说明
1.  admin 模块支持基本 后台管理员登录、角色管理、菜单管理 支持按钮级别权限控制 支持通配符配置URL权限

#### 用户账号登录
1. 数据脚本默认有两个用户，admin，test (admin拥有所有操作权限)
![登录](https://images.gitee.com/uploads/images/2020/0520/093116_28977fb3_4764922.png "屏幕截图.png")
2. 账号管理
![输入图片说明](https://images.gitee.com/uploads/images/2020/0520/094446_aae47d00_4764922.png "屏幕截图.png")
3. 菜单管理
![菜单列表](https://images.gitee.com/uploads/images/2020/0520/094636_478421a4_4764922.png "屏幕截图.png")
- 自动识别组件
![新增菜单或权限](https://images.gitee.com/uploads/images/2020/0520/094709_838811a1_4764922.png "屏幕截图.png")
- 自动识别接口权限URL
![输入图片说明](https://images.gitee.com/uploads/images/2020/0520/095048_debe8ab5_4764922.png "屏幕截图.png")
4. 角色管理
![输入图片说明](https://images.gitee.com/uploads/images/2020/0520/095322_1d9fb830_4764922.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0520/095354_931e9fe9_4764922.png "屏幕截图.png")
#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


