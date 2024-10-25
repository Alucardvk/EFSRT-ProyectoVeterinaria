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

import com.example.demo.entity.ClienteEntity;
import com.example.demo.entity.MascotaEntity;
import com.example.demo.service.ClienteService;
import com.example.demo.service.MascotaService;
import com.example.demo.service.impl.PdfService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private PdfService pdfService;

    private boolean isSessionValid(HttpSession session) {
        return session.getAttribute("veterinario") != null;
    }

    @GetMapping("/lista_clientes")
    public String listarClientes(Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }
        
        List<ClienteEntity> listaClientes = clienteService.obtenerLosClientes();
        model.addAttribute("clientes", listaClientes);
        
        return "lista_clientes";
    }

    @GetMapping("/registrar_cliente")
    public String mostrarRegistrarCliente(@ModelAttribute ClienteEntity cliente, Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        model.addAttribute("cliente", new ClienteEntity());
        
        List<ClienteEntity> clientes = clienteService.obtenerLosClientes();
        model.addAttribute("clientes", clientes);
        return "registrar_cliente";
    }

    @PostMapping("/registrar_cliente")
    public String registrarCliente(@ModelAttribute ClienteEntity cliente, Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        clienteService.registrarCliente(cliente, model);
        
        return "redirect:/lista_clientes";
    }

    @GetMapping("/actualizar_cliente/{id}")
    public String mostrarActualizarCliente(Model model, @PathVariable("id") Integer id, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        clienteService.verCliente(model, id);
        
        return "actualizar_cliente";
    }

    @GetMapping("/detalle_cliente/{id}")
    public String mostrarDetalleCliente(Model model, @PathVariable("id") Integer id, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        clienteService.verCliente(model, id);
        return "detalle_cliente";
    }

    @GetMapping("/delete_cliente/{id}")
    public String eliminarCliente(@PathVariable("id") Integer id, Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        clienteService.eliminarCliente(id);
        
        List<ClienteEntity> listaClientes = clienteService.obtenerLosClientes();
        model.addAttribute("clientes", listaClientes);
        
        return "lista_clientes";
    }

    @GetMapping("/generarPDF_Cliente")
    public ResponseEntity<InputStreamResource> generarPdfCliente(@RequestParam("clienteId") Integer clienteId, HttpSession session) throws IOException {
        if (!isSessionValid(session)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        ClienteEntity cliente = clienteService.obtenerPorID(clienteId);
        List<MascotaEntity> mascotas = mascotaService.obtenerMascotasPorCliente(cliente.getCodCliente());

        Map<String, Object> datos = new HashMap<>();
        datos.put("cliente", cliente);
        datos.put("mascotas", mascotas); 
        ByteArrayInputStream pdfStream = pdfService.generarPdfdeHtml("clientes_mascotas_pdf", datos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=cliente_mascotas.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }

    @GetMapping("/mostrarFormularioGenerarPDF")
    public String mostrarFormularioGenerarPDF(Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            return "redirect:/";
        }

        List<ClienteEntity> listaClientes = clienteService.obtenerLosClientes();
        model.addAttribute("clientes", listaClientes);
        return "generarPDFCliente";
    }
}
