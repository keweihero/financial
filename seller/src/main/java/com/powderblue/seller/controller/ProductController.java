package com.powderblue.seller.controller;

import com.powderblue.entity.Product;
import com.powderblue.seller.service.ProductRpcService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 产品相关
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductRpcService productRpcService;

    @RequestMapping("/{id}")
    public Product findOne(@PathVariable String id){
        return productRpcService.findOne(id);
    }
}
