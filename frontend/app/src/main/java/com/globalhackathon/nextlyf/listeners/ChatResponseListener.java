package com.globalhackathon.nextlyf.listeners;

public interface ChatResponseListener {
        public void onSuccess(String response);

        public void onFailure(String error);

}
