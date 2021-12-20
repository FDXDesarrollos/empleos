package net.fdxdesarrollos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import net.fdxdesarrollos.model.Solicitud;
import net.fdxdesarrollos.repository.SolicitudesRepository;
import net.fdxdesarrollos.service.ISolicitudesService;

@Service
@Primary
public class SolicitudesServiceJpa implements ISolicitudesService {
	
	@Autowired
	private SolicitudesRepository solicitudesRepo;

	@Override
	public void guardar(Solicitud soltud) {
		solicitudesRepo.save(soltud);
	}

	@Override
	public void eliminar(Integer idSoltud) {
		solicitudesRepo.deleteById(idSoltud);
	}

	@Override
	public List<Solicitud> buscarTodas() {
		return solicitudesRepo.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSoltud) {
		Optional<Solicitud> optional = solicitudesRepo.findById(idSoltud);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existSolicitud(Integer idVacante, Integer idUsuario) {
		int valorAux = solicitudesRepo.existSolicitud(idVacante, idUsuario);
		return (valorAux >= 1)? true: false;
	}

	/*@Override
	@Query(nativeQuery = true, value = "SELECT * FROM solicitudes WHERE idVacante = :idVacante AND idUsuario = :idUsuario")
	public boolean postuladoVacante(Integer idVacante, Integer idUsuario) {
		// TODO Auto-generated method stub
		return false;
	}*/

}
