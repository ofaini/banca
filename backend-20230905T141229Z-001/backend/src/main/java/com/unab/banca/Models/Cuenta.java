package com.unab.banca.Models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="cuenta")
public class Cuenta implements Serializable {
    @Id
    @NotEmpty(message = "El campo identificador de la cuenta no debe ser vacío")
    @Column(name="id_cuenta")
    private String id_cuenta;
    //@NotEmpty(message = "El campo fecha de apertura no debe ser vacío")
    @Column(name="fecha_apertura")
    private Date fecha_apertura;
    //@NotEmpty(message = "El campo saldo de la cuenta no debe ser vacío")
    @Column(name="saldo_cuenta")
    private double saldo_cuenta;
    //@NotEmpty(message = "El campo identificador del cliente, prpietario de la cuenta no debe ser vacío")
    @ManyToOne
    @JoinColumn(name="id_cliente")
    private Cliente cliente;
    
    @Override
    public String toString() {
        return "Cuenta [id_cuenta=" + id_cuenta + ", fecha_apertura=" + fecha_apertura + ", saldo_cuenta="
                + saldo_cuenta + ", cliente=" + cliente + "]";
    }

    

}
