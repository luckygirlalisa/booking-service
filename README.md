### Test

1. 运行`/gradlew build`执行所有测试和验证。(UT + apiTest + lint)

### Getting Started

1. 运行`docker-compose`命令启动订餐服务依赖的其他进程。包括 mongodb, kafka, mockApi  
   ```docker-compose -f docker-compose-for-local.yml up -d```
2. 运行```./gradlew bootRun```启动差旅预订服务。  
