
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro") //localhost:8080/libro
public class LibroControlador {
    
    @Autowired
    private LibroServicio libroServicio;    
    @Autowired
    private AutorServicio autorServicio;   
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/registrar") //localhost:8080/libro/registrar
    public String registrar(ModelMap modelo){
        
        List<Autor> autores = autorServicio.listarAutores();
        
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        
        modelo.addAttribute("autores", autores);
        
        modelo.addAttribute("editoriales", editoriales);
        
        return "libro_form.html";
        
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap modelo) {
        
        try {
            
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial); // si todo sale bien retornamos al index
            
            modelo.put("exito", "El Libro fue cargado correctamente!");

        } catch (MiException ex) {

            List<Autor> autores = autorServicio.listarAutores();

            List<Editorial> editoriales = editorialServicio.listarEditoriales();

            modelo.addAttribute("autores", autores);

            modelo.addAttribute("editoriales", editoriales);

            modelo.put("error", ex.getMessage());

            return "libro_form.html"; //volvemos a cargar el formulario.
        }
               
        return "index.html"; 
    }
    
    
    
}
