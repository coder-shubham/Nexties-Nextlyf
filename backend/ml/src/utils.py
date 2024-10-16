import requests
import json
import os
from .env import OPENAI_API_KEY

url = "https://api.openai.com/v1/chat/completions"
model = "gpt-3.5-turbo"

def generate_response(system_message, user_message):
    payload = json.dumps({
        "model": model,
        "messages": [
            {
                "role": "system",
                "content": system_message
            },
            {
                "role": "user",
                "content": user_message
            }
        ],
        "temperature": 0.5,
        "seed": 22
    })
    headers = {
        'Content-Type': 'application/json',
        'Authorization': OPENAI_API_KEY #os.environ['OPENAI_API_KEY']
    }

    response = requests.request("POST", url, headers=headers, data=payload)
    response = json.loads(response.text)

    return response['choices'][0]['message']['content']
