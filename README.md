# Getting Started

## 部署
### 依赖
- git
- jdk
- maven
- mysql

##步骤 centeros 系统
- yun update
- yum install git
- mkdir app
- cd app
- git clone github.io.forum.git
- yum install maven
- mvn -v
- mvn clean compile package
- cp xxx/xx/application.properties  xxxx/ss/application-prod.properties
- vim  application-prod.properties

- mvn package

- java -jar -Dspring.profiles.active=prod target/forum-0.0.1-snapshot.jar




### 技巧
git commit --amend --no-edit   （追加  不需要改备注文案）

### 资料

[spring web文档](https://spring.io/guides/gs/serving-web-content/)
[es论坛官网](https://elasticsearch.cn)

[github--oauth-app](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
https://github.com/settings/apps/chenyimo-forum

[github-oauth-认证步骤](https://developer.github.com/apps/building-github-apps/identifying-and-authorizing-users-for-github-apps/)

[markdown富文本编辑](http://editor.md.ipandao.com/examples/)
[ufile sdk](https://github.com/ucloud/ufile-sdk-java)

[ucloud文件存储](https://console.ucloud.cn/ufile/ufile/detail?id=cym) 

### 开源的论坛 类似这个（https://elasticsearch.cn ）

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Web Starter](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

### 数据库
```sql
CREATE CACHED TABLE PUBLIC.USER(
    ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_A7ADE43F_7CCF_4195_B206_D6DFF919C5C6) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_A7ADE43F_7CCF_4195_B206_D6DFF919C5C6,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(100),
    TOKEN CHAR(36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT
)


mvn flyway:migrate

```

[h2数据库添加用户的命令](https://blog.csdn.net/qq_19671173/article/details/68952965)

### 添加flywaydb 数据库管理
[添加flywaydb](https://flywaydb.org/getstarted/firststeps/maven#integrating-flyway)

### lombok
[lombok](https://www.projectlombok.org/features/all)

### 开发小插件 & 小技巧
OneTab
LiveReload 改变html的时候 页面将自动更新 


interllij debug调试

drop frame 的方式。（回到上一个调用栈）

### 分页逻辑 
每页显示5条

0 5 1   
5 5 2   
10 5 3

5*（i-1）


12%5!=0 12/5 +1
else
12%5==0 12/5

select * from QUESTION limit 0,5;


## 技术站