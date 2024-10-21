import base64
import json
import logging
import numpy as np
import requests
from cfg import Cfg

def embeddings_api(text_list: list):
    # text = str(text)
    payload = json.dumps({
        "encoding_format": "base64",
        "input": text_list,
        "model": "null",
        "user": "null"
    })
    headers = {
        'Content-Type': 'application/json'
    }
    response = requests.request("POST", url=Cfg.embedding_model_url, headers=headers, data=payload)

    return response.text

def get_embeddings(text: str):
    response = embeddings_api(text_list=[text])
    # logging.info(f"Embedding response: {response}")
    response = json.loads(response)

    # logging.info(f"Embedding response: {response}")
    encoded_document_embedding = response['data'][0]['embedding']
    buffer = base64.b64decode(encoded_document_embedding)
    decoded_document_embedding = np.frombuffer(buffer, dtype=np.float32)
    return decoded_document_embedding