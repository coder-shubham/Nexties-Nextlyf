import json
from flask import Flask, request, jsonify

# from utils import generate_response
# from db import FirebaseDB

# from speech_to_text.stt import transcribe_speech
from qa.vector_db import WeaviateDB
from cfg import Cfg
from qa.embeddings import get_embeddings
from chatbot.chat_handler import SessionStore, ChatHandler
from recommendation import recommendations
from community import sample_conversation

###### logger ######
import logging.handlers
import sys
import logging

root_logger = logging.getLogger()
root_logger.setLevel(logging.INFO)
# print to console
handler = logging.StreamHandler(sys.stdout)
handler.setFormatter(logging.Formatter(
        '%(levelname)s %(asctime)s line:%(lineno)d %(message)s'))
root_logger.addHandler(handler)
# print to file
# handler = logging.FileHandler('run.log')
# handler.setFormatter(logging.Formatter(
#         '%(levelname)s %(asctime)s line:%(lineno)d %(message)s'))
# root_logger.addHandler(handler)
logging.info("Logger initialized")

app = Flask(__name__)

# fire_db = FirebaseDB()

vector_db = WeaviateDB(
    collection_name=Cfg.vector_db_collection_name, 
    vectorizer_fn=get_embeddings, 
    host=Cfg.vector_db_host, 
    port=Cfg.vector_db_port,
)
vector_db.get_or_create_collection()

session_store = SessionStore()
chat_handler = ChatHandler(session_store, vector_db)

@app.route('/hello', methods=['GET'])
def hello():
    return jsonify({'message': 'Hello, World!'})

###### vector db ######
@app.route('/vector_db/create_collection', methods=['POST'])
def create_collection():
    vector_db.get_or_create_collection()
    return jsonify({"message": "Collection created successfully."})

@app.route('/vector_db/delete_collection', methods=['POST'])
def delete_collection():
    vector_db.drop_collection()
    return jsonify({"message": "Collection deleted successfully."})

@app.route('/vector_db/insert', methods=['POST'])
def insert():
    request_data = request.get_json()
    data = request_data['data']
    vector_db.insert(data=data)
    return jsonify({"message": "Data inserted successfully."})

@app.route('/vector_db/insert_bulk', methods=['POST'])
def insert_bulk():
    request_data = request.get_json()
    data = request_data['data']
    vector_db.insert_bulk(data=data)
    return jsonify({"message": "Data inserted successfully."})

@app.route('/vector_db/get_similar', methods=['POST'])
def get_similar():
    request_data: dict = request.get_json()
    query = request_data['query']
    limit = request_data.get('limit', 2)
    alpha = request_data.get('alpha', 0.5)
    response = vector_db.get_similar(query=query, limit=limit, alpha=alpha)
    return jsonify({"response": response})

###### chatbot ######
@app.route('/converse', methods=['POST'])
def converse():
    data: dict = request.get_json()
    session_id = data['session_id']

    user_details = data.get('user_details')
    new_session = data.get('new_session', False)
    if new_session:
        chat_handler.session_store.clear_chat_history(session_id)
        chat_handler.add_user_details(session_id, user_details)

    user_query = data.get('user_query', "")
    response = chat_handler.chat_journey(session_id, user_query, new_session)
    return jsonify({'response': response})

@app.route('/group_conversation', methods=['GET', 'POST'])
def group_conversation():
    return jsonify(sample_conversation.example_conversation)

###### recommendation ######
@app.route('/recommendation', methods=['POST'])
def recommendation():
    data: dict = request.get_json()
    user = data.get('user')
    recommendation_type = data.get('recommendation_type')
    logging.info(f"User: {user}, Recommendation type: {recommendation_type}")
    response = recommendations.recommend(user, recommendation_type)
    logging.info(f"Recommendations response: {response}")
    return jsonify({'recommendations': response})

###### transcription ######
# @app.route('/transcribe', methods=['POST'])
# def transcribe():
#     data: dict = request.get_json()
#     audio_path = data.get('audio_path')
#     audio_data = data.get('audio_data')
#     text = transcribe_speech(audio_path=audio_path, audio_data=audio_data)
#     return jsonify({'transcription': text})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)