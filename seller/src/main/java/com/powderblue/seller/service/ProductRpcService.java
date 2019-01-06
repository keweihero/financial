package com.powderblue.seller.service;

import com.powderblue.api.ProductRpc;
import com.powderblue.api.domain.ProductRpcReq;
import com.powderblue.entity.Product;
import com.powderblue.entity.enums.ProductStatus;
import net.bytebuddy.description.type.TypeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lkw
 * 产品相关服务
 */
@Service
public class ProductRpcService {

    private static Logger LOG = LoggerFactory.getLogger(ProductRpcService.class);

    @Autowired
    private ProductRpc productRpc;

    /**
     * 查询全部产品
     * @return
     */
    public List<Product> findAll(){
        ProductRpcReq req = new ProductRpcReq();
        List<String> status = new ArrayList<>();
        status.add(ProductStatus.IN_SELL.name());
        req.setStatusList(status);
//        Pageable pageable = new PageRequest(0, 1000, Sort.Direction.DESC, "rewardRate");
        LOG.info("rpc查询全部产品,请求{}", req);
        List<Product> result = productRpc.query(req);
        LOG.info("rpc查询全部产品,结果{}", result);
        return result;
    }

    /**
     * 查询单个产品
     * @param id
     * @return
     */
    public Product findOne(String id){
        LOG.info("rpc查询产品详情,请求:{}", id);
        Product result = productRpc.findOne(id);
        LOG.info("rpc查询产品详情,结果:{}", result);
        return result;
    }
    @PostConstruct
    public void testFindAll(){
        findAll();
//        findOne("001");
    }


}
