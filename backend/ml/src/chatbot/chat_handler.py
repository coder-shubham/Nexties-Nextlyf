import ast
import json
import logging
from utils import generate_response
from prompts import chatbot_prompts
from chatbot.intent_detection import chatbot_intents

class SessionStore:
    def __init__(self):
        self.session_chat_history = {}
        self.session_user_details = {}

    def get_session_ids(self):
        return list(self.session_chat_history.keys())
    
    def get_chat_history(self, session_id):
        if session_id is None or session_id == "None":
            return []
        session_id = str(session_id.strip())
        if session_id == "":
            logging.debug(f"Chat history for all sessions {self.session_chat_history}")
            return self.session_chat_history
        
        elif session_id not in self.session_chat_history:
            self.session_chat_history[session_id] = []

        chat_history = self.session_chat_history.get(session_id)  #[session_id]
        logging.debug(f"Chat history for session {session_id}: {chat_history}")
        return chat_history

    def add_chat_history(self, session_id, message, **kwargs):
        session_id = str(session_id.strip())
        if session_id == "":
            return {"message": "Session ID not provided"}

        chat_history = self.get_chat_history(session_id)  # ["chat_history"]
        # if chat_history is None:
        #     self.session_chat_history[session_id] = []
        chat_history.append(message)
        logging.debug(f"Updated chat history for session {session_id}: {chat_history}")
        self.session_chat_history[session_id] = chat_history

    def clear_chat_history(self, session_id):
        session_id = str(session_id.strip())
        if session_id == "":
            self.session_chat_history = {}
            logging.info("Cleared chat history for all sessions")
            return {"message": "Cleared chat history for all sessions"}
        else:
            self.session_chat_history[session_id] = []
            logging.info(f"Cleared chat history for session id: {session_id}")
            return {"message": f"Cleared chat history for session {session_id}"}

    def get_user_details(self, session_id):
        if session_id is None or session_id == "None":
            return {}
        session_id = str(session_id.strip())
        if session_id == "":
            logging.debug(f"Chat history for all sessions {self.session_user_details}")
            return self.session_user_details
        
        elif session_id not in self.session_user_details:
            self.session_user_details[session_id] = {}

        user_details = self.session_user_details.get(session_id)  #[session_id]
        logging.debug(f"Chat history for session {session_id}: {user_details}")
        return user_details

    def add_user_details(self, session_id, user_details, **kwargs):
        session_id = str(session_id.strip())
        if session_id == "":
            return {"message": "Session ID not provided"}

        # user_details = self.get_user_details(session_id)  # ["user_details"]
        # if user_details is None:
        #     self.session_user_details[session_id] = []
        user_details = user_details
        logging.debug(f"Updated chat history for session {session_id}: {user_details}")
        self.session_user_details[session_id] = user_details

    def delete_session(self, session_id=""):
        session_id = str(session_id).strip()
        if session_id == "":
            self.session_chat_history = {}
            self.session_user_details = {}
            logging.info("Cleared chat history and user details for all sessions")
            return {"message": "Cleared chat history and user details for all sessions"}
        else:
            del self.session_chat_history[session_id]
            del self.session_user_details[session_id]
            # self.session_chat_history[session_id] = []
            logging.info(f"Cleared chat history and user details for session id: {session_id}")
            return {"message": f"Cleared chat history and user details for session {session_id}"}

class ChatHandler:
    def __init__(self, session_store, vector_db):
        self.session_store: SessionStore = session_store
        self.active_intent = None
        self.active_entities = None
        self.vector_db = vector_db
        self.response_weights = [0.4, 0.3, 0.2, 0.05, 0.05]

    def add_user_details(self, session_id, user_details):
        self.session_store.add_user_details(session_id, user_details)

    def validate_intent(self, intents):
        # check if first response is an FAQ
        if intents[0]['answer'] not in chatbot_intents.keys():
            faqs = []
            for obj in intents:
                intent = obj['answer']
                if intent not in chatbot_intents.keys():
                    faqs.append(intent)
            return False, faqs
        
        intent_weights = {}
        for ind, obj in enumerate(intents):
            intent = obj['answer']
            intent_weights[intent] = intent_weights.get(intent, 0) + self.response_weights[ind]
        
        # weigh intents
        weighted_intents = [k for k, v in sorted(intent_weights.items(), key=lambda item: item[1], reverse=True)]
        return True, weighted_intents[0]
    
    def extract_json(self, text):
        stack = []
        json_start = None

        for i, char in enumerate(text):
            if char == '{':
                if not stack:
                    json_start = i
                stack.append(char)
            elif char == '}':
                try:
                    stack.pop()
                except:
                    pass
                if not stack:
                    json_end = i + 1
                    json_str = text[json_start:json_end]
                    try:
                        # logging.info(f"fn: Extracted JSON: {json_str}")
                        json_data = json.loads(json_str)  # Optional: Validate the JSON
                        
                        return json_data
                    except json.JSONDecodeError:
                        try:
                            json_data = ast.literal_eval(json_str)
                            return json_data
                        except:
                            pass
                        # print(json_str)
                        # print(traceback.format_exc())
                        pass
        return None
    
    def entity_extraction(self, session_id, intent, user_message):
        # refresh chat history for the same session
        self.session_store.clear_chat_history(session_id)
        self.session_store.add_chat_history(session_id, message={"role": "system", "content": chatbot_prompts.intent_instruction(chatbot_intents[intent], ' '.join(intent.split("_")[1:]))})
        self.session_store.add_chat_history(session_id, message={"role": "user", "content": user_message})
        
        # extract entities
        entities_obj = None
        while entities_obj is None:
            response = generate_response(self.session_store.get_chat_history(session_id))
            self.session_store.add_chat_history(session_id, message=response['choices'][0]['message'])
            entities_obj_str = response['choices'][0]['message']['content']
            entities_obj = self.extract_json(entities_obj_str.replace(r"\\n", ""))
            logging.info(f"Extracted entities: {entities_obj}")

        if self.active_entities is None:
            self.active_entities = entities_obj
        else:
            # TODO: do not update the entities if already extracted
            self.active_entities.update(entities_obj)

        # validate the entities and check for missing value
        deletion_keys = []
        for key, value in self.active_entities.items():
            if value is None or str(value).lower() in ["", "value", "None", "none", "null"]:
                deletion_keys.append(key)
        
        for key in deletion_keys:
            del self.active_entities[key]

        # check for missing entities
        missing_entities = []
        for entity in chatbot_intents[intent]:
            if entity not in self.active_entities:
                self.active_entities[entity] = None
                missing_entities.append(entity)

        if missing_entities:
            logging.info(f"Missing entities: {missing_entities}")
            extracted_entities = ', '.join(set(self.active_entities.keys()) - set(missing_entities))
            if extracted_entities == "":
                extracted_entities = "nothing"
            # refresh chat history for the same session
            self.session_store.clear_chat_history(session_id)
            self.session_store.add_chat_history(session_id, message={"role": "system", "content": chatbot_prompts.missing_entities_instruction(missing_entities, ' '.join(intent.split("_")[1:]), extracted_entities)})
            self.session_store.add_chat_history(session_id, message={"role": "user", "content": user_message})
            
            # generate response to get the first empty entity
            response = generate_response(self.session_store.get_chat_history(session_id))
            self.session_store.add_chat_history(session_id, message=response['choices'][0]['message'])
            response = response['choices'][0]['message']['content']
            logging.info(f"Response based on missing entities: {response}")
            # entities_obj = self.extract_json(entities_obj_str)

        else:
            self.active_intent = None
            self.active_entities = None
            response = intent
            logging.info(f"Got all entities, final response: {response}")

        return response

    def chat_journey(self, session_id, user_message, new_session=False):
        if new_session:
            self.active_entities = None
        chat_history = self.session_store.get_chat_history(session_id)
        user_details = self.session_store.get_user_details(session_id)
        response = ""

        if len(chat_history) == 0:
            # Welcome message
            self.session_store.add_chat_history(session_id, message={"role": "system", "content": chatbot_prompts.start_instruction})
            self.session_store.add_chat_history(session_id, message={"role": "user", "content": chatbot_prompts.start_user_query(user_details)})

            # Generate response
            response = generate_response(self.session_store.get_chat_history(session_id))
            self.session_store.add_chat_history(session_id, message=response['choices'][0]['message'])
            response = response['choices'][0]['message']['content']

        else:
            # intent detection
            # check if active intent
            if self.active_intent:
                response = self.entity_extraction(session_id, self.active_intent, user_message)
            else:
                retrieved_intents = self.vector_db.get_similar(query=user_message, limit=5, alpha=0.5)
                logging.info(f"Retrieved intents: {retrieved_intents}")
                # validate intent
                intent_or_faq_object = self.validate_intent(retrieved_intents)
                if intent_or_faq_object[0]:
                    intent = intent_or_faq_object[1]
                    logging.info(f"Intent detected: {intent}")
                    self.active_intent = intent

                    response = self.entity_extraction(session_id, intent, user_message)

                    # response = intent
                else:
                    # faq
                    # create refreshed chat history for the same session
                    self.session_store.clear_chat_history(session_id)
                    self.session_store.add_chat_history(session_id, message={"role": "system", "content": chatbot_prompts.faq_instruction(intent_or_faq_object[1])})
                    self.session_store.add_chat_history(session_id, message={"role": "user", "content": user_message})
                    
                    # generate response
                    response = generate_response(self.session_store.get_chat_history(session_id))
                    self.session_store.add_chat_history(session_id, message=response['choices'][0]['message'])
                    response = response['choices'][0]['message']['content']
                    logging.info(f"FAQ response based on the faqs: {intent_or_faq_object[1]}")

        return response
