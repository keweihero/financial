package com.powderblue.api.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lkw
 * 参数接口
 * 序列化
 */
@JsonDeserialize(as = ProductRpcReq.class)
public interface ParamInf {
    public List<String> getIdList();
    public BigDecimal getMinRewardRate();
    public BigDecimal getMaxRewardRate();
    public List<String> getStatusList();
}
