import gradio as gr
from intent_detection import get_heuristic_intent

def get_intent(text, history):
    return get_heuristic_intent(text)

gr.ChatInterface(get_intent, type="messages").launch(share=False)