from ensurepip import bootstrap
from flask import Flask
from flask import jsonify, request
from app.service.messageService import MessageService
from kafka import KafkaProducer
import json
import os
from app.service.Expense import Expense

app = Flask(__name__)
app.config.from_pyfile('config.py')

messageService = MessageService()
kafka_host = os.getenv('KAFKA_HOST', 'localhost')
kafka_port = os.getenv('KAFKA_PORT', '9092')
kafka_bootstrap_servers = f"{kafka_host}:{kafka_port}"
print("Kafka server is "+ kafka_bootstrap_servers)
print("\n")

producer = KafkaProducer(
    bootstrap_servers=kafka_bootstrap_servers,
    value_serializer=lambda v: json.dumps(v).encode('utf-8')
)

@app.route('/v1/ds/message', methods=['POST'])

def handle_message():
    message = request.json.get('message')

    user_id = request.headers.get('X-USER-ID')
    result = messageService.process_message(message)
    serialized_result= result.serialize();
    serialized_result["user_id"] = user_id
    producer.send('expense_service', serialized_result)

    return jsonify(serialized_result)


@app.route('/', methods=['GET'])
def handle_get():
    return "Hello World"


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=7070, debug=True)