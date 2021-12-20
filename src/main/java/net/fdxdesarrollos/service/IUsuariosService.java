package net.fdxdesarrollos.service;

import java.util.List;

import net.fdxdesarrollos.model.Usuario;

public interface IUsuariosService {
	void guardar(Usuario usuario);
	void eliminar(Integer idUsuario);
	List<Usuario> buscarTodos();
	Usuario buscarPorId(Integer idUsuario);
	Usuario buscarPorUsername(String username);
}
