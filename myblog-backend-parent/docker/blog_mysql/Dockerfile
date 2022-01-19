FROM mysql:8.0.27
#将初始化数据库命令放入这个文件夹后在初始化后自动执行query
COPY initial_db.sql /docker-entrypoint-initdb.d/

