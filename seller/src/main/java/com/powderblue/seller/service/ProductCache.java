package com.powderblue.seller.service;

import com.hazelcast.core.HazelcastInstance;
import com.powderblue.api.ProductRpc;
import com.powderblue.api.domain.ProductRpcReq;
import com.powderblue.entity.Product;
import com.powderblue.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品缓存
 */
@Component
public class ProductCache {
    private static Logger LOG = LoggerFactory.getLogger(ProductCache.class);
    static final String CACHE_NAME = "imooc_product";

    @Resource
    private ProductRpc productRpc;

    @Resource
    private HazelcastInstance hazelcastInstance;

    /**
     * 读取缓存
     * @param id
     * @return
     */
    @Cacheable(cacheNames = CACHE_NAME)
    public Product readCache(String id){
        LOG.info("rpc查询产品详情,请求:{}", id);
        Product result = productRpc.findOne(id);
        LOG.info("rpc查询产品详情,结果:{}", result);
        return result;
    }

    public List<Product> readAllCache(){
        Map<Object, Product> map = hazelcastInstance.getMap(CACHE_NAME);
        List<Product> products = null;
        if(map.size() > 0){
            products = new ArrayList<>();
            products.addAll(map.values());
        }else {
            products = findAll();
        }
        return  products;
    }

    public List<Product> findAll(){
        ProductRpcReq req = new ProductRpcReq();
        List<String> status = new ArrayList<>();
        status.add(ProductStatus.IN_SELL.name());
        req.setStatusList(status);
        LOG.info("rpc查询全部产品,请求{}", req);
        List<Product> result = productRpc.query(req);
        LOG.info("rpc查询全部产品,结果{}", result);
        return result;
    }

    @CachePut(cacheNames = CACHE_NAME, key = "#product.id")
    public Product putCache(Product product){
        return product;
    }

    @CacheEvict(cacheNames = CACHE_NAME)
    public void removeCache(String id){

    }
}
