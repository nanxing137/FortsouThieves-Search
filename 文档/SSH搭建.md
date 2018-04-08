# SSH搭建

## 流程

#### 环境：

1. 创建各种包

   - dao,dao.impl
   - service,service.impl
   - web
   - util
   - domain

2. 创建数据库

   略

#### 分析

**持久层：**

1. Spring管理dao接口，使用单例管理
2. Dao接口使用Spring提供的HibernateTemplate模板进行开发（Dao接口开发方法）
3. Spring管理sessionFactory，使用单例管理
4. Spring管理数据源，使用c3p0数据库连接池

**业务层：**

1. Spring管理service接口使用单例管理
2. 使用Spring声明式事务控制方式，对service接口进行事务控制

**web层：**

1. Spring管理Action接口，使用**多例管理**



> 注：管理：控制反转，依赖注入



#### 操作步骤（重要）

> 整合持久层

1. 导入jar包

   - hibernate基础运行包
   - spring基础运行包
   - spring整合框架包
   - 数据库驱动包
   - c3p0数据库连接池（数据源）包
   - log4j日志包
   - junit单元测试包
   - spring整合test测试框架(junit)包

2. 配置数据源

   由spring管理C3P0数据库连接池

   - 配置applicationContext

     - 添加约束


     - 配置数据库连接池
    
     - 配置sessionFactory
    
       使用spring提供的`org.springframework.orm.hibernate5.LocalSessionFactoryBean`
    
       spring可从工厂中获取
    
     - 配置hibernateTemplate

   - 配置hibernate运行参数

   - 开发dao

     使用HibernateTemplate开发，dao接口实现类继承HibernateDaoSupport

   - 配置dao

> 整合业务层

 1. 开发service

 2. service配置

 3. 声明式事务控制

    基于AOP思想，对service产生代理对象，由代理对象进行事务控制

    - 配置事务管理器
      - 负责事务控制
    - 配置增强
      - 对目标对象，进行事务增强
    - 配置AOP
      - 配置切点

> 整合web层（部分，其余见下）

1. 加入jar包

   - struts2

   - spring和web层整合包

     spring-web-5.0.4.RELEASE

   - spring和struts2的整合包

     struts2-spring-plugin-2.5.16

> struts2整合spring方法

1. 方案1

   1. struts2-spring-plugin-2.5.16中有一个常量的配置

      ```xml
      <bean type="com.opensymphony.xwork2.ObjectFactory" name="spring" class="org.apache.struts2.spring.StrutsSpringObjectFactory" />

      <!--  Make the Spring object factory the automatic default -->
      <constant name="struts.objectFactory" value="spring" />
      ```

      默认设置struts.objectFactory（对象工厂）的值为spring，即对象工厂

      说明：原来由struts2自己创建，改为由spring和struts2联合创建

**方案1：**（建议使用）

> action由spring管理，对action实例化和依赖注入
>
> 甚至可以AOP编程

1. 开发aciton，将action再spring容器中配置，也可使用@controller注解
2. 同时，再struts.xml中配置action的class，指定引用spring容器中bean的id或name

原理：

1. 请求到struts2框架

2. struts2通过struts.objectFactory对象工厂创建对象

   struts.objectFactory：

   逻辑：先从spring容器中找bean，调用getBean()方法，获取bean

**方案2：**（不建议）

> action不由spring管理

1. 开发action，不需要在容器中配置
2. 在struts.xml中配置class是action的全限定类名

原理：

1. 请求到struts2框架

2. struts2通过struts.objectFactory对象工厂创建对象，获取不到bean实例

   逻辑：

   ​	获取不到bean，会自己创建action实例，调用springAPI创建action实例

   ​	调用springAPI对action中依赖注入

缺点：action没有被spring管理，不能正常对action进行ioc，aop编程

> 整合web层（下）

1. 开发action

   - 属性驱动，通过action类中的属性，绑定请求参数
   - 模型驱动，专门驱动单独的模型类，绑定请求参数，在模型类中，也是属性驱动（建议使用）
   - 模型驱动好处：将属性及get/set方法全部抽取到一个类中，不用action中定义，方便维护action中业务方法

2. 配置action

   - 在spring容器中配

     注意使用多例模式

   - 在struts.xml中配置

---

## hibernate加载问题

方法0：手动加载

- 遍历调用函数↓

  ```
  xxx.GetBaseDictBySource().getXXX()
  ```

  ​



方法一：立即加载

- 更改映射文件

- 设置many-to-one参数fetch等于join

  ```xml

  ```

  ​

方法二：

1. 在web.xml中配置spring提供的过滤器

   OpenSissionInViewFilter

```xml
<!-- 解决延迟加载session在web层不可用的问题 -->
<filter>
    <filter-name>OpenSessionInViewFilter</filter-name>
    <filter-class>org.springframework.orm.hibernate5.support.OpenSessionInViewFilter</filter-class>
    <init-param>
        <param-name>flushMode</param-name>
        <param-value>AUTO</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>OpenSessionInViewFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

原理：将session的生命周期延长到request

> 注：图中所注代码还有bug