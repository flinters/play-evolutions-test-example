FROM mysql:8

ENV MYSQL_ROOT_PASSWORD password

COPY create_database.sql /docker-entrypoint-initdb.d/create_database.sql
