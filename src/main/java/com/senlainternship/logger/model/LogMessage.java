package com.senlainternship.logger.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LogMessage {

    private String time;

    private String executor;

    private String type;

    private String body;
}
