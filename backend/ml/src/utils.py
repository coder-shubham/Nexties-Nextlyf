import logging
import requests
import json
import os

OPENAI_API_KEY = os.environ.get('OPENAI_API_KEY')

url = "https://api.openai.com/v1/chat/completions"
model = "gpt-3.5-turbo" # "gpt-4o-mini"

def generate_response(messages): # system_message, user_message
    logging.info(f"Request to OpenAI: {messages}")
    payload = json.dumps({
        "model": model,
        "messages": messages,
        "temperature": 0.5,
        "seed": 22
    })
    headers = {
        'Content-Type': 'application/json',
        'Authorization': OPENAI_API_KEY #os.environ['OPENAI_API_KEY']
    }

    response = requests.request("POST", url, headers=headers, data=payload)
    response = json.loads(response.text)
    logging.info(f"Response from OpenAI: {response}")

    return response #['choices'][0]['message']['content']

        # [
        #     {
        #         "role": "system",
        #         "content": system_message
        #     },
        #     {
        #         "role": "user",
        #         "content": user_message
        #     }
        # ]
