Transaction Management System
一个基于 Spring Boot 的银行交易管理系统，提供完整的交易 CRUD 操作、数据持久化、缓存优化和 RESTful API。

🌟 功能特性
交易管理: 创建、查看、更新、删除金融交易记录

数据持久化: 使用 H2 文件数据库，重启应用数据不丢失

RESTful API: 提供完整的 API 接口，支持各种查询操作

数据验证: 全面的输入验证和异常处理

缓存优化: 使用 Caffeine 缓存提升性能

分页查询: 支持大数据集的分页显示

Web 界面: 简洁的 Web 界面便于操作

监控端点: 集成 Spring Boot Actuator 用于应用监控

🛠️ 技术栈
Java 17: 编程语言

Spring Boot 3.2.x: Web 框架

Spring Data JPA: 数据持久层

H2 Database: 嵌入式数据库

Caffeine: 缓存实现

Maven: 项目构建工具

Spring Boot Actuator: 应用监控

📦 项目结构
transaction-management/
├── src/
│ ├── main/
│ │ ├── java/com/example/transactionmanagement/
│ │ │ ├── entity/ # 数据实体类
│ │ │ ├── repository/ # 数据访问层
│ │ │ ├── service/ # 业务逻辑层
│ │ │ ├── controller/ # Web控制层
│ │ │ ├── exception/ # 异常处理
│ │ │ ├── config/ # 配置类
│ │ │ ├── data/ # 数据初始化
│ │ │ └── TransactionManagementApplication.java # 启动类
│ │ └── resources/
│ │ ├── static/ # 静态资源(Web界面)
│ │ ├── application.properties # 应用配置
│ │ └── ...
│ └── test/ # 测试代码
├── target/ # 构建输出目录
├── data/ # H2数据库文件(自动生成)
├── pom.xml # Maven配置
└── README.md # 项目说明

🚀 快速开始
前提条件
Java 17 或更高版本

Maven 3.6 或更高版本

1. 克隆项目
   bash
   git clone https://github.com/your-username/transaction-management.git
   cd transaction-management
2. 使用Maven构建项目
   # 使用系统Maven
   mvn clean package
3. 运行应用程序
   # 运行打包后的JAR文件
   java -jar target/transaction-management-0.0.1-SNAPSHOT.jar
4. 访问应用
   应用启动后，可以通过以下方式访问：

   Web界面: http://localhost:8080

   H2数据库控制台: http://localhost:8080/h2-console

   JDBC URL: jdbc:h2:file:./data/transactiondb

   用户名: sa

   密码: password

   Actuator端点:

   健康检查: http://localhost:8080/actuator/health

   应用信息: http://localhost:8080/actuator/info

   性能指标: http://localhost:8080/actuator/metrics
   
📖 API 文档
   基础URL
   所有API端点都以 /api/transactions 为前缀

端点列表

1. 创建交易
   方法: POST /api/transactions

请求体:

json
{
"amount": 100.50,
"type": "DEPOSIT",
"description": "Salary",
"category": "INCOME"
}
响应: 201 Created + 创建的交易数据

2. 获取所有交易(分页)
   方法: GET /api/transactions

查询参数:

page (可选, 默认: 0) - 页码

size (可选, 默认: 10) - 每页大小

sortBy (可选, 默认: "timestamp") - 排序字段

direction (可选, 默认: "desc") - 排序方向(asc/desc)

响应: 200 OK + 分页的交易列表

3. 根据ID获取交易
   方法: GET /api/transactions/{id}

响应: 200 OK + 交易数据 或 404 Not Found

4. 根据交易ID获取交易
   方法: GET /api/transactions/transactionId/{transactionId}

响应: 200 OK + 交易数据 或 404 Not Found

5. 更新交易
   方法: PUT /api/transactions/{id}

请求体: 同创建交易

响应: 200 OK + 更新后的交易数据

6. 删除交易
   方法: DELETE /api/transactions/{id}

响应: 204 No Content 或 404 Not Found

7. 按类型筛选交易
   方法: GET /api/transactions/type/{type}

查询参数: page, size (同获取所有交易)

响应: 200 OK + 分页的交易列表

8. 按分类筛选交易
   方法: GET /api/transactions/category/{category}

查询参数: page, size (同获取所有交易)

响应: 200 OK + 分页的交易列表

9. 按金额范围筛选交易
   方法: GET /api/transactions/amount-range

查询参数:

minAmount - 最小金额

maxAmount - 最大金额

page, size (同获取所有交易)

响应: 200 OK + 分页的交易列表

🧪 测试
运行测试
bash

# 运行所有测试

mvn test

# 运行单元测试

mvn test -Dtest=*UnitTest

# 运行集成测试

mvn test -Dtest=*IntegrationTest

# 运行特定测试类

mvn test -Dtest=TransactionServiceTest
压力测试
您可以使用 Apache JMeter 或 curl 进行压力测试:

bash

# 使用curl进行简单压力测试(创建100个交易)

for i in {1..100}; do
curl -X POST http://localhost:8080/api/transactions \
-H "Content-Type: application/json" \
-d '{"amount":'$((RANDOM % 1000))',"type":"DEPOSIT","description":"Test '$i'","category":"TEST"}'
done

# 使用Apache Bench进行并发测试

ab -n 1000 -c 100 http://localhost:8080/api/transactions?page=0&size=10
⚙️ 配置说明
应用配置(application.properties)
properties

# 服务器端口

server.port=8080

# H2数据库配置(文件模式)

spring.datasource.url=jdbc:h2:file:./data/transactiondb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password

# JPA配置

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2控制台

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# 缓存配置

spring.cache.type=caffeine
spring.cache.caffeine.spec=maximumSize=500,expireAfterWrite=600s

# Actuator配置

management.endpoints.web.exposure.include=health,metrics,info
自定义配置
您可以通过环境变量或命令行参数覆盖默认配置:

bash

# 更改服务器端口

java -jar target/transaction-management-0.0.1-SNAPSHOT.jar --server.port=9090

# 使用内存数据库(替代文件数据库)

java -jar target/transaction-management-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:h2:mem:testdb

# 禁用H2控制台

java -jar target/transaction-management-0.0.1-SNAPSHOT.jar --spring.h2.console.enabled=false


🐳 Docker 支持
项目支持 Docker 容器化部署:

构建 Docker 镜像
bash

# 构建镜像

docker build -t transaction-management .

# 运行容器

docker run -p 8080:8080 transaction-management
使用 Docker Compose
bash

# 使用docker-compose启动

docker-compose up -d

# 停止服务

docker-compose down
