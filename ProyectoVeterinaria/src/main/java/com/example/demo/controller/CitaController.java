package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.CitaEntity;
import com.example.demo.entity.ClienteEntity;
import com.example.demo.entity.MascotaEntity;
import com.example.demo.entity.VeterinarioEntity;
import com.example.demo.repository.VeterinarioRepository;
import com.example.demo.service.CitaService;
import com.example.demo.service.MascotaService;

@Controller
public class CitaController {

	@Autowired
	private CitaService citaService;
	
	@Autowired
	private MascotaService mascotaService;
	
	@Autowired
	private VeterinarioRepository veterinarioRepository;
	
	@GetMapping("/lista_citas")
	public String listarCitas(Model model) {
		List<CitaEntity> listaCitas = citaService.obtenerLasCitas();
		model.addAttribute("citas",listaCitas);
		

		return "lista_citas";
	}
	
	@GetMapping("/registrar_cita/{id}")
	public String mostrarRegistrarCita(@PathVariable("id") int id, Model model) {
	    CitaEntity cita = new CitaEntity();
	    
	  
	    MascotaEntity mascota = mascotaService.obtenerPorID(id);
	    cita.setCodMascota(mascota); // Asignar la mascota a la cita

	
	    List<VeterinarioEntity> veterinarios = veterinarioRepository.findAll();
	    
	    model.addAttribute("cita", cita);
	    model.addAttribute("veterinarios", veterinarios);
	    
	    return "registrar_cita"; 
	}
	
	@PostMapping("/registrar_cita")
	public String registrarCita(@ModelAttribute CitaEntity cita,Model model) {
		
		MascotaEntity mascota = mascotaService.obtenerPorID(cita.getCodMascota().getCodMascota());
		VeterinarioEntity veterinario = veterinarioRepository.findById(cita.getCodVet().getCodVet()).get();
		cita.setCodMascota(mascota);
		cita.setCodVet(veterinario);
		
		 if (cita.getEstado() == null || cita.getEstado().isEmpty()) {
		        cita.setEstado("Pendiente");
		    }
		 
		 if (cita.getInforme() == null || cita.getInforme().isEmpty()) {
		        cita.setInforme("-");
		    }
		
		List<MascotaEntity> mascotas = mascotaService.obtenerLasMascotas();
		model.addAttribute("mascotas",mascotas);

		List<VeterinarioEntity> veterinarios = veterinarioRepository.findAll();
		model.addAttribute("veterinarios",veterinarios);
		
		citaService.registrarCita(cita, model);
		
		return "redirect:/lista_citas";
	}
	
	@GetMapping("/actualizar_cita/{id}")
	public String mostrarActualizarCita(Model model, @PathVariable("id") Integer id) {
		citaService.verCita(model, id);
		
		return "actualizar_cita";
	}
	
	@GetMapping("/detalle_cita/{id}")
	public String mostrarDetalleCita(Model model, @PathVariable("id") Integer id) {
		citaService.verCita(model, id);
		return "detalle_cita";
	}
	
	
	@GetMapping("/detalle_registrar_inf/{id}")
	public String mostrarDetalleCitaInf(Model model, @PathVariable("id") Integer id) {
		
		CitaEntity cita = citaService.obtenerPorID(id);
		model.addAttribute("cita",cita);
		
		MascotaEntity mascota = mascotaService.obtenerPorID(cita.getCodMascota().getCodMascota());
		VeterinarioEntity veterinario = veterinarioRepository.findById(cita.getCodVet().getCodVet()).get();
		
	    model.addAttribute("mascota", mascota);
	    model.addAttribute("veterinario", veterinario);
		
		cita.setCodMascota(mascota);
		cita.setCodVet(veterinario);
		
		
		return "detalle_registrar_inf";
	}
	
	@PostMapping("/detalle_registrar_inf")
	public String registrarCitaInf(@RequestParam("codCita") Integer codCita,
	                               @RequestParam("informe") String informe, 
	                               @RequestParam("estado") String estado, 
	                               Model model) {
	    

	    CitaEntity cita = citaService.obtenerPorID(codCita);
	    
	   
	    cita.setInforme(informe);
	    cita.setEstado(estado);
	    
	 
	    citaService.actualizarCitaInforme(cita);
	    
	    
	    return "redirect:/detalle_cita/" + codCita;
	}
	@GetMapping("/delete_cita/{id}")
	public String eliminarCita(@PathVariable("id")Integer id,Model model) {
		citaService.eliminarCita(id);
		
		List<CitaEntity> listaCitas = citaService.obtenerLasCitas();
		model.addAttribute("citas",listaCitas);
		
		return "lista_citas";
	}
}
