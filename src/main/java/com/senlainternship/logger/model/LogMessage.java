package com.senlainternship.logger.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.aspectj.lang.JoinPoint;

import java.time.LocalDateTime;

import static com.senlainternship.logger.model.KafkaLoggerConstant.DATE_TIME_FORMATTER;

@Getter
@Setter
@ToString
public class LogMessage {

    private String time;

    private String executor;

    private String type;

    private String body;

    public static LogMessage of(JoinPoint joinPoint, String body, String type) {
        LogMessage logMessage = new LogMessage();
        logMessage.setTime(LocalDateTime.now().format(DATE_TIME_FORMATTER));
        logMessage.setExecutor(joinPoint.getStaticPart().getSignature().toString());
        logMessage.setType(type);
        logMessage.setBody(body);
        return logMessage;
    }
}
