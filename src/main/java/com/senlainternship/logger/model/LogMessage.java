package com.senlainternship.logger.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
@ToString
public class LogMessage {

    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

    private String time;

    private String executor;

    private String type;

    private String body;

    public static LogMessage of(String executor, String body, String type) {
        LogMessage logMessage = new LogMessage();
        logMessage.setTime(LocalDateTime.now().format(format));
        logMessage.setExecutor(executor);
        logMessage.setType(type);
        logMessage.setBody(body);
        return logMessage;
    }
}
