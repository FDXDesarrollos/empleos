
package net.fdxdesarrollos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.fdxdesarrollos.model.Solicitud;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {
    @Query(nativeQuery = true, value = "SELECT COUNT(*) AS id FROM Solicitudes WHERE idVacante = :idVacante AND idUsuario = :idUsuario")
    Integer existSolicitud(@Param("idVacante")Integer idVacante, @Param("idUsuario")Integer idUsuario);        	
}