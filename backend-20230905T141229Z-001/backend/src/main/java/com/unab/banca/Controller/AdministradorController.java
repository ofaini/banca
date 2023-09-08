package com.unab.banca.Controller;
import com.unab.banca.Models.Administrador;
import com.unab.banca.Security.Hash;
import com.unab.banca.Dao.AdministradorDao;
import com.unab.banca.Service.AdministradorService;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/administrador")
public class AdministradorController {
    @Autowired
    private AdministradorDao administradorDao; 
    @Autowired
    private AdministradorService administradorService;
    
    @PostMapping(value="/")
    @ResponseBody
    public ResponseEntity<Administrador> agregar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario, @Valid @RequestBody Administrador administrador){   
        Administrador admon1=new Administrador();
        admon1=administradorDao.login(usuario, Hash.sha1(clave));
        if (admon1!=null) {
            return new ResponseEntity<>(administradorService.save(administrador), HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
        }
            
    }
   
    @DeleteMapping(value="/{id}") 
    public ResponseEntity<Administrador> eliminar(@PathVariable String id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Administrador objadm=new Administrador();
        objadm=administradorDao.login(usuario, Hash.sha1(clave));
       if (objadm!=null) {
            Administrador obj = administradorService.findById(id); 
            if(obj!=null) 
                administradorService.delete(id);
            else 
                return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR); 
            return new ResponseEntity<>(obj, HttpStatus.OK); 
      
       } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       }
       
        
    }
    
    @PutMapping(value="/") 
    @ResponseBody
    public ResponseEntity<Administrador> editar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario,@Valid @RequestBody Administrador administrador){ 
        Administrador admon1=new Administrador();
        admon1=administradorDao.login(usuario, Hash.sha1(clave));
        if (admon1!=null) {
            administrador.setClave_administrador(Hash.sha1(administrador.getClave_administrador()));
            Administrador obj = administradorService.findById(administrador.getId_administrador()); 
            if(obj!=null) { 
                obj.setNombre_administrador(administrador.getNombre_administrador());
                obj.setClave_administrador(administrador.getClave_administrador());
                administradorService.save(administrador); 
            } 
            else 
                return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR); 
            return new ResponseEntity<>(obj, HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    }
   
    @GetMapping("/list") 
    @ResponseBody
    public ResponseEntity<List<Administrador>> consultarTodo(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){
        Administrador administrador=new Administrador();
        administrador=administradorDao.login(usuario, Hash.sha1(clave));
        if (administrador!=null) {
            return new ResponseEntity<>(administradorService.findAll(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }  
          
    }
    
    @GetMapping("/list/{id}") 
    @ResponseBody
    public ResponseEntity<Administrador> consultaPorId(@PathVariable String id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Administrador administrador=new Administrador();
        administrador=administradorDao.login(usuario, Hash.sha1(clave));
        if (administrador!=null) {
            return new ResponseEntity<>(administradorService.findById(id),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }   
    }
    
    @GetMapping("/login")
    @ResponseBody
    public Administrador ingresar(@RequestParam ("usuario") String usuario,@RequestParam ("clave") String clave) {
        clave=Hash.sha1(clave);
        return administradorService.login(usuario, clave);
    }
}
