package com.powderblue.seller.service;

import com.powderblue.api.ProductRpc;
import com.powderblue.api.events.ProductStatusEvent;
import com.powderblue.entity.Product;
import com.powderblue.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lkw
 * 产品相关服务
 */
@Service
public class ProductRpcService implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger LOG = LoggerFactory.getLogger(ProductRpcService.class);

    static final String MQ_DESTINATION = "Consumer.cache.VirtualTopic.PRODUCT_STATUS";
    //                                                  VirtualTopic.PRODUCT_STATUS
    @Autowired
    private ProductRpc productRpc;

    @Resource
    private ProductCache productCache;
    /**
     * 查询全部产品
     * @return
     */
    public List<Product> findAll(){
        return productCache.readAllCache();
    }

    /**
     * 查询单个产品
     * @param id
     * @return
     */
    public Product findOne(String id){
        Product product = productCache.readCache(id);
        if (product == null){
            productCache.removeCache(id);
        }
        return product;
    }
//    @PostConstruct
    public void testFindAll(){
//        findAll();
//        findOne("001");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<Product> products = findAll();
        products.forEach(product -> {
            productCache.putCache(product);
        });
    }

    @JmsListener(destination = MQ_DESTINATION)
    void updateCache(ProductStatusEvent event){
        LOG.info("receive event:{}", event);
        productCache.removeCache(event.getId());
        if (ProductStatus.IN_SELL.equals(event.getStatus())){
            productCache.removeCache(event.getId());
        }
    }


}
