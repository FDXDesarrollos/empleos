package net.fdxdesarrollos.service;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.fdxdesarrollos.model.Vacante;

@Service
public class VacantesServiceImp implements IVacantesService{
	private List<Vacante> lista = null;
	
	public VacantesServiceImp() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		lista = new LinkedList<Vacante>();
		
		try {
			//Oferta de trabajo 1
			Vacante vac1 = new Vacante();
			vac1.setId(1);
			vac1.setNombre("Ingeniero de Telecomunicaciones");
			vac1.setDescripcion("Se solicita Ingeniero para soporte Intranet");
			vac1.setFecha(sdf.parse("01-07-2021"));
			vac1.setSalario(30000.00);
			vac1.setDestacado(1);
			vac1.setImagen("logo1.png");
			vac1.setEstatus("1");
			vac1.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
	
			//Oferta de trabajo 2
			Vacante vac2 = new Vacante();
			vac2.setId(2);
			vac2.setNombre("Desarrollador Java Web");
			vac2.setDescripcion("Importante empresa solicita programador Java");
			vac2.setFecha(sdf.parse("02-07-2021"));
			vac2.setSalario(30000.00);
			vac2.setDestacado(1);
			vac2.setImagen("logo2.png");
			vac2.setEstatus("2");
			vac2.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");			
			
			//Oferta de trabajo 3
			Vacante vac3 = new Vacante();
			vac3.setId(3);
			vac3.setNombre("Contador Publico");
			vac3.setDescripcion("Se solicita contador con COI, NOI y SAE.");
			vac3.setFecha(sdf.parse("03-07-2021"));
			vac3.setSalario(35000.00);
			vac3.setDestacado(0);
			vac3.setImagen("logo3.png");
			vac3.setEstatus("3");
			vac3.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");			
			
			//Oferta de trabajo 4
			Vacante vac4 = new Vacante();
			vac4.setId(4);
			vac4.setNombre("SysAdmin");
			vac4.setDescripcion("Se solicita Ingeniero para SysAdmin");
			vac4.setFecha(sdf.parse("04-07-2021"));
			vac4.setSalario(25000.00);
			vac4.setDestacado(0);
			vac4.setImagen("logo4.png");
			vac4.setEstatus("1");
			vac4.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");			
			
			lista.add(vac1);
			lista.add(vac2);
			lista.add(vac3);
			lista.add(vac4);
		} catch (java.text.ParseException ex) {
			System.out.print("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Override
	public List<Vacante> buscaTodos() {
		return lista;
	}

	@Override
	public Vacante buscarPorId(Integer idVacante) {
		for(Vacante v : lista) {
			if(v.getId() == idVacante) return v;
		}
		
		return null;
	}

	@Override
	public void guardar(Vacante vac) {
		lista.add(vac);
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Integer idVacante) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
