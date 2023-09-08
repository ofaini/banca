package com.unab.banca.Controller;
import com.unab.banca.Models.Cliente;
import com.unab.banca.Models.Administrador;
import com.unab.banca.Security.Hash;
import com.unab.banca.Dao.ClienteDao;
import com.unab.banca.Dao.AdministradorDao;
import com.unab.banca.Service.ClienteService;
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
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteDao clienteDao; 
    @Autowired
    private AdministradorDao administradorDao;
    @Autowired
    private ClienteService clienteService;
    
    @PostMapping(value="/")
    @ResponseBody
    public ResponseEntity<Cliente> agregar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario, @Valid @RequestBody Cliente cliente){   
        Administrador admon=new Administrador();
        admon=administradorDao.login(usuario, Hash.sha1(clave));
        if (admon!=null) {
            cliente.setClave_cliente(Hash.sha1(cliente.getClave_cliente()));
            return new ResponseEntity<>(clienteService.save(cliente), HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
        }
            
    }
   
    @DeleteMapping(value="/{id}") 
    public ResponseEntity<Cliente> eliminar(@PathVariable String id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Administrador admon=new Administrador();
        admon=administradorDao.login(usuario, Hash.sha1(clave));
       if (admon!=null) {
            Cliente obj = clienteService.findById(id); 
            if(obj!=null) 
                clienteService.delete(id);
            else 
                return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR); 
            return new ResponseEntity<>(obj, HttpStatus.OK); 
      
       } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       }
       
        
    }
    
    @PutMapping(value="/") 
    @ResponseBody
    public ResponseEntity<Cliente> editar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario,@Valid @RequestBody Cliente cliente){ 
        Administrador admon=new Administrador();
        admon=administradorDao.login(usuario, Hash.sha1(clave));
        if (admon!=null) {
            cliente.setClave_cliente(Hash.sha1(cliente.getClave_cliente()));
            Cliente obj = clienteService.findById(cliente.getId_cliente()); 
            if(obj!=null) { 
                obj.setNombre_cliente(cliente.getNombre_cliente());
                obj.setClave_cliente(cliente.getClave_cliente());
                clienteService.save(cliente); 
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
    public ResponseEntity<List<Cliente>> consultarTodo(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){
        Administrador admon=new Administrador();
        admon=administradorDao.login(usuario, Hash.sha1(clave));
        if (admon!=null) {
            return new ResponseEntity<>(clienteService.findAll(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }  
          
    }
    
    @GetMapping("/list/{id}") 
    @ResponseBody
    public ResponseEntity<Cliente> consultaPorId(@PathVariable String id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Administrador admon=new Administrador();
        admon=administradorDao.login(usuario, Hash.sha1(clave));
        if (admon!=null) {
            return new ResponseEntity<>(clienteService.findById(id),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }   
    }
    
    @GetMapping("/login")
    @ResponseBody
    public Cliente ingresar(@RequestParam ("usuario") String usuario,@RequestParam ("clave") String clave) {
        clave=Hash.sha1(clave);
        return clienteService.login(usuario, clave); 
    }
}
