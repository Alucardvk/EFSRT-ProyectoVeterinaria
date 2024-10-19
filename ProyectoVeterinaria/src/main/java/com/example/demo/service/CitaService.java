package com.example.demo.service;

import java.util.List;

import org.springframework.ui.Model;

import com.example.demo.entity.CitaEntity;
import com.example.demo.entity.ClienteEntity;


public interface CitaService {
	List<CitaEntity> obtenerLasCitas();
	void registrarCita(CitaEntity citaEntity, Model model);
	void verCita(Model model, Integer id);
	void eliminarCita(Integer id);
	CitaEntity obtenerPorID(Integer id);
	void actualizarCitaInforme(CitaEntity citaEntity);
	public List<CitaEntity> obtenerCitasPorEstado(String estado);
}
