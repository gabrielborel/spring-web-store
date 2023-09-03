package borelgabriel.com.br.springwebstore.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serial;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
public class User implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;
    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @Temporal(TemporalType.DATE)
    private Date lastPasswordUpdateDate;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_access",
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "access_id"}, name = "unique_user_access"),
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", table = "user", unique = false, foreignKey = @ForeignKey(name = "user_fk", value = ConstraintMode.CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "access_id", referencedColumnName = "id", table = "access", unique = false, foreignKey = @ForeignKey(name = "access_fk", value = ConstraintMode.CONSTRAINT))
    )
    private List<Access> accessList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.accessList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
