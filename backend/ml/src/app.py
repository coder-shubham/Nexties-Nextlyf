from flask import Flask, request, jsonify

from utils import generate_response
from db import FirebaseDB

from speech_to_text.stt import transcribe_speech

app = Flask(__name__)

fire_db = FirebaseDB()

@app.route('/hello', methods=['GET'])
def hello():
    return jsonify({'message': 'Hello, World!'})

@app.route('/transcribe', methods=['POST'])
def transcribe():
    data = request.get_json()
    audio_path = data['audio_path']
    text = transcribe_speech(audio_path)
    return jsonify({'transcription': text})