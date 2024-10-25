package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.VeterinarioEntity;
import com.example.demo.repository.VeterinarioRepository;
import com.example.demo.util.Utilitario;

import jakarta.servlet.http.HttpSession;

@Controller
public class VeterinarioController {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    private boolean isSessionValid(HttpSession session) {
        return session.getAttribute("veterinario") != null;
    }

    @GetMapping("/listarVeterinario")
    public String listarVeterinarios(Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }
        List<VeterinarioEntity> listaVeterinarios = veterinarioRepository.findAll();
        model.addAttribute("listaVeterinarios", listaVeterinarios);
        return "VeterinarioLista";
    }

    @GetMapping("/registrar_veterinario")
    public String showRegistrarVeterinario(Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        model.addAttribute("veterinario", new VeterinarioEntity());
        return "registrar_veterinario";
    }

    @PostMapping("/registrar_veterinario")
    public String registrarVeterinario(@ModelAttribute VeterinarioEntity veterinario, Model model) {
        if (veterinarioRepository.findByCorreo(veterinario.getCorreo()) != null) {
            model.addAttribute("errorMessage", "El correo electrónico ya está en uso.");
            model.addAttribute("veterinario", new VeterinarioEntity());
            return "registrar_veterinario"; 
        }

        String hashedPassword = Utilitario.extraerHash(veterinario.getContrasenia());
        veterinario.setContrasenia(hashedPassword);

        veterinarioRepository.save(veterinario);
        return "redirect:/listarVeterinario";
    }

    @GetMapping("/editar_veterinario/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Integer codVet, Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        VeterinarioEntity veterinario = veterinarioRepository.findById(codVet)
            .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));
        model.addAttribute("veterinario", veterinario);
        return "actualizarVeterinario"; 
    }

    @PostMapping("/actualizar_veterinario")
    public String actualizarVeterinario(@ModelAttribute("veterinario") VeterinarioEntity veterinario, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        VeterinarioEntity veterinarioExistente = veterinarioRepository.findById(veterinario.getCodVet())
            .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));
        
        if (veterinario.getContrasenia() != null && !veterinario.getContrasenia().isEmpty()) {
            String hashedPassword = Utilitario.extraerHash(veterinario.getContrasenia());
            veterinario.setContrasenia(hashedPassword);
        } else {
            veterinario.setContrasenia(veterinarioExistente.getContrasenia());
        }

        veterinarioRepository.save(veterinario); 
        return "redirect:/listarVeterinario"; 
    }

    @GetMapping("/eliminar_veterinario/{id}")
    public String eliminarVeterinario(@PathVariable("id") Integer codVet, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }
        
        veterinarioRepository.deleteById(codVet); 
        return "redirect:/listarVeterinario";
    }
}
