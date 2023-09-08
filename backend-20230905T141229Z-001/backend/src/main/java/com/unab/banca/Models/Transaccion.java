package com.unab.banca.Models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name="transaccion")
public class Transaccion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_transaccion")
    private int id_transaccion;
    //@NotEmpty(message = "EL campo Fecha transacción no debe ser vacío")
    @Column(name="fecha_transaccion")
    private Date fecha_transaccion;
    //@NotEmpty(message = "EL campo valor transacción no debe ser vacío")
    @Column(name="valor_transaccion")
    private double valor_transaccion;
    //@NotEmpty(message = "EL campo Tipo transacción no debe ser vacío")
    @Column(name="tipo_transaccion")
    private String tipo_transaccion;
    //@NotEmpty(message = "EL campo identificador cuenta que realizó la transacción no debe ser vacío")
    @ManyToOne
    @JoinColumn(name="id_cuenta")
    private Cuenta cuenta;

    @Override
    public String toString() {
        return "Transaccion [id_transaccion=" + id_transaccion + ", fecha_transaccion=" + fecha_transaccion
                + ", valor_transaccion=" + valor_transaccion + ", tipo_transaccion=" + tipo_transaccion + ", cuenta="
                + cuenta + "]";
    }
}
