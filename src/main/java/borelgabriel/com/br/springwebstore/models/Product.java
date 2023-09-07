package borelgabriel.com.br.springwebstore.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
@SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "unity_type", nullable = false)
    private String unityType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT", length = 2000)
    private String description;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double width;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private Double depth;

    @Column(name = "sale_value", nullable = false)
    private BigDecimal saleValue = BigDecimal.ZERO;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;

    @Column(name = "stock_alert_quantity")
    private Integer stockAlertQuantity = 0;

    @Column(name = "youtube_video_url")
    private String youtubeVideoUrl;

    @Column(name = "alert_stock_quantity")
    private Boolean alertStockQuantity = Boolean.FALSE;

    @Column(name = "clicks_quantity")
    private Integer clicksQuantity = 0;

    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getUnityType() {
        return unityType;
    }

    public void setUnityType(String unityType) {
        this.unityType = unityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public BigDecimal getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(BigDecimal saleValue) {
        this.saleValue = saleValue;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getStockAlertQuantity() {
        return stockAlertQuantity;
    }

    public void setStockAlertQuantity(Integer stockAlertQuantity) {
        this.stockAlertQuantity = stockAlertQuantity;
    }

    public String getYoutubeVideoUrl() {
        return youtubeVideoUrl;
    }

    public void setYoutubeVideoUrl(String youtubeVideoUrl) {
        this.youtubeVideoUrl = youtubeVideoUrl;
    }

    public Boolean getAlertStockQuantity() {
        return alertStockQuantity;
    }

    public void setAlertStockQuantity(Boolean alertStockQuantity) {
        this.alertStockQuantity = alertStockQuantity;
    }

    public Integer getClicksQuantity() {
        return clicksQuantity;
    }

    public void setClicksQuantity(Integer clicksQuantity) {
        this.clicksQuantity = clicksQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
