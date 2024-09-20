package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Cita")
public class CitaEntity {
	
    @Id	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cod_Cita", length = 5, nullable = false)
    private Integer codCita;

    @Column(name = "Fecha_Cita", nullable = false)
    private LocalDate fechaCita;

    @Column(name = "Hora_Cita", nullable = false)
    private LocalTime horaCita;

    @Column(name = "Descripcion", length = 255)
    private String descripcion;

    @Column(name = "Estado", length = 10, nullable = false)
    private String estado;

    @Column(name = "Informe", length = 500)
    private String informe;

    @ManyToOne
    @JoinColumn(name = "Cod_Mascota", nullable = false)
    private MascotaEntity codMascota;

    @ManyToOne
    @JoinColumn(name = "Cod_Veterinario", nullable = false)
    private VeterinarioEntity codVet;

    @ManyToOne
    @JoinColumn(name = "Cod_Cliente", nullable = false)
    private ClienteEntity codCliente;


}
