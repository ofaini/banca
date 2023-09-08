package com.unab.banca.Service;
import com.unab.banca.Models.Cliente;
import com.unab.banca.Dao.ClienteDao;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class ClienteService {
    @Autowired
    private ClienteDao clienteDao;

    @Transactional(readOnly=false)
    public Cliente save(Cliente cliente) {
        return clienteDao.save(cliente);
    }

    @Transactional(readOnly=false)
    public void delete(String id) {
        clienteDao.deleteById(id);
    }

    @Transactional(readOnly=true)
    public Cliente findById(String id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Transactional(readOnly=true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Transactional(readOnly=true)
    public Cliente login(String usuario, String clave) {
        return clienteDao.login(usuario, clave);
    }

}
