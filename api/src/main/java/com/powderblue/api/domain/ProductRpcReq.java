package com.powderblue.api.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lkw
 * 相关产品请求对象
 */
public class ProductRpcReq implements ParamInf{
    /** 编号列表 */
    private List<String> idList;
    /** 最小收益率 */
    private BigDecimal minRewardRate;
    /** 最大收益率 */
    private BigDecimal maxRewardRate;
    /** 状态列表 */
    private List<String> statusList;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @Override
    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }
    @Override
    public BigDecimal getMinRewardRate() {
        return minRewardRate;
    }

    public void setMinRewardRate(BigDecimal minRewardRate) {
        this.minRewardRate = minRewardRate;
    }
    @Override
    public BigDecimal getMaxRewardRate() {
        return maxRewardRate;
    }

    public void setMaxRewardRate(BigDecimal maxRewardRate) {
        this.maxRewardRate = maxRewardRate;
    }
    @Override
    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

}
