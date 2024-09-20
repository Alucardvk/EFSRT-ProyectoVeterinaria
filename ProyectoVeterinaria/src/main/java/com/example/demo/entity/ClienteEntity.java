package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "Cliente")
public class ClienteEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cod_Cliente", nullable = false)
    private Integer codCliente;

    @Column(name = "Nombre", length = 20, nullable = false)
    private String nombre;

    @Column(name = "Apellido_Pat", length = 30, nullable = false)
    private String apellidoPat;

    @Column(name = "Apellido_Mat", length = 30, nullable = false)
    private String apellidoMat;

    @Column(name = "Correo", length = 50)
    private String correo;

    @Column(name = "Direccion", length = 50, nullable = false)
    private String direccion;

    @Column(name = "Telefono", length = 9)
    private String telefono;

    @Column(name = "DNI", length = 8, nullable = false)
    private String dni;
}
