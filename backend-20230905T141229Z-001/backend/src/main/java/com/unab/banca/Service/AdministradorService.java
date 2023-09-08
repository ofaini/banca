package com.unab.banca.Service;
import com.unab.banca.Models.Administrador;
import com.unab.banca.Dao.AdministradorDao;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class AdministradorService {
    @Autowired
    private AdministradorDao administradorDao;

    @Transactional(readOnly=false)
    public Administrador save(Administrador administrador) {
        return administradorDao.save(administrador);
    }

    @Transactional(readOnly=false)
    public void delete(String id) {
        administradorDao.deleteById(id);
    }

    @Transactional(readOnly=true)
    public Administrador findById(String id) {
        return administradorDao.findById(id).orElse(null);
    }

    @Transactional(readOnly=true)
    public List<Administrador> findAll() {
        return (List<Administrador>) administradorDao.findAll();
    }

    @Transactional(readOnly=true)
    public Administrador login(String usuario, String clave) {
        return administradorDao.login(usuario, clave);
    }

}
