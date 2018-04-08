# struts2使用文档

struts2执行流程

- 用户请求前端控制器`StrutspreapredAndExecuteFilter`

  它是一个Filter，拦截用户的请求，根据url调用Action

  提前在struts.xml配置文件将action类进行配置

- 前端控制器调用`Action`

  Action：封装请求参数，调用service

  Action：根据不同的业务需求，需要程序员开发

- Action调用service获取数据，业务处理

- 前端控制器（`StrutspreapredAndExecuteFilter调用Result`），响应结果

  Result：相应结果，支持不同的视图技术

- Result根据用户的调用使用不同的视图技术

  在struts.xml中配置Result转发到的jsp路径


- Result响应结果

  底层通过servlet API进行响应


---



框架提供：

- 前端控制器`StrutspreapredAndExecuteFilter`
- struts2提供了多个类型的Result

程序员开发：

- Action类
- struts.xml
- jsp页面

---

## 开发流程

1. 前端控制器


---

## Action

#### Action定义的规范

1. Action可以是一个pojo（不实现任何类和接口）
2. Action可以实现Action接口或者继承ActionSupport
3. Action中定义的请求处理方法必须是public、且没有参数，方法名不限制，方法的返回值可以是void或String

---

#### 参数绑定

>  struts提供将请求的参数自动封装到pojo/集合等对象中

**属性驱动:**

​	如果按照struts指定的开发规则开发，自动请求的参数设置（赋值）到struts的action类的属性中。

​	开发规则

 1. 确定请求参数名称

 2. 在action类中添加属性，属性名和参数名一直，并添加get/set方法

    真正的规则是setXXXX()，这个set方法后的XXX（首字母变为小写）和参数名一致





属性驱动支持不同action类属性类型，包括基本类型（String/int）、list、map、数组、pojo



1. 基本类型：直接对应名字即可，命名规则为驼峰

2. pojo：页面写成`对象.属性`，可递归命名，命名规则为驼峰

3. 数组：名字相同即可

4. list:

   1. 后端创建映射

      比如List<pojo> list;

   2. 前端：

      name="list[index].pojo中属性名"

---

#### 动态配置访问

```xml
<action name="*"
        class="XXXXXXXXX"
        method="{1}">
    <result name="success">/demo/{1}.jsp</result>
</action>
```

或

```xml
<action name="*_*"
        class="{1}"
        method="{2}">
    <result name="success">/{2}.jsp</result>
</action>
```

---

#### 分模块配置struts

- 分模块先配置各个xml

- 在struts.xml中使用

  ```xml
  <include file="struts-xxx.xml"></include>
  .....
  ```

- 各个模块要继承common中的package

  因为可以使用公用包中的全局result

common包

```xml
<package name="common" extend="struts-default">
	<global-result name="success" type="dispatcher">
        jsp/success.jsp
    </global-result>
</package>
```

