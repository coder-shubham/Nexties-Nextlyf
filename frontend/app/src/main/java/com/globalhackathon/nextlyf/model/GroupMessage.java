package com.globalhackathon.nextlyf.model;

public class GroupMessage {
        private String text;
        private String type;

        public GroupMessage(String text, String type) {
            this.text = text;
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public String getType() {
            return type;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setType(String type) {
            this.type = type;
        }

}
