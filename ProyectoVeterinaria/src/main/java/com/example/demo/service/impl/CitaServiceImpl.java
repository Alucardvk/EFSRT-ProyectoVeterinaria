package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.entity.CitaEntity;
import com.example.demo.entity.ClienteEntity;
import com.example.demo.repository.CitaRepository;
import com.example.demo.service.CitaService;

@Service
public class CitaServiceImpl implements CitaService {
	
	@Autowired
	private CitaRepository citaRepository;
	
	@Override
	public List<CitaEntity> obtenerLasCitas() {
		// TODO Auto-generated method stub
		return citaRepository.findAll();
	}

	@Override
	public void registrarCita(CitaEntity citaEntity, Model model) {
		// TODO Auto-generated method stub
		citaRepository.save(citaEntity);
	}

	@Override
	public void verCita(Model model, Integer id) {
		// TODO Auto-generated method stub
		CitaEntity citaDetalle = citaRepository.findById(id).get();
		model.addAttribute("cita",citaDetalle);
	}

	@Override
	public void eliminarCita(Integer id) {
		// TODO Auto-generated method stub
		citaRepository.deleteById(id);
	}
	
	public CitaEntity obtenerPorID(Integer id) {
		// TODO Auto-generated method stub
		return citaRepository.findById(id).get();
	}
	
	
	public void actualizarCitaInforme(CitaEntity citaEntity) {
	    // Verificar si la cita existe en la base de datos antes de actualizar
	    CitaEntity citaExistente = citaRepository.findById(citaEntity.getCodCita())
	                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con ID: " + citaEntity.getCodCita()));

        // Actualizar solo los campos que deseas (informe y estado en este caso)
        citaExistente.setInforme(citaEntity.getInforme());
        citaExistente.setEstado(citaEntity.getEstado());

        // Guardar la cita actualizada en la base de datos
        citaRepository.save(citaExistente);
    }
	
	
	
	
}
