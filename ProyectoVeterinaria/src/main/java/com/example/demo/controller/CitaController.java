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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.CitaEntity;
import com.example.demo.entity.MascotaEntity;
import com.example.demo.entity.VeterinarioEntity;
import com.example.demo.repository.VeterinarioRepository;
import com.example.demo.service.CitaService;
import com.example.demo.service.MascotaService;
import com.example.demo.service.impl.PdfService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CitaController {

    @Autowired
    private CitaService citaService;
    
    @Autowired
    private MascotaService mascotaService;
    
    @Autowired
    private VeterinarioRepository veterinarioRepository;
    
    @Autowired
    private PdfService pdfService;

    private boolean isSessionValid(HttpSession session) {
        return session.getAttribute("veterinario") != null;
    }

    @GetMapping("/lista_citas")
    public String listarCitas(Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }
        
        List<CitaEntity> listaCitas = citaService.obtenerLasCitas();
        model.addAttribute("citas", listaCitas);
        
        return "lista_citas";
    }

    @GetMapping("/registrar_cita/{id}")
    public String mostrarRegistrarCita(@PathVariable("id") int id, Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        CitaEntity cita = new CitaEntity();
        MascotaEntity mascota = mascotaService.obtenerPorID(id);
        cita.setCodMascota(mascota); // Asignar la mascota a la cita

        List<VeterinarioEntity> veterinarios = veterinarioRepository.findAll();
        model.addAttribute("cita", cita);
        model.addAttribute("veterinarios", veterinarios);
        
        return "registrar_cita"; 
    }

    @PostMapping("/registrar_cita")
    public String registrarCita(@ModelAttribute CitaEntity cita, Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

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
        model.addAttribute("mascotas", mascotas);
        
        List<VeterinarioEntity> veterinarios = veterinarioRepository.findAll();
        model.addAttribute("veterinarios", veterinarios);
        
        citaService.registrarCita(cita, model);
        
        return "redirect:/lista_citas";
    }

    @GetMapping("/actualizar_cita/{id}")
    public String mostrarActualizarCita(Model model, @PathVariable("id") Integer id, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        citaService.verCita(model, id);
        return "actualizar_cita";
    }

    @GetMapping("/detalle_cita/{id}")
    public String mostrarDetalleCita(Model model, @PathVariable("id") Integer id, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        citaService.verCita(model, id);
        return "detalle_cita";
    }

    @GetMapping("/detalle_registrar_inf/{id}")
    public String mostrarDetalleCitaInf(Model model, @PathVariable("id") Integer id, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        CitaEntity cita = citaService.obtenerPorID(id);
        model.addAttribute("cita", cita);
        
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
                                    Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        CitaEntity cita = citaService.obtenerPorID(codCita);
        cita.setInforme(informe);
        cita.setEstado(estado);
        
        citaService.actualizarCitaInforme(cita);
        
        return "redirect:/detalle_cita/" + codCita;
    }

    @GetMapping("/delete_cita/{id}")
    public String eliminarCita(@PathVariable("id") Integer id, Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        citaService.eliminarCita(id);
        
        List<CitaEntity> listaCitas = citaService.obtenerLasCitas();
        model.addAttribute("citas", listaCitas);
        
        return "lista_citas";
    }

    @GetMapping("/generarPDF_Citas")
    public ResponseEntity<InputStreamResource> generarPdfCitas(HttpSession session) throws IOException {
        if (!isSessionValid(session)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        List<CitaEntity> citas = citaService.obtenerLasCitas();
        Map<String, Object> datos = new HashMap<>();
        datos.put("citas", citas);

        ByteArrayInputStream pdfStream = pdfService.generarPdfdeHtml("citasTotales_PDF", datos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citas.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }

    @GetMapping("/generarPDF_CitasPendientes")
    public ResponseEntity<InputStreamResource> generarPdfCitasPendientes(HttpSession session) throws IOException {
        if (!isSessionValid(session)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        List<CitaEntity> citasPendientes = citaService.obtenerCitasPorEstado("Pendiente");
        Map<String, Object> datos = new HashMap<>();
        datos.put("citas", citasPendientes);

        ByteArrayInputStream pdfStream = pdfService.generarPdfdeHtml("citasPendientes_PDF", datos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citas_pendientes.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }

    @GetMapping("/generarPDF_CitasCompletadas")
    public ResponseEntity<InputStreamResource> generarPdfCitasCompletadas(HttpSession session) throws IOException {
        if (!isSessionValid(session)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        List<CitaEntity> citasCompletadas = citaService.obtenerCitasPorEstado("Completado");
        Map<String, Object> datos = new HashMap<>();
        datos.put("citas", citasCompletadas);

        ByteArrayInputStream pdfStream = pdfService.generarPdfdeHtml("citasCompletadas_PDF", datos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citas_completadas.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}
