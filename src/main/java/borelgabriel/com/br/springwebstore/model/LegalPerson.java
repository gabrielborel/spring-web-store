package borelgabriel.com.br.springwebstore.model;

import jakarta.persistence.*;

import java.io.Serial;

@Entity
@Table(name = "legal_person")
@PrimaryKeyJoinColumn(name = "id")
public class LegalPerson extends Person {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String cnpj;

    @Column(name = "state_registration", nullable = false)
    private String stateRegistration;

    @Column(name = "municipal_registration",nullable = false)
    private String municipalRegistration;

    @Column(name = "fantasy_name",nullable = false)
    private String fantasyName;

    @Column(name = "corporate_name",nullable = false)
    private String corporateName;

    @Column(nullable = false)
    private String category;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getStateRegistration() {
        return stateRegistration;
    }

    public void setStateRegistration(String stateRegistration) {
        this.stateRegistration = stateRegistration;
    }

    public String getMunicipalRegistration() {
        return municipalRegistration;
    }

    public void setMunicipalRegistration(String municipalRegistration) {
        this.municipalRegistration = municipalRegistration;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
