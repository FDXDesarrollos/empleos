package net.fdxdesarrollos.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.fdxdesarrollos.model.Usuario;
import net.fdxdesarrollos.service.IUsuariosService;

@Controller
@RequestMapping(value="/usuarios")
public class UsuariosController {
	@Autowired
	private IUsuariosService serviceUsuarios;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Usuario> lista = serviceUsuarios.buscarTodos();
		model.addAttribute("usuarios", lista);			
		return "usuarios/listUsuarios";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Integer idUsuario, Model model) {
		model.addAttribute("usuario", serviceUsuarios.buscarPorId(idUsuario));
		return "usuarios/frmUsuario";
	}
	
	@GetMapping("/delete/{id}")
	public void eliminar(@PathVariable("id") Integer idUsuario, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String json="";
		
		try {
			serviceUsuarios.eliminar(idUsuario);
			json="{\"value\":true}";
		}catch(Exception ex) {
			json="{\"value\":false}";
		}
		
		out.write(json);
        out.flush();
        out.close();
	}
}
