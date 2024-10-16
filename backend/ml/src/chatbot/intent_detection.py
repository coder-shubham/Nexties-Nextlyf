import re

intents = {
    'greeting': re.compile(r'\b(hi|hello|hey|greetings)\b'),
    'goodbye': re.compile(r'\b(bye|goodbye|see you later)\b'),
    'thanks': re.compile(r'\b(thanks|thank you)\b'),
    'book_room': re.compile(r'\b(book|reserve|schedule|get|booked|bookings|booking)\b.*?\b(room)\b|\b(room)\b.*?\b(book|reserve|schedule|get|booked|bookings|booking)\b'),
    'cancel_room': re.compile(r'\b(cancel|remove|delete|cancelation|cancellation)\b.*?\b(room)\b|\b(room)\b.*?\b(cancel|remove|delete|cancelation|cancellation)\b'),
    'book_shared_space': re.compile(r'\b(book|reserve|schedule|get|booked|bookings|booking)\b.*?\b(shared space)\b|\b(shared space)\b.*?\b(book|reserve|schedule|get|booked|bookings|booking)\b'),
    'cancel_shared_space': re.compile(r'\b(cancel|remove|delete|cancelation|cancellation)\b.*?\b(shared space)\b|\b(shared space)\b.*?\b(cancel|remove|delete|cancelation|cancellation)\b'),
    'book_event': re.compile(r'\b(book|reserve|schedule|get|booked|bookings|booking)\b.*?\b(event)\b|\b(event)\b.*?\b(book|reserve|schedule|get|booked|bookings|booking)\b'),
    'cancel_event': re.compile(r'\b(cancel|remove|delete|cancelation|cancellation)\b.*?\b(event)\b|\b(event)\b.*?\b(cancel|remove|delete|cancelation|cancellation)\b'),
}

fallback_intent = 'fallback'

def get_heuristic_intent(text):
    for intent, pattern in intents.items():
        if pattern.search(text):
            return intent
    return fallback_intent