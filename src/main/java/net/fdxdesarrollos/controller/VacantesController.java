package net.fdxdesarrollos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.fdxdesarrollos.model.Vacante;
import net.fdxdesarrollos.service.ICategoriasService;
import net.fdxdesarrollos.service.IVacantesService;
import net.fdxdesarrollos.utilities.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Value("${empleosapp.ruta.logos}")
	private String rutaLogos;
	
	@Autowired
	private IVacantesService serviceVacantes;
	
	@Autowired
	private ICategoriasService serviceCategorias;
	
	@GetMapping("/index")
	public String index(Model model){
		List<Vacante> lista = serviceVacantes.buscaTodos();
		model.addAttribute("vacantes", lista);		
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Vacante> lista = serviceVacantes.buscarTodas(page);
		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/create")
	public String crear(Vacante vacante, Model model) {
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
		return "vacantes/frmVacante";
	}
	
	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes, @RequestParam("archivoImagen") MultipartFile multipart) {
		if(result.hasErrors()) {
			for(ObjectError error: result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			
			return "vacantes/frmVacante";
		}
		
		if (!multipart.isEmpty()) {
			String nombreImagen = Utileria.guardarArchivo(multipart, Utileria.staticPath(rutaLogos));
			if (nombreImagen != null){ 
				vacante.setImagen(nombreImagen);
			}
		}
		
		serviceVacantes.guardar(vacante);
		
		attributes.addFlashAttribute("msg","Información registrada");
		return "redirect:/vacantes/index";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Integer idVacante, Model model) {
		model.addAttribute("vacante", serviceVacantes.buscarPorId(idVacante));
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
		return "vacantes/frmVacante";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Integer idVacante, RedirectAttributes attributes) {		
		serviceVacantes.eliminar(idVacante);
		
		attributes.addFlashAttribute("msg","La vacante fue eliminada");
		return "redirect:/vacantes/index";
	}
	
	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") Integer idVacante, Model model) {
		Vacante vac = serviceVacantes.buscarPorId(idVacante);
		
		System.out.print("Vacante: " + vac);
		model.addAttribute("vacante", vac);
		
		return "vacantes/detalle";
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(fmt,false));
	}
	
	/*@PostMapping("/save")
	public String guardar(@RequestParam("nombre") String nombre,
						  @RequestParam("descrip") String descripcion,
						  @RequestParam("estatus") String estatus,
						  @RequestParam("fecha") String fecha,
						  @RequestParam("destacado") Integer destacado,
						  @RequestParam("salario") Double salario,
						  @RequestParam("detalles") String detalles) {
		
		System.out.println("Nombre Vacante: " + nombre);
		System.out.println("Descripcion: " + descripcion);
		System.out.println("Estatus: " + estatus);
		System.out.println("Fecha Publicación: " + fechaMySQL(fecha));
		System.out.println("Destacado: " + destacado);
		System.out.println("Salario Ofrecido: " + salario);
		System.out.println("Detalles: " + detalles);
		
		return "vacantes/listVacantes";
	}*/		

}
