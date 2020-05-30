docker restart mysql || \
docker run --name mysql -p 3306:3306 -v /Users/lgf/Documents/dev/docker/mysql:/var/lib/mysql  -e MYSQL_ROOT_PASSWORD=root -d mysql
