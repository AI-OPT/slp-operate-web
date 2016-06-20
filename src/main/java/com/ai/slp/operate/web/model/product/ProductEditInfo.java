package com.ai.slp.operate.web.model.product;

import java.sql.Timestamp;

/**
 * 商品编辑信息提交信息
 * Created by jackieliu on 16/6/20.
 */
public class ProductEditInfo {

    private String prodId;

    private String prodName;
    private String productSellPoint;

    private String activeType;
    private Short activeCycle;
    private String unit;

    private String proDetailContent;

    private String isSaleNationwide;

    private String isReplaceSell;

    private String upshelfType;

    private Timestamp upTime;

    private String rechargeType;

    private String basicOrgId;

    private String audiencesPerson;

    private String audiencesEnterprise;

    private String audiencesAgents;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProductSellPoint() {
        return productSellPoint;
    }

    public void setProductSellPoint(String productSellPoint) {
        this.productSellPoint = productSellPoint;
    }

    public String getActiveType() {
        return activeType;
    }

    public void setActiveType(String activeType) {
        this.activeType = activeType;
    }

    public Short getActiveCycle() {
        return activeCycle;
    }

    public void setActiveCycle(Short activeCycle) {
        this.activeCycle = activeCycle;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProDetailContent() {
        return proDetailContent;
    }

    public void setProDetailContent(String proDetailContent) {
        this.proDetailContent = proDetailContent;
    }

    public String getIsSaleNationwide() {
        return isSaleNationwide;
    }

    public void setIsSaleNationwide(String isSaleNationwide) {
        this.isSaleNationwide = isSaleNationwide;
    }

    public String getIsReplaceSell() {
        return isReplaceSell;
    }

    public void setIsReplaceSell(String isReplaceSell) {
        this.isReplaceSell = isReplaceSell;
    }

    public String getUpshelfType() {
        return upshelfType;
    }

    public void setUpshelfType(String upshelfType) {
        this.upshelfType = upshelfType;
    }

    public Timestamp getUpTime() {
        return upTime;
    }

    public void setUpTime(Timestamp upTime) {
        this.upTime = upTime;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }

    public String getBasicOrgId() {
        return basicOrgId;
    }

    public void setBasicOrgId(String basicOrgId) {
        this.basicOrgId = basicOrgId;
    }

    public String getAudiencesPerson() {
        return audiencesPerson;
    }

    public void setAudiencesPerson(String audiencesPerson) {
        this.audiencesPerson = audiencesPerson;
    }

    public String getAudiencesEnterprise() {
        return audiencesEnterprise;
    }

    public void setAudiencesEnterprise(String audiencesEnterprise) {
        this.audiencesEnterprise = audiencesEnterprise;
    }

    public String getAudiencesAgents() {
        return audiencesAgents;
    }

    public void setAudiencesAgents(String audiencesAgents) {
        this.audiencesAgents = audiencesAgents;
    }
}
