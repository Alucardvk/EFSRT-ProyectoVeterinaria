package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.entity.ClienteEntity;
import com.example.demo.entity.MascotaEntity;
import com.example.demo.repository.MascotaRepository;
import com.example.demo.service.MascotaService;


@Service
public class MascotaServiceImpl implements MascotaService {

	@Autowired
	private MascotaRepository mascotaRepository;
	
	@Override
	public List<MascotaEntity> obtenerLasMascotas() {
		// TODO Auto-generated method stub
		return mascotaRepository.findAll();
	}

	@Override
	public void registrarMascota(MascotaEntity mascotaEntity, Model model) {
		// TODO Auto-generated method stub
		mascotaRepository.save(mascotaEntity);
	}

	@Override
	public void verMascota(Model model, Integer id) {
		// TODO Auto-generated method stub
		MascotaEntity mascotaDetalle = mascotaRepository.findById(id).get();
		model.addAttribute("mascota",mascotaDetalle);
	}

	@Override
	public void eliminarMascota(Integer id) {
		// TODO Auto-generated method stub
		mascotaRepository.deleteById(id);
	}
	
	public MascotaEntity obtenerPorID(Integer id) {
		// TODO Auto-generated method stub
		return mascotaRepository.findById(id).get();
	}
}
