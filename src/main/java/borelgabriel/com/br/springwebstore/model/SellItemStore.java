package borelgabriel.com.br.springwebstore.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "sell_item_store")
@SequenceGenerator(name = "sell_item_store_seq", sequenceName = "sell_item_store_seq", allocationSize = 1)
public class SellItemStore implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sell_item_store_seq")
    private Long id;

    @Column(nullable = false)
    private Double quantity;

    @OneToOne(targetEntity = Product.class)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "product_fk")
    )
    private Product product;

    @OneToOne(targetEntity = SellPurchaseVirtualStore.class)
    @JoinColumn(
            name = "sell_purchase_virtual_store_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "sell_purchase_virtual_store_fk")
    )
    private SellPurchaseVirtualStore sellPurchaseVirtualStore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public SellPurchaseVirtualStore getSellPurchaseVirtualStore() {
        return sellPurchaseVirtualStore;
    }

    public void setSellPurchaseVirtualStore(SellPurchaseVirtualStore sellPurchaseVirtualStore) {
        this.sellPurchaseVirtualStore = sellPurchaseVirtualStore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SellItemStore that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
