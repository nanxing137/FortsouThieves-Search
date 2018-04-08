# Spring

#### 什么是IOC

> 如不使用IOC，代码中创建对象直接操作接口实现类，没有面向接口开发。调用接口的类和接口实现类之间存在直接耦合。
>
> 解决：调用接口类和接口实现类解耦
>
> 可以将创建接口实现类对象的工作交给工厂来做
>
> 结果:对象只与工厂耦合，对象之间没有耦合

**IOC:将原来程序中自己创建实现类对象的控制权反转到IOC容器中**

---

## 配置applicationContext.xml

spring的IoC容器配置文件applicationContext.xml

#### 配置bean

1. 编写接口及实现类
2. 需要在spring的容器的配置文件中配置spring要管理的bean

#### spring对bean实例化的三种方法

> spring对applicationContext.xml中配置的bean进行实例化



1. 通过类构造方法(无参)

   直接配置即可 

   ```xml
   <bean id="XXX" class="XXXX"></bean>
   ```

2. 通过类构造方法(有参)

   ```xml
   <bean id="XXX" class="XXXX">
       <!-- 
   	一切参考构造函数
   	index：参数位置
   	value：默认参数值
   	type：参数类型
   	-->
   	<constructor-arg index="0" value="1" type="java.lang.Integer"></constructor-arg>
       <constructor-arg index="1" value="11111" type="java.lang.Long"></constructor-arg>
       <constructor-arg index="2" value="Lucifer" type="java.lang.String"></constructor-arg>
   </bean>
   ```

3. 通过静态工厂方法获取bean实例

   ```xml
   <bean id="XXX" class="工厂类路径" factory-method="工厂里具体方法"></bean>
   ```

   ​

---

#### ApplicationContext

ApplicationContext理解为spring容器的上下文，同故宫上下文操作容器中bean

`ClassPathXmlApplicationContext`：加载classpath下的配置文件创建一个容器实例

`FileSystemXmlApplicationContext`：加载文件系统任意目录下的配置文件，创建一个容器实例



掌握：多文件的加载方法

> 下列方法可混合使用

1. 通过String数组加载

```java
ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring/applicationContext.xml","spring/applicationContext2.xml"});
```

2. 通过通配符加载

```
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:bean*.xml");
```

---

## DI（依赖注入）

控制反转，哪些对象被反转，获取依赖对象的过程被反转了

action调用service，action依赖service，在action中所依赖的service创建被反转到spring容器。

service依赖dao，依赖的dao也被反转到容器。

#### 依赖注入：在ioc运行期间，动态的将对象的依赖关系注入到对象的属性中。

service：依赖dao，如何实现依赖注入？

1. spring要管理service（前提）
2. spring要管理dao（前提）

> 总结：依赖方（service）、被依赖方（dao）都需要被spring管理

3. 根据依赖关系，将dao实例注入到service的属性中

#### DI使用

目标：让spring将service依赖的dao注入

1. 在spring容器中配置dao和service

2. 配置依赖关系，service依赖dao

   ````xml
   <!-- 
    spring进行依赖注入时候根据name拼接成一个set方法，name是set方法后面的串（首字母变小写）
    ref：从容器中获取指定名称的bean实例
    value：制定一个具体的数值
     -->
   <bean id="userService" class="service.impl.UserServiceImpl">
       <!--在本类中，有几个需要注入的对象，就写几个property -->
       <property name="ftsUsersDao" ref="ftsUsersDao"></property>
   
   </bean>
   ````

3. 根据依赖关系，service依赖dao，将dao实例注入至service属性中（spring自动处理）

   **底层原理：spring根据配置文件中的依赖关系，首先获取被以来对象dao实例，调用service对象中的set方法将dao实例注入到service属性**

---

#### 小结

IOC和DI区别

ioc：控制反转，将对象的创建权反转到ioc容器

di：依赖注入，将对象所依赖的对象，注入到对象的属性中

​	就是ioc的一个具体实现方法



- ioc理解为容器
- ios容器包括spring管理的所有bean
- ioc容器负责对bean实例化（3种方法）
- ioc容器对bean进行实例化时检查有哪些依赖属性，将依赖的属性注入实例化的bean属性中

要实现依赖注入，需要spring管理依赖方和被依赖方（spring要对依赖方和被依赖方实例化）





**个人理解**

**ioc为通过反射创建对象，相当于new XXX**

**di为为属性赋值，相当于=**

**两者都是框架隐性操作，需要配置xml文件支持**



---

## 依赖注入方法（重要）

1. 通过有参构造器注入属性值

   ```xml
   <bean id="xxx" class="xxxx">
       <!-- 允许多个值，角标递增，类型对应即可-->
       <constructor-arg index="0" value="xxx" type="java.lang.String"></constructor-arg>
   </bean>
   ```

2. 通过set方法注入（最常用）

   ```xml
   <!-- 
    spring进行依赖注入时候根据name拼接成一个set方法，name是set方法后面的串（首字母变小写）
    ref：从容器中获取指定名称的bean实例
    value：制定一个具体的数值
     -->
   <bean id="userService" class="service.impl.UserServiceImpl">
       <!--在本类中，有几个需要注入的对象，就写几个property -->
       <property name="ftsUsersDao" ref="ftsUsersDao"></property>

   </bean>
   ```

3. p命名空间

   ```xml
   <!--引用对象-->
   <!--引入常量-->
   <bean id="xxx" class="xxxx"
          
         p:xxx-ref="xxx"
         p:xxx="xxxx"
         >
   </bean>
   ```

4. spEL表达式注入

   Spring Expression Language (SpEL)

   有一套椅子的语言规范

   [SpEL]: https://docs.spring.io/spring/docs/3.0.x/reference/expressions.html	"SpEL"




---

## spring管理struts的action（重要）

### spring和web系统整合

#### 1. 分析

> 在action中从spring容器获取bean实例
>
> 由于action由struts2框架进行实例化，所以无法实现依赖注入（将service注入action属性中）

解决：让spring对action进行实例化，完成action的依赖注入

**注意：spring对bean进行实例化时前提，才可以对bean进行依赖注入**

#### 2. applicationContext管理

applicationContext是spring的容器实例的对象，对它的管理采取单例模式

spring提供方案对applicationContext容器实例对象进行单例管理

**将applicationContext和servletContext（单例）绑定**

**采取方法：在web.xml中添加监听器，当系统启动时候，加载spring容器，创建spring容器，将applicationContext和servletContext进行绑定**

web.xml中添加监听

```xml
<!-- 添加监听器，加载spring配置文件，创建spring实例，和servletContext绑定 -->
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>	
<!-- 如果不指定applicationContextLocation，默认加载/WEB-INF/applicationContext.xml -->
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
</context-param>
```

### spring和struts2整合

首先加一个包`struts2-spring-plugin-2.3.24`

#### 步骤（非常重要）

1. 将action在spring容器中配置

   ```xml
   <bean id="userAction" class="web.action.UserAction" p:userService-ref="userService"></bean>
   ```

2. 在struts.xml配置文件中，将action的class该为容器中的bean

   ```xml
   <!--
   spring和struts2整合后，class要改为spring容器中action的id
   struts2拿着class（beanname）从spring容器中找
   -->
   <action name="add" class="userAction" method="add">
       <!-- 
      name:action返回的逻辑视图名
      type:制定结果类型
       dispatcher:转发到页面
       redict:重定向：直接配置url
       -->
       <result name="success" type="dispatcher">
           /jsp/user/add.jsp
       </result>

   </action>
   <action name="addSubmit" class="userAction" method="addSubmit">

       <result name="success" type="dispatcher">
           /jsp/success.jsp
       </result>

   </action>
   ```

   ​

---

## bean的作用域

spring提供的bean的作用域：

| 类别          | 说明                                                         |
| ------------- | ------------------------------------------------------------ |
| singleton     | 在spring ioc容器中仅存在一个bean实例，bean以单例方式存在     |
| prototype     | 每次从容器中调用bean时，都返回一个新的实例，即每次调用getBean()时，相当于执行new XXXBean() |
| request       | 每次HTTP请求都会创建一个新的bean，该作用域仅适用于WebApplicationContext环境 |
| session       | 同一个HTTP Session共享一个bean，不同Session使用不同的bean，该作用域仅适用于WebApplicationContext环境 |
| globalSession | 一般用于Prolet应用环境，该作用域仅适用于WebApplicationContext环境 |

**重点！！！**

dao：单例，在dao中不要添加具有状态的属性

service：单例，在service中不要添加具有状态的属性

action：多例

servlet：单例

**当类线程安全时可以用单例，没有数据域的成员变量（具有状态的属性）**

---

## Spring开发流程

1. 编写dao接口，在spring容器配置dao

   dao要以单例模式管理

2. 编写service接口及实现类，将service依赖的dao作为service类的属性及get/set方法定义

   在spring容器中配置service，配置service依赖的属性

   service要以单例模式管理

3. 编写action类，并且将action所依赖的service也最为属性定义，并且定义属性get/set方法

   配置action

   1. 在spring容器配置action

      以多例模式管理struts2的action

   2. 在struts.xml配置action


---

## 基于注解开发

#### 一、实例化的注解

```java
@Repository("xxxx")
```

用来标识此bean是持久化bean（dao），可指定bean的名称。不指定默认类名首字母小写

```java
@Service("xxxx")
```

用来标识此bean是一个业务bean（service）

```java
@Controller("xxxx")
@Scope("prototype")
```

用来标识此bean是一个控制器

使用scope注解标识bean的作用域



> 除此之外

```java
@Component
```

可以用在任何bean上

使用此标记标识它为一个bean

**配置组件扫描**

```xml
<!-- 
配置组建扫描
component-scan可以扫描疏解@controller、@Service、@Repository、@Component
指定扫描的包
-->
<context:component-scan base-package=""></context:component-scan>

```

> 原理：
>
> 加载spring容器时，根据上边配置的包路径，扫描包下及子包下的类，如果表示了@controller、@Service、@Repository、@Component 就进行实例化





#### 二、依赖注入

按类型注入

```
@Autowired
@Qualifier("指定bean的id/name")
```

1. 可以标记在属性上，根据属性类型，去spring容器中找同类型的bean，如果找到，则将bean注入到属性中

   注意：如果容器中同一个类型的bean有多个，使用Autowired会报错。使用@Qualifier("XXX")和@AutoWired配置，Qualifier指定将哪个bean注入

2. Autowired和Qualifier可以用在set方法上

   找set方法参数类型，从容器中找bean，进行注入

按名称注入

```java
@Resource("XXXXXX")
```

按照名称注入，如果找到，则注入

和Autowired区别：

- Resource是jdk下的，而Autowired是spring提供的。
- Resource默认名称匹配，允许空值。如果没写则默认类名。如果使用默认并且没有通过名称匹配，则进行类型查找匹配
- Autowired默认通过类型匹配，不允许空值（除非写`@Autowired(required=false)`）。如需要名称匹配，则需要加`@Qualifier("指定bean的id/name")`



---

## 注解和xml混合开发（重要，实际开发应用）

bean实例化：采用xml配置方式，分别使用不同的xml配置action，service，dao。为了更好的管理系统中的bean

bean属性依赖注入：采用注解方式。为了简化开发

**使用到的注解 **

```java
@Autowried
@Qulifier
@Resourse
```

**web.xml注解改动**

```xml
<!-- 添加监听器，加载spring配置文件，创建spring实例，和servletContext绑定 -->
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>	
<!-- 如果不指定applicationContextLocation，默认加载/WEB-INF/applicationContext.xml -->
<!-- spring配置文件允许多个，半角逗号隔开即可 -->
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:spring/applicationContext.xml,classpath:spring/applicationContext-*.xml</param-value>
</context-param>
```

