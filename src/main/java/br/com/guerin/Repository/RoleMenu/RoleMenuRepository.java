package br.com.guerin.Repository.RoleMenu;

import br.com.guerin.Entity.Menu;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface RoleMenuRepository  extends JpaRepository<RoleMenu, Long> {
    @Query("SELECT p FROM RoleMenu p where p.role = :role")
    ArrayList<RoleMenu> findByRole(Role role);

    @Query("SELECT p FROM RoleMenu p where p.menu = :menu")
    ArrayList<RoleMenu> findByMenu(Menu menu);

    @Query("SELECT p FROM RoleMenu p where p.menu = :menu and p.role = :role")
    Optional<RoleMenu> findByMenuAndRole(Menu menu, Role role);
}