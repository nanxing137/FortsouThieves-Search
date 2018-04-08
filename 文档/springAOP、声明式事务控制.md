# springAOP、声明式事务控制

#### 1. spring整合hibernate完成事务控制

关键点：spring将hibernate的session托管

重点：

- 学习hibernateTemplate（封装hibernate数据库方法模板）
- spring对事务控制支持，提供三个对象：事务控制器、事务定义信息对象、事务状态信息对象

> 使用hibernateTemplate，使用spring提供的三个对象，完成事务控制



#### 步骤（很重要）

##### **配置dao**

1. 在dao实现类中继承`HibernateDaoSupport`（注意版本！！！此处选5）
2. 使用时`this.getHibernateTemplate().save(ftsUsers);`

运行报错：`sessionFactory`or `hibernateTemplate` is required

解决：将 `sessionFactory `  or `hibernateTemplate` is required注入到dao中

```xml

<!-- 加载db.properties配置文件 -->

<context:property-placeholder location="classpath:db.properties"/>


<!-- 
 配置组建扫描
 component-scan可以扫描疏解@controller、@Service、@Repository、@Component
 指定扫描的包
 -->
<context:component-scan base-package="fts"></context:component-scan>
<bean id="ftsUsersDao2" class="fts.dao.impl.FtsUsersDaoImpl2">
    <property name="hibernateTemplate" ref="hibernateTemplate"></property>

</bean>





<!-- hibernateTemplate，依赖了sessionFactory -->
<bean id="hibernateTemplate" class="org.springframework.orm.hibernate5.HibernateTemplate">
    <!-- 配置sessionFactory -->
    <property name="sessionFactory" ref="sessionFactory"></property>
</bean>
<!-- 配置sessionFactory,依赖数据源(数据库连接池) -->
<bean id="sessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
    <!-- 依配置数据源 -->
    <property name="dataSource" ref="dataSource"></property>
</bean>
<!-- spring对数据源进行管理 -->
<bean name="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
    <property name="driverClass" value="${jdbc.driver}"></property>
    <property name="jdbcUrl" value="${jdbc.url}"></property>
    <property name="user" value="${jdbc.user}"></property>
    <property name="password" value="${jdbc.password}"></property>
</bean>

```

> 注意层层依赖关系

加载hibernate配置的映射文件：在创建SessionFactory的时候加载hibernate映射文件

未完，不写

---

## AOP

#### **AOP原理：**

**aop面向切面编程的原理就是代理**

1. 静态代理：

   采用一些工具类，对原来的类生成一个代理类，代理类以.class存在

2. 动态代理（重点）：

   在运行过程中，通过反射来生成类的代理对象，在代理对象中对原来的对象进行增强

**spring采用动态代理的技术：**

1. 基于接口生成代理对象

   根据接口方法生成代理对象，对原来代理对象增强

   使用jdk提供的反射实现

2. 基于类生成代理对象

   根据类生成一个子类（代理对象），在代理对象中对父类进行增强

   使用CGLib实现

#### AOP联盟

AOP联盟出一套AOP标准

AOP术语

- Pointcut（切入点）：确定在哪个方法上增强，一个类有多个切入点

- Advice（通知/增强）：在切点上进行增强。包括：前置增强、后置增强、抛出异常增强、最终增强、环绕增强

- Target（目标对象）：对**目标对象**进行增强，产生代理对象

- Proxy（代理）：对目标对象进行增强，产生**代理对象**。即增加连接织入的目标类

- Introduction（引介）：引介是一种特殊的增强，在不修改类代码的前提下可以在运行期间为类动态地添加一些方法或属性，引介是针对类的一种增强（操控字节码）

- Weaving（织入）：

  > 是指把增强代码织入到目标类具体连接点上的过程，aop有三种织入方式：

  1. 编译期织入

  2. 类装载期织入

  3. 动态代理织入，在运行期间，为目标类生成代理子类

     **spring采用动态代理织入，而AspectJ采用编译期织入和类装载期织入**

- Aspect（切面）：包括切点和增强，面向哪个切点进行增强（编程）



---

## AspectJ语法

#### 通过语法配置切入点

切入点：方法和类

表达式包括两部分：

- 函数名
- 操作参数

execution函数名

核心：

- ..代表数量不限
- *代表名字不限
- .代表包
- +代表子类

掌握：

- execution(*cn.service.impl.*.*(..))

  匹配cn.service.impl包下所有类的所有方法（任意形参）

- whitin(cn.service..*)

  匹配cn.service包下所有类（包括子包）所有方法

#### 增强

- Before（前置增强）相当于BeforeAdvice
- AfterReturning（后置增强）相当于AfterReturningAdvice
- Around（环绕增强）相当于MethodInterceptor
- AfterThrowing（抛出异常增强）相当于ThrowAdvice
- After（最终增强）不管是否异常，该通知都会执行

```java
public void sava(){
    try{
        //前置增强
        调用目标方法执行；
        //后置增强
    }catch(Exception ex){
        //抛出异常增强
    }finally{
        //最终finally增强
    }
}
```



#### 代理对象生成步骤

1. spring要对目标类进行管理（前提）

   在容器中配置目标类

2. 编写增强类（增强代码）

3. 切点，spring要根据切点，找到目标类中的目标方法

4. 生成代理对象（spring自动完成）

---

## 配置文件汇总

- 总配置

  1. 加载db.properties数据连接
  2. 配置hibernateTemplate，sessionFactory，dataSource

- action配置注入service

- dao配置注入hibernateTemplate

- service配置注入dao，注意不用注入transactionManager

- transaction配置（重点）声明式事务配置↓↓↓↓↓↓

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
  	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  	xmlns:p="http://www.springframework.org/schema/p"
  	xmlns:aop="http://www.springframework.org/schema/aop"
  	xmlns:tx="http://www.springframework.org/schema/tx"
  	xmlns:context="http://www.springframework.org/schema/context"
  	xsi:schemaLocation="
          http://www.springframework.org/schema/beans 
          http://www.springframework.org/schema/beans/spring-beans.xsd 
          http://www.springframework.org/schema/context 
          http://www.springframework.org/schema/context/spring-context.xsd 
          http://www.springframework.org/schema/aop 
          http://www.springframework.org/schema/aop/spring-aop-2.5.xsd 
          http://www.springframework.org/schema/tx 
          http://www.springframework.org/schema/tx/spring-tx.xsd">


  	<!-- 配置事务管理器 -->
  	<bean id="transactionManager"
  		class="org.springframework.orm.hibernate5.HibernateTransactionManager">
  		<property name="sessionFactory" ref="sessionFactory"></property>
  	</bean>

  	<tx:advice id="txAdvice"
  		transaction-manager="transactionManager">
  		<!-- 配置事务定义信息 propagation:传播行为REQUIRED表示需要在事务中运行，如果没有事务，则开启一个新事务 name:匹配方法名，执行方法时，设置事务定义信息 
  			insert*:表示以insert开头的所有方法 设置其传播行为为REQUIRED follback-for:配置异常类类路径，当此方法抛出此类异常，则回滚事务，默认RuntimeException -->
  		<tx:attributes>
  			<tx:method name="insert*" propagation="REQUIRED" />
  			<tx:method name="update*" propagation="REQUIRED" />
  			<tx:method name="delete*" propagation="REQUIRED" />
  			<tx:method name="find*" propagation="SUPPORTS"
  				read-only="true" />
  		</tx:attributes>
  	</tx:advice>
  	<!-- 配置切面 -->
  	<aop:config>
  		<!-- 使用advisor也可以配置切面 实现最终和上面的tx:advice配对使用，实现事务控制 advice-ref:指定引用哪个增强 -->
  		<aop:advisor advice-ref="txAdvice"
  			pointcut="execution(* fts.service.impl.*.*(..))" />
  	</aop:config>
  </beans>
  ```

  ​

JDK或CGLib

默认为false，使用jdk代理

```xml
<aop:config proxy-target-class="false"></aop:config>
```

改为true，使用cglib代理

注：如果此类没有实现接口，只能使用cglib代理



---

## 注解开发事务控制

在需要控制的方法（通常为insert，update，delete，find...）前加上`@Transactional`，内置很多参数，可以配置，不写任何参数默认REQUIRED

还要加上注解驱动

```xml
<!-- 注解驱动事务管理 -->
	<tx:annotation-driven transaction-manager="transactionManager"/>
	
```

最后事务控制xml

```xml
	<!-- 配置事务管理器 -->
	<bean id="transactionManager"
		class="org.springframework.orm.hibernate5.HibernateTransactionManager">
		<property name="sessionFactory" ref="sessionFactory"></property>
	</bean>
	
	<!-- 注解驱动事务管理 -->
	<tx:annotation-driven transaction-manager="transactionManager"/>
	
```

