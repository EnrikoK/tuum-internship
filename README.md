# Summer intertnship test assignment
A sample banking application using REST APIs and RabbitMQ. The queuePorducer folder contains the API endpoints and logic for handling API requests and sending them to the rabbitMQ message queue. 
QueueReceiver folder contains a spring boot application that handles the update and insert requests coming from rabbitMQ queues. 

## How to run it
Navigate to the project folder and run (for windows):

```
docker-compose up --build
```

This should buld 4 containers for queueProducer, queueReceiver, PostgreSQL database and RabbitMQ server.

## API documentation

### /api/account/

To create a new account with empty balances

[POST] http://localhost:8080/api/account/


Request body example
```
{
	"customerId":"test",
	"country":"EST",
	"currencies":["USD","EUR"]
}
```

Response example
```
{
	"accountId": 2,
	"customerId": "test",
	"balances": [
		{
			"amount": 0.0,
			"currency": "USD"
		},
		{
			"amount": 0.0,
			"currency": "EUR"
		}
	]
}
```


Get account balance using account id

[GET]  http://localhost:8080/api/account/?id=2

Response example

```
{
	"accountId": 2,
	"customerId": "test",
	"balances": [
		{
			"amount": 0.0,
			"currency": "EUR"
		},
		{
			"amount": 190.0,
			"currency": "USD"
		}
	]
}
```

### /api/transaction

Create a new transaction

[POST] http://localhost:8080/api/transaction/

Request body example

```
{
	"accountId":2,
  	"amount":9.5,
	"currency":"USD",
	"direction":"OUT",
	"description":"test"
}
```
Response example

```
{
	"accountId": 2,
	"transactionId": 5,
	"amount": 9.5,
	"currency": "USD",
	"direction": "OUT",
	"description": "test",
	"balance": 190.0
}
```


Get all user transactions using account id

[GET] http://localhost:8080/api/transaction/?id=2

Response example

```
[
	{
		"transactionId": 4,
		"accountId": 2,
		"amount": 199,
		"currency": "USD",
		"direction": "IN",
		"description": "test"
	},
	{
		"transactionId": 5,
		"accountId": 2,
		"amount": 9,
		"currency": "USD",
		"direction": "OUT",
		"description": "test"
	}
]
```

### About the solution
The most difficult part for me was figuring out the process using RabbitMQ as it was my first time using a messaging queue in an application. I decided to use the remote procedure call feature of rabbitMQ so that when the queueProducer submits a new request, then a response could be sent back from the queueListener that consumes the messages. Simple get request I implemented in the queueProducer part of the API, because the database only gets read and I think that sending that request through a message queue would just make the response time longer.


I did not manage to write any tests for the application, because I don't know how to test applications using message queues yet and I didn't have the time to figure it out.

Also I do not know how many request could this application handle per second on a good machine, but on my own machine that was running 4 docker containers at the same time, a single request took from 50ms up to 14 seconds. But assuming that all request take about 50ms then about 20 request per second. For horizontal scaling one could concider launching multiple queueReceivers that would listen to the same message queue and handle the update and insert requests.


In conclusion, i gave it my best and learned a lot! I think if I would have had more time, then i would have implemented better input checks and error handling on the queueReceiver side. At the time the queueReceiver just returns null if an input is invalid and assumes that queueProducer does all the needed data validation and sends valid data to the queue.
