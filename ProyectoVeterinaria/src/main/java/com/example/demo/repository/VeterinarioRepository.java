package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.VeterinarioEntity;

public interface VeterinarioRepository extends JpaRepository<VeterinarioEntity, Integer> {
	VeterinarioEntity findByCorreoAndContrasenia(String correo, String contrasenia);

	VeterinarioEntity findByCorreo(String correo);

}
