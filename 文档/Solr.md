# Solr

> 全文检索服务器

#### solr和lucene区别：

lucene是一个全文搜索引擎工具包，不能独立运行，对外提供服务。

solr是一个全文检索服务器，它可以单独运行在servlet容器。可以单独对外提供搜索和索引功能。

solr比lucene在开发全文检索功能时，更快捷，更方便。

---

#### solr安装配置

1. 下载solr：https://lucene.apache.org
2. 运行环境
   - jdk
   - solr
   - mysql
   - web服务器：tomcat

#### solr的安装

1. 安装tomcat

2. 将工程部署到tomcat

3. 添加solr扩展服务包

4. 添加log4j.properties

   在WEB-INF下classes目录下（没有则创建）