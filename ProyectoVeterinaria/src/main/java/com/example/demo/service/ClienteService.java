package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.entity.ClienteEntity;

public interface ClienteService {
	List<ClienteEntity> obtenerLosClientes();
	void registrarCliente(ClienteEntity clienteEntity, Model model);
	void verCliente(Model model, Integer id);
	void eliminarCliente(Integer id);
	ClienteEntity obtenerPorID(Integer id);
}
