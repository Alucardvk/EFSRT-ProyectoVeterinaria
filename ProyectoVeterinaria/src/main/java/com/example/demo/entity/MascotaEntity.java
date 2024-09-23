package com.example.demo.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
@Table(name = "Mascota")
public class MascotaEntity {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cod_Mascota", nullable = false)
    private Integer codMascota;

    @Column(name = "Nombre", length = 20, nullable = false)
    private String nombre;

    @Column(name = "Raza", length = 25, nullable = false)
    private String raza;

    @Column(name = "Sexo", length = 1, nullable = false)
    private String sexo;

    @Column(name = "Edad", nullable = false)
    private int edad;

    @Column(name = "Especie", length = 15, nullable = false)
    private String especie;

    @Column(name = "Peso", precision = 10, nullable = false)
    private Double peso;

    @ManyToOne
    @JoinColumn(name = "Cod_Cliente", nullable = false)
    private ClienteEntity cliente;

}
