# clientapi
Client API create large intense volume of bank transaction.
Steps to test data pipeline system:

1. Set up environment with docker file:
. Create docker image from the Docker file - docker build -t pipe_data_ubuntu_pg_java_container .
. Run the docker images and create from it docker container - 
RUN docker container 
	docker run -d --name data_pipe -p 5432:5432 -p 6379:6379 pipe_data_ubuntu_pg_java_container

2.Create table "transactions":
CREATE TABLE transactions (
    trans_id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    country VARCHAR(100),
    latitude_coord DOUBLE PRECISION,
    longitude_coord DOUBLE PRECISION
);

2. Run "fraudengine" application.
3. Run "clientapi" application.
4. Create a collection in Postman with this configuration.
Postman params:
Get request with url http://localhost:8080/api/loop/start?url=https://url&totalRequests=10&delayMillis=2000
Query params:
url - http://localhost:8080/api/transactions
totalRequests - 100
delayMillis - 2000

Send request from Postman and observe the result in Redis and PostgreSQL.
