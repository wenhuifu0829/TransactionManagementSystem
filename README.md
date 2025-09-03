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
