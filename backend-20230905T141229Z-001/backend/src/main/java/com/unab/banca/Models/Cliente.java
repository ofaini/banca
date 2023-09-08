package com.unab.banca.Models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="cliente")
public class Cliente  implements Serializable{
    @Id
    @NotEmpty(message = "El campo identificador del  cliente no debe ser vacío")
    @Column(name="id_cliente")
    private String id_cliente;
    @NotEmpty(message = "El campo nombre cliente no debe ser vacío")
    @Size(min = 5, max = 80,message = "El campo nombre Cliente debe tener mínimo 5 caracteres y máximo 80")
    @Column(name="nombre_cliente")
    private String nombre_cliente;
    @NotEmpty(message = "El campo clave cliente no debe ser vacío")
    @Size(min = 5, max = 80,message = "El campo nombre Cliente debe tener mínimo 5 caracteres y máximo 50")
    @Column(name="clave_cliente")
    private String clave_cliente;

    @Override
    public String toString() {
        return "Cliente [id_cliente=" + id_cliente + ", nombre_cliente=" + nombre_cliente + ", clave_cliente="
                + clave_cliente + "]";
    }
}
