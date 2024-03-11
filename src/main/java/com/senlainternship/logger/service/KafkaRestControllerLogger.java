package com.senlainternship.logger.service;


import com.senlainternship.logger.config.KafkaProducer;
import com.senlainternship.logger.model.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Slf4j
@RequiredArgsConstructor
public class KafkaRestControllerLogger {

    public static final String ARGS = "ARGS";

    public static final String RETURNS = "RETURNS";

    public static final String WARNING = "WARNING";

    private final KafkaProducer kafkaProducer;

    @Pointcut("@annotation(com.senlainternship.logger.annotation.LoggableMessage)")
    public void callAtBookControllerPublic() {
    }

    @Pointcut("@annotation(com.senlainternship.logger.annotation.LoggableException)")
    public void callAtExceptionHandlerPublic() {
    }

    @Before("callAtBookControllerPublic()")
    public void sendRequiredArguments(JoinPoint joinPoint) {
        String name = Arrays.stream(joinPoint.getArgs()).map(arg -> arg.getClass().getSimpleName() + ": " + arg).collect(Collectors.joining(", "));
        String executor = joinPoint.getStaticPart().getSignature().toString();
        LogMessage logMessage = LogMessage.of(executor, name, ARGS);
        log.debug(logMessage.toString());
        this.sendMessage(logMessage);
    }

    @AfterReturning(pointcut = "callAtBookControllerPublic()", returning = "retVal")
    public void sendMessage(JoinPoint joinPoint, Object retVal) {
        String s = retVal.toString();
        String executor = joinPoint.getStaticPart().getSignature().toString();
        LogMessage logMessage = LogMessage.of(executor, s, RETURNS);
        log.debug(logMessage.toString());
        this.sendMessage(logMessage);
    }

    @AfterReturning("callAtExceptionHandlerPublic()")
    public void sendException(JoinPoint joinPoint) {
        String eMessage = Arrays.stream(joinPoint.getArgs())
                .findFirst()
                .map(src -> ((Exception) src).getMessage())
                .orElse("Unknown exception!");
        String executor = joinPoint.getStaticPart().getSignature().toString();
        LogMessage logMessage = LogMessage.of(executor, eMessage, WARNING);
        log.debug(logMessage.toString());
        this.sendMessage(logMessage);
    }

    private void sendMessage(LogMessage message) {
        if (message.getType().equals(WARNING)) {
            kafkaProducer.sendException(message);
        } else {
            kafkaProducer.sendMessage(message);
        }
    }
}