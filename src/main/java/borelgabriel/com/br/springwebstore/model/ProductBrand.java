package borelgabriel.com.br.springwebstore.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "product_brand")
@SequenceGenerator(name = "product_brand_seq", sequenceName = "product_brand_seq", allocationSize = 1)
public class ProductBrand implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_brand_seq")
    private Long id;
    @Column(name = "name_description", nullable = false)
    private String nameDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameDescription() {
        return nameDescription;
    }

    public void setNameDescription(String nameDescription) {
        this.nameDescription = nameDescription;
    }
}
