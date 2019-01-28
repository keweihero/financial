package com.powderblue.manager.service;

import com.powderblue.api.events.ProductStatusEvent;
import com.powderblue.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
@Component
public class ProductStatusManager {
    static final String MQ_DESTINATION = "VirtualTopic.PRODUCT_STATUS";

    @Resource
    private JmsTemplate jmsTemplate;

    private static Logger LOG = LoggerFactory.getLogger(ProductStatusManager.class);

    public void changeStatus(String id, ProductStatus status){
        ProductStatusEvent event = new ProductStatusEvent(id, status);
        LOG.info("sendMessage:{}", event);
        jmsTemplate.convertAndSend(MQ_DESTINATION, event);
    }

    @PostConstruct
    public void init(){
        changeStatus("001", ProductStatus.IN_SELL);
    }
}