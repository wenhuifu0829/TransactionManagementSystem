Transaction Management System
一个基于 Spring Boot 的银行交易管理系统，提供完整的交易 CRUD 操作、数据持久化、缓存优化和 RESTful API。

📦 项目结构
transaction-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/transactionmanagement/
│   │   │       ├── entity/ # 数据实体类
│   │   │       ├── service/ # 业务逻辑层
│   │   │       ├── controller/ # Web 控制层
│   │   │       ├── exception/ # 异常处理
│   │   │       ├── data/ # 数据初始化
│   │   │       │   └── TransactionManagementApplication.java # 启动类
│   │   │       └── resources/
│   │   ├── config/ # 配置类
│   │   ├── static/ # 静态资源 (Web 界面)
│   │   ├── application.properties # 应用配置
│   │   └── test/ # 测试代码
│   └── target/ # 构建输出目录
├── data/ # H2 数据库文件 (自动生成)
└── pom.xml # Maven 配置
🛠️ 技术栈
Java 17: 编程语言
Spring Boot 3.2.x: Web 框架
Spring Data JPA: 数据持久层
H2 Database: 嵌入式数据库
Caffeine: 缓存实现
Maven: 项目构建工具
Spring Boot Actuator: 应用监控
🌟 功能特性
交易管理: 创建、查看、更新、删除金融交易记录
数据持久化: 使用 H2 文件数据库，重启应用数据不丢失
RESTful API: 提供完整的 API 接口，支持各种查询操作
数据验证: 全面的输入验证和异常处理
缓存优化: 使用 Caffeine 缓存提升性能
分页查询: 支持大数据集的分页显示
Web 界面: 简洁的 Web 界面便于操作
监控端点: 集成 Spring Boot Actuator 用于应用监控
🚀 快速开始
前提条件
Java 17 或更高版本
Maven 3.6 或更高版本
步骤
克隆项目
git clone https://github.com/your-username/transaction-management.git
cd transaction-management
使用 Maven 构建项目
mvn clean package
运行应用程序
java -jar target/transaction-management-0.0.1-SNAPSHOT.jar

访问应用
Web 界面: http://localhost:8080
H2 数据库控制台: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:file:./data/transactiondb
用户名: sa
密码: password
Actuator 端点:
健康检查: http://localhost:8080/actuator/health
应用信息: http://localhost:8080/actuator/info
性能指标: http://localhost:8080/actuator/metrics
📖 API 文档
基础 URL
所有 API 端点都以 /api/transactions 为前缀。

端点列表
创建交易

方法: POST /api/transactions

请求体:{
  "amount": 100.50,
  "type": "DEPOSIT",
  "description": "Salary",
  "category": "INCOME"
}
响应: 201 Created + 创建的交易数据
