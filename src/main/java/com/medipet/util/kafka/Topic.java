package com.medipet.util.kafka;

public enum Topic {
    EMAIL_TOPIC("emailTopic");

    private final String name;

    private Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
