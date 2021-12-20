package net.fdxdesarrollos.controller;

import java.util.Date;
//import java.util.Date;
//import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import java.text.DateFormat;
//import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.fdxdesarrollos.model.Perfil;
import net.fdxdesarrollos.model.Usuario;
import net.fdxdesarrollos.model.Vacante;
import net.fdxdesarrollos.service.ICategoriasService;
import net.fdxdesarrollos.service.IUsuariosService;
import net.fdxdesarrollos.service.IVacantesService;
//import net.fdxdesarrollos.utilities.Utileria;

@Controller
public class HomeController {
	@Autowired
	private IVacantesService serviceVacantes;
	@Autowired
	private ICategoriasService serviceCategorias;
	@Autowired
	private IUsuariosService serviceUsuarios;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String mostrarHome(Model model) {
		List<Vacante> lista = serviceVacantes.buscaTodos();
		model.addAttribute("vacantes", lista);
		//System.out.println( Utileria.staticPath("/doc/logos/"));
		return "home";
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession session) {
		String username = auth.getName();
		System.out.println("---> Nombre del usuario: " + username);
		
		for(GrantedAuthority rol: auth.getAuthorities()) {
			System.out.println("---> Rol: " + rol.getAuthority());
		}
		
		if(session.getAttribute("usuario") == null) {
			Usuario usuario = serviceUsuarios.buscarPorUsername(username);
			usuario.setPassword(null);
			session.setAttribute("usuario", usuario);
			System.out.println("Usuario: " + usuario);
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String mostrarLogin() {
		return "frmLogin";
	}
	
	@GetMapping("/signup")
	public String registraUsuario(Usuario usuario) {
		return "usuarios/frmUsuario";
	}
	
	@PostMapping("/signup")
	public String guardaUsuario(Usuario usuario, BindingResult result, RedirectAttributes attributes) {		
		String pwdencry = "";
		
		if(result.hasErrors()) {
			for(ObjectError error: result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			
			attributes.addFlashAttribute("msgFail","Ocurrio un error");
			return "redirect:/usuarios/index";
		}
		
		try {
			Perfil per1 = new Perfil();
			per1.setId(3);
			usuario.agregar(per1);
			usuario.setEstatus(1);
			usuario.setFechaRegistro(new Date());
			
			pwdencry = passwordEncoder.encode(usuario.getPassword());
			usuario.setPassword(pwdencry);
			
			serviceUsuarios.guardar(usuario);
			
			attributes.addFlashAttribute("msg","Información registrada");
		}catch(Exception ex) {
			attributes.addFlashAttribute("msgFail","Error al registrar (usuario existente), verifique información");
		}
		
		return "redirect:/signup";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request){
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
	}
	
	@GetMapping("/bcrypt/{texto}")
	@ResponseBody
	public String encryptar(@PathVariable("texto") String texto) {
		return texto + "Encryptado: " + passwordEncoder.encode(texto);
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		Vacante vac = new Vacante();
		vac.reset();
		model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
		model.addAttribute("busqueda",vac);
	}
	
	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		List<Vacante> lista = serviceVacantes.buscaTodos();
		model.addAttribute("vacantes", lista);
		
		return "tabla";
	}
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("busqueda") Vacante vacante, Model model) {
		System.out.println("Buscado por: " + vacante);
		
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
		
		Example<Vacante> exmple = Example.of(vacante, matcher);
		List<Vacante> lista = serviceVacantes.buscarByExample(exmple);
		model.addAttribute("vacantes",lista);
		return "home";
	}
	
	/**
	 * InitBinder para Strings si los detecta vacios en el Data Binding los settea a null.
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/*@GetMapping("/listado")
	public String mostrarListado(Model model) {
		List<String> lista = new LinkedList<String>();
		lista.add("Ingeniero de Sistemas");
		lista.add("Auxiliar de Contabilidad");
		lista.add("Arquitecto");
		lista.add("Vendedor");
		
		model.addAttribute("empleos", lista);
		
		return "listado";
	}
	
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model) {
		Vacante vac1 = new Vacante();
		vac1.setNombre("Ingeniero de Telecomunicaciones");
		vac1.setDescripcion("Se solicita Ingeniero para soporte Intranet");
		vac1.setFecha(new Date());
		vac1.setSalario(9700.00);
		
		model.addAttribute("vacante", vac1);
		
		return "detalle";
	}*/	
}