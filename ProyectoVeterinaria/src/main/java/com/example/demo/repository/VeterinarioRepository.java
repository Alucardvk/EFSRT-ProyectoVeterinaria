package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.VeterinarioEntity;


@Repository
public interface VeterinarioRepository extends JpaRepository<VeterinarioEntity, Integer> {
	VeterinarioEntity findByCorreoAndContrasenia(String correo, String contrasenia);

	VeterinarioEntity findByCorreo(String correo);

}