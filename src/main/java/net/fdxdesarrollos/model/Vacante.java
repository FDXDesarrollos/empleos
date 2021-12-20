package net.fdxdesarrollos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Vacantes")
public class Vacante {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String descripcion;
	private Date fecha;
	private Double salario;
	private Integer destacado;
	private String imagen = "no-image.png";
	private String estatus;
	private String detalles;
	//@Transient
	@OneToOne
	@JoinColumn(name="idCategoria")
	private Categoria categoria;
	
	//private DateFormat fmt = null;
	
	public Vacante() {
		//fmt = DateFormat.getDateInstance(DateFormat.LONG);
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/*public Date getFecha() {
		return this.fecha;
	}*/
	
	public Date getFecha() {
		//return fmt.format(this.fecha);
		return this.fecha;
	}	
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Double getSalario() {
		//return String.format("%,.2f",salario);
		return this.salario;
	}
	
	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Integer getDestacado() {
		return destacado;
	}

	public void setDestacado(Integer destacado) {
		this.destacado = destacado;
	}
	
	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public void reset() {
		this.imagen = null;
	}

	@Override
	public String toString() {
		return "Vacante [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fecha=" + fecha +
			   ", salario=" + salario + ", estatus=" + estatus + ", detalles=" + detalles + ", categoria=" + categoria + "]";
	}
	
	/*
	 import java.text.ParseException;
	 import java.text.SimpleDateFormat;
	 import java.text.DateFormat;
	  
	 private String fechaMySQL(String fecha) {
		String fechaParse = "";
		SimpleDateFormat fmt1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date fechaAux = fmt1.parse(fecha);
			fechaParse = fmt2.format(fechaAux);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		
		return fechaParse;
	}*/
}
