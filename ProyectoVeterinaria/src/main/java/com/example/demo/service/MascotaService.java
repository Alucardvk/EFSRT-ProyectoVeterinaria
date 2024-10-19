package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.entity.ClienteEntity;
import com.example.demo.entity.MascotaEntity;

public interface MascotaService {
	List<MascotaEntity> obtenerLasMascotas();
	void registrarMascota(MascotaEntity mascotaEntity,Model model);
	void verMascota(Model model, Integer id);
	void eliminarMascota(Integer id);
	MascotaEntity obtenerPorID(Integer id);
	List<MascotaEntity> obtenerMascotasPorCliente(Integer codCliente);
}
