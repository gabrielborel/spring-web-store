package borelgabriel.com.br.springwebstore.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product_category")
@SequenceGenerator(name = "product_category_seq", sequenceName = "product_category_seq", allocationSize = 1)
public class ProductCategory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_category_seq")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCategory that)) return false;
        return Objects.equals(getId(), that.getId());
    }
}
