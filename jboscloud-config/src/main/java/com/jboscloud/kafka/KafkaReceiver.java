package com.jboscloud.kafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Component
public class KafkaReceiver {
    private Log log= LogFactory.getLog(KafkaReceiver.class);
    @KafkaListener(topics = {"springCloudBus"})
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("------------------record="+record);
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("----------------- record =" + record);
            log.info("------------------ message =" + message);
        }
    }
}
