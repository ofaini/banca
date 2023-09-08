package com.unab.banca.Dao;
import com.unab.banca.Models.Cuenta;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CuentaDao  extends CrudRepository<Cuenta, String> {
    //Operación para seleccionar cuentas de un cliente en particular (SELECT)
    @Transactional(readOnly=true)//No afecta integridad base de datos
    @Query(value="SELECT * FROM cuenta WHERE id_cliente= :idc", nativeQuery=true)
    public List<Cuenta> consulta_cuenta(@Param("idc") String idc); 
    //Operación Depósito
    @Transactional(readOnly=false)
    @Modifying
    @Query(value="UPDATE cuenta SET saldo_cuenta=saldo_cuenta + :valor_deposito WHERE id_cuenta like :idcta", nativeQuery=true)
    public void deposito(@Param("idcta") String idcta,@Param("valor_deposito") Double valor_deposito); 
    //Operación Retiro
    @Transactional(readOnly=false)
    @Modifying
    @Query(value="UPDATE cuenta SET saldo_cuenta=saldo_cuenta - :valor_retiro WHERE id_cuenta like :idcta", nativeQuery=true)
    public void retiro(@Param("idcta") String idcta,@Param("valor_retiro") Double valor_retiro); 
}
