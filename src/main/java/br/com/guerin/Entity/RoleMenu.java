package br.com.guerin.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "role_menu", schema = "public")
public class RoleMenu extends AbstractEntity{
    @Getter @Setter
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "menu_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Menu menu;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public RoleMenu(Menu menu, Role role) {
        this.menu = menu;
        this.role = role;
    }
}
