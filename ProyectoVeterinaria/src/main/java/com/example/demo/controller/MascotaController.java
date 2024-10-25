package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.ClienteEntity;
import com.example.demo.entity.MascotaEntity;
import com.example.demo.service.ClienteService;
import com.example.demo.service.MascotaService;
import com.example.demo.service.impl.PdfService;

import jakarta.servlet.http.HttpSession;

@SuppressWarnings("unused")
@Controller
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private PdfService pdfService;
    
    @GetMapping("/mascotas")
    public String listarMascotas(HttpSession session, Model model) {
        List<MascotaEntity> listaMascotas = mascotaService.obtenerLasMascotas();
        List<ClienteEntity> listaClientes = clienteService.obtenerLosClientes();
        
        model.addAttribute("clientes", listaClientes);
        model.addAttribute("mascotas", listaMascotas);
        return "mascotas";
    }
    
    @GetMapping("/registrar_mascota")
    public String mostrarRegistrarMascota(HttpSession session, @ModelAttribute MascotaEntity mascota, Model model) {
        model.addAttribute("mascota", new MascotaEntity());
        
        List<ClienteEntity> clientes = clienteService.obtenerLosClientes();
        List<MascotaEntity> listaMascotas = mascotaService.obtenerLasMascotas();
        
        model.addAttribute("clientes", clientes);
        model.addAttribute("cliente", new ClienteEntity());
        model.addAttribute("mascotas", listaMascotas);
        
        return "registrar_mascota";
    }
    
    @PostMapping("/registrar_mascota")
    public String registrarMascota(HttpSession session, @ModelAttribute MascotaEntity mascota, Model model) {
        ClienteEntity cliente = clienteService.obtenerPorID(mascota.getCliente().getCodCliente());
        mascota.setCliente(cliente);
        mascotaService.registrarMascota(mascota, model);
        
        List<MascotaEntity> mascotas = mascotaService.obtenerLasMascotas();
        model.addAttribute("mascotas", mascotas);
        return "mascotas";
    }
    
    @GetMapping("/actualizar_mascota/{id}")
    public String mostrarActualizarMascota(HttpSession session, Model model, @PathVariable("id") Integer id) {
        mascotaService.verMascota(model, id);
        return "actualizar_mascota";
    }
    
    @GetMapping("/detalle_mascota/{id}")
    public String mostrarDetalleMascota(HttpSession session, Model model, @PathVariable("id") Integer id) {
        mascotaService.verMascota(model, id);
        return "detalle_mascota";
    }
    
    @GetMapping("/delete_mascota/{id}")
    public String eliminarMascota(HttpSession session, @PathVariable("id") Integer id, Model model) {
        mascotaService.eliminarMascota(id);
        
        List<MascotaEntity> listaMascotas = mascotaService.obtenerLasMascotas();
        model.addAttribute("mascotas", listaMascotas);
        
        return "mascotas";
    }
}