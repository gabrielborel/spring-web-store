package borelgabriel.com.br.springwebstore.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Objects;


@Entity
@Table(name = "access")
@SequenceGenerator(name = "access_seq", sequenceName = "access_seq", allocationSize = 1)
public class Access implements GrantedAuthority {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "access_seq")
    private Long id;

    @Column(nullable = false)
    private String description;

    @Override
    public String getAuthority() {
        return this.description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Access access)) return false;
        return Objects.equals(getId(), access.getId());
    }
}
