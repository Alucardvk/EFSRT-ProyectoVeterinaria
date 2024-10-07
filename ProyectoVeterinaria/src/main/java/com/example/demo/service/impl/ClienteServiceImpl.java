package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.entity.ClienteEntity;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public List<ClienteEntity> obtenerLosClientes() {
		// TODO Auto-generated method stub
		return clienteRepository.findAll();
	}

	@Override
	public void registrarCliente(ClienteEntity clienteEntity, Model model) {
		// TODO Auto-generated method stub
		clienteRepository.save(clienteEntity);
	}

	@Override
	public void verCliente(Model model, Integer id) {
		// TODO Auto-generated method stub
		ClienteEntity clienteDetalle = clienteRepository.findById(id).get();
		model.addAttribute("cliente",clienteDetalle);
	}

	@Override
	public void eliminarCliente(Integer id) {
		// TODO Auto-generated method stub
		clienteRepository.deleteById(id);
	}

	public ClienteEntity obtenerPorID(Integer id) {
		// TODO Auto-generated method stub
		return clienteRepository.findById(id).get();
	}


	
}
