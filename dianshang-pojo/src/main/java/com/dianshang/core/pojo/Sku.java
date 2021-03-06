package com.dianshang.core.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="bbs_sku")
public class Sku implements Serializable {
    @Transient
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * ID
     */
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Long id;

    /**
     * 商品ID
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 颜色ID
     */
    @Column(name = "color_id")
    private Long colorId;

    /**
     * 尺码
     */
    @Column(name = "size")
    private String size;

    /**
     * 市场价
     */
    @Column(name = "market_price")
    private Float marketPrice;

    /**
     * 售价
     */
    @Column(name = "price")
    private Float price;

    /**
     * 运费 默认10元
     */
    @Column(name = "delive_fee")
    private Float deliveFee;

    /**
     * 库存
     */
    @Column(name = "stock")
    private Integer stock;

    /**
     * 购买限制
     */
    @Column(name = "upper_limit")
    private Integer upperLimit;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public Float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getDeliveFee() {
        return deliveFee;
    }

    public void setDeliveFee(Float deliveFee) {
        this.deliveFee = deliveFee;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Sku{" +
                "color=" + color +
                ", id=" + id +
                ", productId=" + productId +
                ", colorId=" + colorId +
                ", size='" + size + '\'' +
                ", marketPrice=" + marketPrice +
                ", price=" + price +
                ", deliveFee=" + deliveFee +
                ", stock=" + stock +
                ", upperLimit=" + upperLimit +
                ", createTime=" + createTime +
                '}';
    }
}