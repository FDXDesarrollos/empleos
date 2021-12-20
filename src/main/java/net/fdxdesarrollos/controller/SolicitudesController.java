package net.fdxdesarrollos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
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

import net.fdxdesarrollos.model.Solicitud;
import net.fdxdesarrollos.model.Usuario;
import net.fdxdesarrollos.service.ISolicitudesService;
import net.fdxdesarrollos.service.IUsuariosService;
import net.fdxdesarrollos.service.IVacantesService;
import net.fdxdesarrollos.utilities.Utileria;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {
	
	/**
	 * EJERCICIO: Declarar esta propiedad en el archivo application.properties. El valor sera el directorio
	 * en donde se guardarán los archivos de los Curriculums Vitaes de los usuarios.
	 */
	@Value("${empleosapp.ruta.soltud}")
	private String rutaArchivo;
	
	@Autowired
	private ISolicitudesService serviceSoltud;
	
	@Autowired
	private IVacantesService serviceVacantes;
	
    @Autowired
   	private IUsuariosService serviceUsuarios;
    
    /**
	 * Metodo que muestra la lista de solicitudes sin paginacion
	 * Seguridad: Solo disponible para un usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */    
    @GetMapping("/index")
	public String index(Model model) {
    	List<Solicitud> lista = serviceSoltud.buscarTodas();
    	model.addAttribute("solicitudes", lista);
		return "solicitudes/listSolicitudes";
	}    
    
	/**
	 * Método para renderizar el formulario para aplicar para una Vacante
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 */
	@GetMapping("/create/{id}")
	public String crear(@PathVariable("id") Integer idVacante, Model model) {
		model.addAttribute("vacante", serviceVacantes.buscarPorId(idVacante));
		return "solicitudes/frmSolicitud";
	}
	
	/**
	 * Método que guarda la solicitud enviada por el usuario en la base de datos
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(Solicitud solicitud, BindingResult result, RedirectAttributes attributes, @RequestParam("archivoCV") MultipartFile multiPart, 
			              Model model, HttpSession session, Authentication authentication) {			
		Integer idUsuario = 0;
		Integer idVacante = solicitud.getVacante().getId();
		String nombreArchivo = null;
		
		// Recuperamos el username que inicio sesión
		String username = authentication.getName();		
		
		// Buscamos el objeto Usuario en BD	
		Usuario usuario = serviceUsuarios.buscarPorUsername(username);
		idUsuario = usuario.getId();
		
		if(serviceSoltud.existSolicitud(idVacante, idUsuario)){
			attributes.addFlashAttribute("msgfail", "Ya te has postulado a esta oferta!");
			return "redirect:/solicitudes/create/" + idVacante;
		}	
		
		if(result.hasErrors()) {
			for(ObjectError error: result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			
			attributes.addFlashAttribute("msgfail", "Erro al registrar!");
			return "solicitudes/frmSolicitud";
		}		
		
		if (!multiPart.isEmpty()) {
			nombreArchivo = Utileria.guardarArchivo(multiPart, Utileria.staticPath(rutaArchivo));
			
			if (nombreArchivo != null){ 
				solicitud.setArchivo(nombreArchivo);
			}
		}
		
		// Referenciamos la solicitud con el usuario 
		solicitud.setUsuario(usuario); 
		solicitud.setFecha(new Date());
		
		// Guadamos el objeto solicitud en la bd
		serviceSoltud.guardar(solicitud);
		
		attributes.addFlashAttribute("msg", "Gracias por enviar tú CV!");
		return "redirect:/solicitudes/create/" + idVacante;
	}
	
	/**
	 * Método para eliminar una solicitud
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR. 
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Integer idSoltud, RedirectAttributes attributes) {
		serviceSoltud.eliminar(idSoltud);
		
		attributes.addFlashAttribute("msg","La solicitud fue eliminada");
		//return "redirect:/solicitudes/indexPaginate";
		return "redirect:/solicitudes/index";
	}
    
    /**
	 * Metodo que muestra la lista de solicitudes con paginacion
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado() {
		
		// EJERCICIO
		return "solicitudes/listSolicitudes";
	}	
	
	/**
	 * Personalizamos el Data Binding para todas las propiedades de tipo Date
	 * @param webDataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
