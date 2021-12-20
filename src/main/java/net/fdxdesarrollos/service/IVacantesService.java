package net.fdxdesarrollos.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.fdxdesarrollos.model.Vacante;

public interface IVacantesService {
	void guardar(Vacante vac);
	void eliminar(Integer idVacante);
	List<Vacante>buscaTodos();
	List<Vacante> buscarDestacadas();
	Vacante buscarPorId(Integer idVacante);
	List<Vacante> buscarByExample(Example<Vacante> example);
	Page<Vacante>buscarTodas(Pageable page);
}
