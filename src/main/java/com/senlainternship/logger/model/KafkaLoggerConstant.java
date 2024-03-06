package com.senlainternship.logger.model;

import java.time.format.DateTimeFormatter;

public interface KafkaLoggerConstant {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

    String ARGS = "ARGS";

    String RETURNS = "RETURNS";

    String WARNING = "WARNING";
}
