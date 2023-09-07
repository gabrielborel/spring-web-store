package borelgabriel.com.br.springwebstore.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product_image")
@SequenceGenerator(name = "product_image_seq", sequenceName = "product_image_seq", allocationSize = 1)
public class ProductImage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_image_seq")
    private Long id;

    @Column(name = "original_image", nullable = false, columnDefinition = "TEXT")
    private String originalImage;

    @Column(name = "minified_image", nullable = false, columnDefinition = "TEXT")
    private String minifiedImage;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "product_fk")
    )
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public String getMinifiedImage() {
        return minifiedImage;
    }

    public void setMinifiedImage(String minifiedImage) {
        this.minifiedImage = minifiedImage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductImage that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}