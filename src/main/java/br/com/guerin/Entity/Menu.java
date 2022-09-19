package br.com.guerin.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "menus", schema = "public")
public class Menu extends AbstractEntity {

    @Getter
    @Setter
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Getter
    @Setter
    @Column(name = "description", nullable = true, length = 100)
    private String description;

    @Getter
    @Setter
    @Column(name = "path", nullable = true, length = 50)
    private String path;

    @Getter
    @Setter
    @Column(name = "icon", nullable = true, length = 20)
    private String icon;
}
