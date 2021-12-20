package net.fdxdesarrollos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.fdxdesarrollos.model.Categoria;
import net.fdxdesarrollos.model.Vacante;
import net.fdxdesarrollos.service.ICategoriasService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {
	
	@Autowired
	private ICategoriasService serviceCategorias;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Categoria> lista = serviceCategorias.buscarTodas();
		model.addAttribute("categorias", lista);			
		return "categorias/listCategorias";
	}
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Categoria> lista = serviceCategorias.buscarTodas(page);
		model.addAttribute("categorias", lista);
		return "categorias/listCategorias";
	}	
	
	@GetMapping("/create")
	public String crear(Model model) {
		Categoria cat = new Categoria();
		model.addAttribute("categoria", cat);		
		return "categorias/frmCategoria";
	}
	
	@PostMapping("/save")
	public String guardar(Categoria cat, BindingResult result, RedirectAttributes attrib) {
		if(result.hasErrors()) {
			for(ObjectError error: result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			
			return "categorias/frmCategoria";
		}
		
		serviceCategorias.guardar(cat);
		attrib.addFlashAttribute("msg"," Información registrada");
		return "redirect:/categorias/index";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Integer idCategoria, Model model) {
		model.addAttribute("categoria", serviceCategorias.buscarPorId(idCategoria));
		return "categorias/frmCategoria";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Integer idCategoria, RedirectAttributes attributes) {
		try {
			serviceCategorias.eliminar(idCategoria);
			attributes.addFlashAttribute("msg","La categoria fue eliminada");
		}catch(Exception ex) {
			attributes.addFlashAttribute("msgFail", "La categoría con Id " + idCategoria + " no puede ser eliminada, porque su registro se encuentra asociado a alguna vacante.");
			System.out.println("\n Problema de Integridad en Base de Datos: La categoría con Id" + idCategoria +  " no puede ser eliminada, porque su registro se encuentra asociado a alguna vacante. \n"  + ex.getMessage()  + ex.getCause());
		}
		
		return "redirect:/categorias/index";
	}
	
	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") Integer idCategoria, Model model) {
		Categoria cat = serviceCategorias.buscarPorId(idCategoria);
		
		model.addAttribute("categoria", cat);
		System.out.print("Categoria: " + cat);
		return "categorias/frmCategoria";
	}	
}
