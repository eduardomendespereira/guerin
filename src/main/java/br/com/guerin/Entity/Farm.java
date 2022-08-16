package br.com.guerin.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "farms", schema = "public")
public class Farm extends AbstractEntity{

    @Getter @Setter
    @NotNull @NotBlank
    @Length(min = 3, max = 50, message = "O nome deverá ter no máximo {max} caracteres")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Getter @Setter
    @NotNull @NotBlank
    @Length(min = 3, max = 150, message = "O endereço deverá ter no máximo {max} caracteres")
    @Column(name = "address", nullable = false, unique = true)
    private String address;
    public Farm(String name, String address) {
        this.name = name;
        this.address = address;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Farm farm = (Farm) o;
        return Objects.equals(name, farm.name) && Objects.equals(address, farm.address);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }
}
