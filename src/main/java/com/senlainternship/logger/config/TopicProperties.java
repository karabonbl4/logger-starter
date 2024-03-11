package com.senlainternship.logger.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicProperties {

    /**
     * Name for message topic
     */
    private String message;

    /**
     * Name for exception topic
     */
    private String exception;
}
