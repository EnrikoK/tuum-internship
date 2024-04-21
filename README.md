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

