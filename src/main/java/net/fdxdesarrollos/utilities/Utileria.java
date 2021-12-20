package net.fdxdesarrollos.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

public class Utileria {
	public static String guardarArchivo(MultipartFile multiPart, String ruta) {
		String nombreAux = multiPart.getOriginalFilename();
		nombreAux.replace(" ", "-");
		String nombre = rndAlphaNum(6) + nombreAux;
		
		try {
			File imageFile = new File(ruta + nombre);
			System.out.println("Archivo: " + imageFile.getAbsolutePath());
			
			multiPart.transferTo(imageFile);
			return nombre;
		} catch (IOException e) {
			System.out.println("Error " + e.getMessage());
			return null;
		}
	}
	
	public static String rndAlphaNum(int length) {
		String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		
		while(length-- != 0) {
			int character = (int) (Math.random() * CARACTERES.length());
			builder.append(CARACTERES.charAt(character));
		}
		
		return builder.toString();
	}
	
    public static String staticPath(String relativePath) {
        // Obtener el directorio
        File path = null;
        
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            // nothing to do
        }
        
        if (path == null || !path.exists()) {
            path = new File("");
        }

        String pathStr = path.getAbsolutePath();
        // Si se está ejecutando en eclipse, está al mismo nivel que el directorio de destino. 
        // Si se implementa en el servidor, está al mismo nivel que el paquete jar por defecto
        pathStr = pathStr.replace("\\target\\classes", "");

        return pathStr + "/static" + relativePath;
    }	
}
