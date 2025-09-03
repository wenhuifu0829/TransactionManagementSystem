# Transaction Management System

一个基于 Spring Boot 的银行交易管理系统，提供完整的交易 CRUD 操作、数据持久化、缓存优化和 RESTful API。

## 功能特性

- **交易管理**: 创建、查看、更新、删除金融交易记录
- **数据持久化**: 使用 H2 文件数据库，重启应用数据不丢失
- **RESTful API**: 提供完整的 API 接口，支持各种查询操作
- **分页查询**: 支持大数据集的分页显示
- **Web 界面**: 简洁的 Web 界面便于操作
- **监控端点**: 集成 Spring Boot Actuator 用于应用监控
- **数据验证**: 全面的输入验证和异常处理
- **缓存优化**: 使用 Caffeine 缓存提升性能

## 技术栈

- **Java 17**: 编程语言
- **Spring Boot 3.2.x**: Web 框架
- **Spring Data JPA**: 数据持久层
- **H2 Database**: 嵌入式数据库
- **Caffeine**: 缓存实现
- **Maven**: 项目构建工具
- **Spring Boot Actuator**: 应用监控

## 项目结构

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


## 快速开始

### 前提条件

- Java 17 或更高版本
- Maven 3.6 或更高版本

### 1. 克隆项目

```bash
git clone https://github.com/your-username/transaction-management.git
cd transaction-management

2. 使用Maven构建项目
bash
# 使用系统Maven
mvn clean package

# 或使用项目自带的Maven Wrapper(跨平台)
./mvnw clean package   # Linux/Mac
mvnw.cmd clean package # Windows

bash
# 运行打包后的JAR文件
java -jar target/transaction-management-0.0.1-SNAPSHOT.jar

# 或使用Maven直接运行
mvn spring-boot:run

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

docker build -t transaction-management .
docker run -p 8080:8080 transaction-management