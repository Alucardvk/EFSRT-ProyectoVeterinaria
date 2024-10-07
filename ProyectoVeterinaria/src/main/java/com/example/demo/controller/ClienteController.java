package com.example.demo.controller;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.ClienteEntity;

import com.example.demo.service.ClienteService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	
	
	@GetMapping("/lista_clientes")
	public String listarClientes(Model model) {
		List<ClienteEntity> listaClientes = clienteService.obtenerLosClientes();
		model.addAttribute("clientes",listaClientes);
		

		return "lista_clientes";
	}
	
	@GetMapping("/registrar_cliente")
	public String mostrarRegistrarCliente(@ModelAttribute ClienteEntity cliente,Model model) {
		model.addAttribute("cliente",new ClienteEntity());
		
		List<ClienteEntity> clientes = clienteService.obtenerLosClientes();
		model.addAttribute("clientes",clientes);
		return "registrar_cliente";
	}
	
	@PostMapping("/registrar_cliente")
	public String registrarCliente(@ModelAttribute ClienteEntity cliente,Model model) {
		clienteService.registrarCliente(cliente, model);
		

		return "redirect:/lista_clientes";
	}
	
	@GetMapping("/actualizar_cliente/{id}")
	public String mostrarActualizarCliente(Model model, @PathVariable("id") Integer id) {
		clienteService.verCliente(model, id);
		
		return "actualizar_cliente";
	}
	
	@GetMapping("/detalle_cliente/{id}")
	public String mostrarDetalleCliente(Model model, @PathVariable("id") Integer id) {
		clienteService.verCliente(model, id);
		return "detalle_cliente";
	}
	
	
	@GetMapping("/delete_cliente/{id}")
	public String eliminarCliente(@PathVariable("id")Integer id,Model model) {
		clienteService.eliminarCliente(id);
		
		List<ClienteEntity> listaClientes = clienteService.obtenerLosClientes();
		model.addAttribute("clientes",listaClientes);
		
		return "lista_clientes";
	}
}
