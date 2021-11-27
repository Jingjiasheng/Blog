<p align="center">
	<img width="130px" src="https://raw.githubusercontent.com/j_jiasheng/Pictures/master/BlogSystem/logo.png"/>
	<br/><h1 align="center">BlogSystem<br/></h1><br/><br/>
</p>

## 博客系统

本系统是基于 [duanjn](https://github.com/DuanJiaNing/BlogSystem) 的博客项目修改版本，请支持原创！！！！

在线查看：[blog](http://blog.automan.vip),&nbsp;[我的主页](http://blog.duanjn.com/CAFE_BABE/archives)<br>

垃圾服务器，请勿测试攻击，拜托了 _||_

### 版本更新说明

- 更新 `sql` 脚本中的组合唯一键错误
- 添加 博文编辑器 Markedown 当中的图片上传接口，并实现本地图片的上传与回显
- 修改图片大小限制，支持上传较大图片
- 更新 `sql` 脚本中创建表的时候设置的自增步长为 `1`，解决地一个用户注册访问服务器崩溃的 bug

### 运行说明
- 首先需要准备 5.7 版本的 mysql 数据库
- 准备 tomcat web 容器， 版本不限，均可运行
- 配置 config 当中的 db.properties 数据库相关的配置
- 等待 相关的项目以来下载完成之后 打包成 war 包，注意使用 `mvn clean package -DskipTests` 命令跳过测试
- 将 war 包放置到 tomcat 的 webapps 目录中，重启 tomcat ，将解压后的 BlogSystemxxxxx 下的文件复制到 ROOT 目录下
- 上一步命令不可省略，项目中的静态资源并没有配置项目名到路径中，所以只能使用域名直接访问
- 重启 tomcat ，访问 [localhost:8080](http://localhost:8080) 即可访问项目
- 其他操作请直接访问 [原作者项目](https://github.com/DuanJiaNing/BlogSystem)

License
============

    Copyright 2017 j_jiasheng

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


