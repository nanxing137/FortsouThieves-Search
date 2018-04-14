# lucene

环境准备

- jdk:1.7以上
- lucene:Apache Lucene 7.3.0
- ide:eclipse
- 数据库MySQL

主要用到的包

- analysis-common
- core
- queryparser

---

#### 文件索引结构

- 文档域

  文档域存储的就是采集到的信息，通多document对象来存储，具体是通过document对象中field域存储数据

  文档域中document对象之间没有关系，而且每个document中field域也不一定一样

- 索引域

  索引域主要是为了搜索来使用

---

#### 索引

1. 采集数据(po)
2. 创建document对象

```java
public void createIndex() throws IOException {
    //采集数据
    ResourceDao resourceDao = new ResourceDaoImpl();
    List<FtsResourceCopy> list = resourceDao.queryBooks();

    //创建将采集到的数据封装到document对象中
    List<Document> docList = new ArrayList<>();
    Document document;
    for (FtsResourceCopy ftsResourceCopy : list) {
        document = new Document();
        //Store如果是YES则说明存储到文档域中
        //图书id
        Field id = new TextField("id", ftsResourceCopy.getId().toString(), Store.YES);
        Field name = new TextField("name", ftsResourceCopy.getName(), Store.YES);
        Field resourceType = new TextField("resourceType", ftsResourceCopy.getResourceType(), Store.YES);
        Field resourceTotal = new TextField("resourceTotal", ftsResourceCopy.getResourceTotal(), Store.YES);
        Field resourceDetails = new TextField("resourceDetails", ftsResourceCopy.getResourceDetails(), Store.YES);
        //将field域设置到document对象中
        document.add(id);
        document.add(name);
        document.add(resourceType);
        document.add(resourceTotal);
        document.add(resourceDetails);
        docList.add(document);
    }

    //创建分词器
    //标准分词器
    Analyzer analyzer = new StandardAnalyzer();

    IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
    Path path = Paths.get("C:\\\\Users\\\\Thornhill\\\\Desktop\\\\FTS\\\\index\\\\luceneTest");
    Directory directory = FSDirectory.open(path);
    //创建indexWriter
    IndexWriter writer = new IndexWriter(directory, cfg);


    //通过indexWriter对象将document写入到索引库中
    for (Document doc : docList) {
        writer.addDocument(doc);
    }
    //关闭writer
    writer.close();



}
```

#### 分词

> lucene分词主要分为两个步骤：分词，过滤

1. 分词：

   将field域中的内容一个个分词

2. 过滤：

   将分好的词进行过滤。比如去除标点符号、大小写转换、词的型还原（针对英文）、停用词过滤、等

   > 停用词：单独应用没有意义的词。比如的，啊、等

#### 使用luke工具查看索引

略

---

#### 搜索流程

代码：

```java
@Test
public void indexSerach() throws ParseException, IOException {
    // 创建查询对象
    /**
		 * 第一个参数：默认搜索域的名称，需要分词器，搜索时分词器要和索引分词器一致
		 */
    QueryParser queryParser = new QueryParser("resourceDetails", new StandardAnalyzer());
    // 通过queryparser创建query对象
    // 输入的lucene查询语句(关键词需要大写)
    Query query = queryParser.parse("resourceDetails:中 AND resourceDetails:国");
    // 创建IndexSeacher
    Path path = Paths.get("C:\\\\Users\\\\Thornhill\\\\Desktop\\\\FTS\\\\index\\\\luceneTest");
    Directory directory = FSDirectory.open(path);

    IndexReader reader = DirectoryReader.open(directory);
    IndexSearcher indexSearcher = new IndexSearcher(reader);
    // 通过searcher来搜索索引库
    // 第二个参数：指定需要现实的顶部记录的N条
    TopDocs topDocs = indexSearcher.search(query, 10);
    // 根据查询条件匹配出的记录总数
    long count = topDocs.totalHits;
    //根据条件匹配除的记录
    ScoreDoc[] scoreDocs = topDocs.scoreDocs;
    for (ScoreDoc scoreDoc : scoreDocs) {
        //获取文档的ID
        int docID = scoreDoc.doc;
        //通过ID获取文档
        Document doc = indexSearcher.doc(docID);
        System.out.println("id:"+doc.get("id"));
        System.out.println("name:"+doc.get("name"));
        System.out.println("details:"+doc.get("resourceDetails"));
    }
    //关闭资源
    reader.close();
}
```

---

#### Field域

属性：

1. 是否分词

   是：对该field存储的内容进行分词，目的是为了索引

   否：不需要对field进行分词，不分词不代表不索引，而是将整个内容进行索引

2. 是否索引

   是：将分好的词进行索引，索引的目的，就是为了搜索

   否：不索引，不对该field域进行搜索

3. 是否存储

   是：将field域中的内容存储到文档域中，存储的目的，为了搜索页面显示取值用

   否：不存储到文档域中，不存储，则搜索页面中没法获取该值

Field常用类型：

略

---

索引维护：

增加索引：

​	IndexWriter.addDocument(document);

删除索引：

​	增删改操作，都是要通过IndexWriter对象来操作

​	term是索引域中最小的单位。

```java
@Test
public void deleteIndex() throws IOException {
    Analyzer analyzer = new StandardAnalyzer();

    IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
    Path path = Paths.get("C:\\\\Users\\\\Thornhill\\\\Desktop\\\\FTS\\\\index\\\\luceneTest");
    Directory directory = FSDirectory.open(path);
    IndexWriter indexWriter = new IndexWriter(directory, cfg);
    //
    indexWriter.deleteDocuments(new Term("id","139"));
    indexWriter.close();
}
```

​	删除全部直接使用writer.deleteAll();（慎用）

修改索引:

```java
@Test
public void updateIndex() throws IOException {
    Analyzer analyzer = new StandardAnalyzer();

    IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
    Path path = Paths.get("C:\\\\Users\\\\Thornhill\\\\Desktop\\\\FTS\\\\index\\\\luceneTest");
    Directory directory = FSDirectory.open(path);
    IndexWriter indexWriter = new IndexWriter(directory, cfg);
    Document doc = new Document();
    doc.add(new TextField("id", "1",Store.YES));
    indexWriter.updateDocument(new Term("id","140"), doc);
    indexWriter.close();
}
```



​	