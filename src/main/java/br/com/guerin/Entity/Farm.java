package br.com.guerin.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "farms", schema = "public")
public class Farm extends AbstractEntity{

    @Getter @Setter
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Getter @Setter
    @Column(name = "address", nullable = false, unique = true)
    private String address;
}
