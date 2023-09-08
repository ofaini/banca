package com.unab.banca.Service;
import com.unab.banca.Models.Cuenta;
import com.unab.banca.Dao.CuentaDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional;

@Service
public class CuentaService {
    @Autowired
    private CuentaDao cuentaDao;
    
    @Transactional(readOnly=false)
    public Cuenta save(Cuenta cuenta) {
        return cuentaDao.save(cuenta);
    }
    @Transactional(readOnly=false)
    public void delete(String id) {
        cuentaDao.deleteById(id);;
    }
    @Transactional(readOnly=true)
    public Cuenta findById(String id) {
       return cuentaDao.findById(id).orElse(null);
    }
    @Transactional(readOnly=true)
    public List<Cuenta> findByAll() {
        return (List<Cuenta>) cuentaDao.findAll();
    }
    @Transactional(readOnly=true)
    public List<Cuenta> consulta_cuenta(String idc) {
        return (List<Cuenta>) cuentaDao.consulta_cuenta(idc);
    }

    @Transactional(readOnly=false)
    public void deposito(String idcta,Double valor_deposito) {
        cuentaDao.deposito(idcta, valor_deposito);
    }

    @Transactional(readOnly=false)
    public void retiro(String idcta,Double valor_retiro) {
        cuentaDao.retiro(idcta, valor_retiro);
    }
}
