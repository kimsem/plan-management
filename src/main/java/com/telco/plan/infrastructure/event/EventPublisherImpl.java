package com.telco.plan.infrastructure.event;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Azure Service Bus를 사용하여 이벤트를 발행하는 구현체입니다.
 */
@Slf4j
@Component
public class EventPublisherImpl implements EventPublisher {
    
    private final ServiceBusSenderClient sender;
    private final ObjectMapper objectMapper;
    
    public EventPublisherImpl(
            @Value("${azure.servicebus.connection-string}") String connectionString,
            @Value("${azure.servicebus.queue-name}") String queueName,
            ObjectMapper objectMapper) {
        this.sender = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .queueName(queueName)
                .buildClient();
        this.objectMapper = objectMapper;
    }
    
    @Override
    public void publishPlanChanged(PlanChangedEvent event) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(event);
            sender.sendMessage(new ServiceBusMessage(jsonMessage));
            log.info("요금제 변경 이벤트가 발행되었습니다. event: {}", event);
        } catch (Exception e) {
            log.error("요금제 변경 이벤트 발행 중 오류가 발생했습니다.", e);
            throw new RuntimeException("이벤트 발행 실패", e);
        }
    }
    
    @Override
    public void publishPlanReserved(PlanReservedEvent event) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(event);
            sender.sendMessage(new ServiceBusMessage(jsonMessage));
            log.info("요금제 예약 이벤트가 발행되었습니다. event: {}", event);
        } catch (Exception e) {
            log.error("요금제 예약 이벤트 발행 중 오류가 발생했습니다.", e);
            throw new RuntimeException("이벤트 발행 실패", e);
        }
    }
}
