package net.fdxdesarrollos.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.fdxdesarrollos.model.Categoria;

@Service
public class CategoriaServiceImp implements ICategoriasService{
	private List<Categoria> lista = null;

	public CategoriaServiceImp() {
		lista = new LinkedList<Categoria>();
		
		try {
			//Categoria de trabajo 1
			Categoria cat1 = new Categoria();
			cat1.setId(1);
			cat1.setNombre("Contabilidad");
			cat1.setDescripcion("Descripcion de la categoria Contabilidad");
	
			//Categoria de trabajo 2
			Categoria cat2 = new Categoria();
			cat2.setId(2);
			cat2.setNombre("Ventas");
			cat2.setDescripcion("Descripcion de la categoria Ventas");			
			
			//Categoria de trabajo 3
			Categoria cat3 = new Categoria();
			cat3.setId(3);
			cat3.setNombre("Transporte");
			cat3.setDescripcion("Descripcion de la categoria Transporte");
			
			//Categoria de trabajo 4
			Categoria cat4 = new Categoria();
			cat4.setId(4);
			cat4.setNombre("Informática");
			cat4.setDescripcion("Descripcion de la categoria Informática");			

			//Categoria de trabajo 5
			Categoria cat5 = new Categoria();
			cat5.setId(5);
			cat5.setNombre("Construcción");
			cat5.setDescripcion("Descripcion de la categoria Construcción");
			
			lista.add(cat1);
			lista.add(cat2);
			lista.add(cat3);
			lista.add(cat4);
			lista.add(cat5);
		} catch (Exception ex) {
			System.out.print("Error: " + ex.getMessage());
			ex.printStackTrace();
		}		
	}

	@Override
	public void guardar(Categoria cat) {
		lista.add(cat);
	}

	@Override
	public List<Categoria> buscarTodas() {
		return lista;
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		for(Categoria c : lista) {
			if(c.getId() == idCategoria) return c;
		}
		
		return null;
	}

	@Override
	public void eliminar(Integer idCategoria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}
}
