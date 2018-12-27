package com.powderblue.manager.controller;

import com.powderblue.entity.Product;
import com.powderblue.entity.enums.ProductStatus;
import com.powderblue.manager.repositories.ProductRepository;
import com.powderblue.util.RestUtil;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lkw
 * 功能测试
 * 自动化测试
 * TODO jackson BUG -时间日期格式:待修改
 *  generate-ddl: true
 *     hibernate:
 *       ddl-auto: create-drop
 * jdbc:h2:mem:manager;DB_CLOSE_ON_EXIT=FALSE
 *  Lambda表达式
 *  TODO h2内存数据库报错, 无法执行添加
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest {

    private static RestTemplate rest = new RestTemplate();

    @Value("http://localhost:${local.server.port}/manager")
    private String baseUrl;

    // 正常数据
    private static List<Product> normals = new ArrayList();

    // 异常数据
    private static List<Product> exceptions = new ArrayList<>();

    @Autowired
    private ProductRepository productRepository;

    @BeforeClass
    public static void init() {
        Product p1 = new Product("T001", "灵活宝1号", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(3.42));
        Product p2 = new Product("T002", "活期盈-金色人生", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(0), BigDecimal.valueOf(3.28));
        Product p3 = new Product("T003", "朝朝盈-聚财", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(3.86));
        normals.add(p1);
        normals.add(p2);
        normals.add(p3);

        Product e1 = new Product(null, "编号不可为空", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(2.34));
        Product e2 = new Product("E002", "收益率范围错误", ProductStatus.AUDITING.name(),
                BigDecimal.ZERO, BigDecimal.valueOf(1), BigDecimal.valueOf(31));
        Product e3 = new Product("E003", "投资步长需为整数", ProductStatus.AUDITING.name(),
                BigDecimal.ZERO, BigDecimal.valueOf(1.01), BigDecimal.valueOf(3.44));

        exceptions.add(e1);
        exceptions.add(e2);
        exceptions.add(e3);

        // 自定义错误,默认没有错误,继续执行
        ResponseErrorHandler errorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
            }
        };
        rest.setErrorHandler(errorHandler);
    }
    @Test
//    @Transactional
    public void a_create(){
        normals.forEach(product -> {
            Product result = RestUtil.postJSON(rest, baseUrl + "/products", product, Product.class);
            Assert.notNull(result.getCreateAt(), "插入失败");
        });
//        normals.forEach(x -> System.out.println(x));
    }

    @Test
    public void b_createException(){
        exceptions.forEach(product -> {
            Product result = RestUtil.postJSON(rest, baseUrl + "/products", product, Product.class);
        });
    }

    @Test
    public  void c_findOne(){
        normals.forEach(x -> {
            Product result = rest.getForObject(baseUrl + "/products/" + x.getId(), Product.class);
            Assert.isTrue(result.getId().equals(x.getId()), "查询失败");
        });
        exceptions.forEach(x -> {
            Product result = rest.getForObject(baseUrl + "/products/" + x.getId(), Product.class);
            Assert.isNull(result, "查询成功");
        });
    }
    @Test
    @Transactional
    public void d_transaction(){
        normals.forEach(x -> {
            x.setLockTerm(0);
            productRepository.saveAndFlush(x);
        });
    }

    @Test
    public void z_clean(){
        productRepository.delete(normals);
    }


// TODO 多条件查询测试

}
