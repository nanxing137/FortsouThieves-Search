# 持久层

更新数据时，先查再更，否则会将没有进行操作的字段赋空值。





#### 开发dao原则

1. 针对每一张表有一个dao接口
2. dao中不要有业务逻辑，因为dao是一个通用的数据库访问接口

#### 开发service

开发service根据模块去创建，一个模块创建一个service

service不用根据表为单位去创建

#### QBC查询

通过criteria对象拼装查询条件，调用criteria的add方法拼接查询条件，最终hibernate通过criteria对象自动生成sql语句

```java
Criteria criteria = session.createCriteria(FtsUsers.class);

		//criteria.add(Restrictions.eq("userName", "高鹏"));
		criteria.add(Restrictions.like("userName", "%高%"));
		
		// 查询所有数据，由于制定了FtsUsers，查询结果为表fts_users
		List list = criteria.list();
		
```



分页查询

```java
Session session = HibernateUtil.openSession();
		Criteria criteria = session.createCriteria(FtsUsers.class);
		int page = 4;
		int maxResults = 5;
		int firstResult = maxResults*(page-1);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResults);
		List list = criteria.list();
```



投影查询

可以自定义pojo类，将名字对应，即可转换为自定义类

```java
Session session = HibernateUtil.openSession();
		Criteria criteria = session.createCriteria(FtsUsers.class);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("id").as("id"))
				.add(Projections.property("userName").as("userName"))
				);

		//结果封装策略
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FtsUsers.class));
		List<FtsUsers> list = criteria.list();
		System.out.println(list);

```

count查询

```java
Session session = HibernateUtil.openSession();
		Criteria criteria = session.createCriteria(FtsUsers.class);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("id").as("id"))
				.add(Projections.property("userName").as("userName"))
				);

		//结果封装策略
		//criteria.setResultTransformer(new AliasToBeanResultTransformer(FtsUsers.class));
		
		criteria.setProjection(Projections.rowCount());
		List<FtsUsers> list = criteria.list();
		int count = (int) criteria.uniqueResult();
		System.out.println(list);
		System.out.println(count);
```

