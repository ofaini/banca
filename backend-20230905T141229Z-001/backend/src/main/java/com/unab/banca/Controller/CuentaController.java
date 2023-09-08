package com.unab.banca.Controller;
import com.unab.banca.Models.Cuenta;
import com.unab.banca.Security.Hash;
import com.unab.banca.Dao.CuentaDao;
import com.unab.banca.Models.Cliente;
import com.unab.banca.Dao.ClienteDao;
import com.unab.banca.Models.Administrador;
import com.unab.banca.Dao.AdministradorDao;
import com.unab.banca.Service.ClienteService;
import com.unab.banca.Service.CuentaService;

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
@RequestMapping("/cuenta")
public class CuentaController {
    
    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private AdministradorDao administradorDao;

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private CuentaService cuentaService;
    
    
    
    @PostMapping(value="/")
    @ResponseBody
    public ResponseEntity<Cuenta> agregar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario, @Valid @RequestBody Cuenta cuenta){   
        Administrador admon=new Administrador();
        admon=administradorDao.login(usuario, Hash.sha1(clave));
        if (admon!=null) {
            return new ResponseEntity<>(cuentaService.save(cuenta), HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
        }
            
    }

    @DeleteMapping(value="/{id}") 
    public ResponseEntity<Cuenta> eliminar(@PathVariable String id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Administrador admon=new Administrador();
        admon=administradorDao.login(usuario, Hash.sha1(clave));
       if (admon!=null) {
            Cuenta obj = cuentaService.findById(id); 
            if(obj!=null) 
                cuentaService.delete(id);
            else 
                return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR); 
            return new ResponseEntity<>(obj, HttpStatus.OK); 
      
       } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       }
       
        
    }


    @PutMapping(value="/") 
    @ResponseBody
    public ResponseEntity<Cuenta> editar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario,@Valid @RequestBody Cuenta cuenta){ 
        Administrador admon=new Administrador();
        admon=administradorDao.login(usuario, Hash.sha1(clave));
        if (admon!=null) {
            Cuenta obj = cuentaService.findById(cuenta.getId_cuenta()); 
            if(obj!=null) { 
                obj.setFecha_apertura(cuenta.getFecha_apertura());
                obj.setSaldo_cuenta(cuenta.getSaldo_cuenta());
                obj.setCliente(cuenta.getCliente());
                cuentaService.save(cuenta); 
            } 
            else 
                return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR); 
            return new ResponseEntity<>(obj, HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    }

    @PutMapping(value="/deposito") 
    public void deposito(@RequestParam ("idcta") String idcta,@RequestParam ("valor_deposito") Double valor_deposito,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Cliente cliente1=new Cliente();
        cliente1=clienteDao.login(usuario, Hash.sha1(clave));
        if (cliente1!=null) {
           cuentaService.deposito(idcta, valor_deposito); 
        }
          
    }

    @PutMapping(value="/retiro") 
    public void retiro(@RequestParam ("idcta") String idcta,@RequestParam ("valor_retiro") Double valor_retiro,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Cliente cliente1=new Cliente();
        cliente1=clienteDao.login(usuario, Hash.sha1(clave));
        if (cliente1!=null) {
           cuentaService.retiro(idcta, valor_retiro); 
        }
        
    }

    @GetMapping("/list") 
    @ResponseBody
    public ResponseEntity<List<Cuenta>> consultarTodo(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){
        Administrador admon=new Administrador();
        admon=administradorDao.login(usuario, Hash.sha1(clave));
        if (admon!=null) {
            return new ResponseEntity<>(cuentaService.findByAll(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }  
          
    }

    @GetMapping("/list/{id}") 
    @ResponseBody
    public ResponseEntity<Cuenta> consultaPorId(@PathVariable String id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Administrador admon=new Administrador();
        admon=administradorDao.login(usuario, Hash.sha1(clave));
        if (admon!=null) {
            return new ResponseEntity<>(cuentaService.findById(id),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }   
    }


    @GetMapping("/consulta_cuenta")
    @ResponseBody
    public ResponseEntity<List<Cuenta>> consulta_cuenta(@RequestParam ("idc") String idc,@RequestHeader ("usuario") String usuario,@RequestHeader ("clave") String clave) { 
        Cliente cliente=new Cliente();
        cliente=clienteDao.login(usuario, Hash.sha1(clave));
        if (cliente!=null) {
            return new ResponseEntity<>(cuentaService.consulta_cuenta(idc),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    }
}
