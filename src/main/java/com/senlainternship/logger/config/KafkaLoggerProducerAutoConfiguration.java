package com.senlainternship.logger.config;

import com.senlainternship.logger.model.LogMessage;
import com.senlainternship.logger.service.KafkaRestControllerLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@EnableKafka
@EnableConfigurationProperties
public class KafkaLoggerProducerAutoConfiguration {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    @ConfigurationProperties(prefix = "spring.kafka.topic")
    public TopicProperties topicProperties(){
        return new TopicProperties();
    }

    @Bean
    public Map<String, Object> producerMessageConfig(){
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        log.info(String.format("KafkaProducer will start on %s", bootstrapServer));
        return properties;
    }

    @Bean
    public ProducerFactory<String, LogMessage> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerMessageConfig());
    }

    @Bean
    public KafkaTemplate<String, LogMessage> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaProducer kafkaProducer(KafkaTemplate<String, LogMessage> kafkaTemplate, TopicProperties topicProperties){
        return new KafkaProducer(kafkaTemplate, topicProperties.getMessage(), topicProperties.getException());
    }

    @Bean
    public KafkaRestControllerLogger restControllerLogger(KafkaProducer kafkaProducer){
        return new KafkaRestControllerLogger(kafkaProducer);
    }
}
