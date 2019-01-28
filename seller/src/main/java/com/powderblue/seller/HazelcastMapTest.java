package com.powderblue.seller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class HazelcastMapTest {
    @Resource
    private HazelcastInstance hazelcastInstance;

//    @PostConstruct
    public void put(){
        IMap<Object, Object> map = hazelcastInstance.getMap("imooc");
        map.put("name", "imooc1");
    }
}
