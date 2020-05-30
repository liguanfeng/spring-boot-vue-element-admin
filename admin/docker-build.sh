docker build --build-arg JAR_FILE=target/admin.jar -t app/admin -f ../Dockerfile .
#docker exec -it mysql /bin/bash 进入容器
# docker logs -f --tail=100 admin 查看日志
# docker inspect admin   查看容器信息

