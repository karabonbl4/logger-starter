package com.senlainternship.logger.model;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class KafkaLoggerConstant {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

    public static final String ARGS = "ARGS";

    public static final String RETURNS = "RETURNS";

    public static final String WARNING = "WARNING";
}
