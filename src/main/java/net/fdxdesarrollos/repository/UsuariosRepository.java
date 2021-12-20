package net.fdxdesarrollos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.fdxdesarrollos.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
	Usuario findByUsername(String username);
}
