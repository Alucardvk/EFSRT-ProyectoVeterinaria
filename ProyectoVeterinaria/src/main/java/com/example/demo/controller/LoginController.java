package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.VeterinarioEntity;
import com.example.demo.repository.VeterinarioRepository;
import com.example.demo.util.Utilitario;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private VeterinarioRepository veterinarioRepository;

	@GetMapping("/")
	public String showLogin(Model model) {
		model.addAttribute("veterinario", new VeterinarioEntity());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute VeterinarioEntity veterinario, HttpSession session, Model model) {
		VeterinarioEntity veterinarioEncontrado = veterinarioRepository.findByCorreo(veterinario.getCorreo());

		if (veterinarioEncontrado != null
				&& Utilitario.checkPassword(veterinario.getContrasenia(), veterinarioEncontrado.getContrasenia())) {
			session.setAttribute("veterinario", veterinarioEncontrado);
			return "redirect:/menu";
		} else {
			model.addAttribute("errorMessage", "Correo o contraseña incorrectos.");
			model.addAttribute("veterinario", new VeterinarioEntity());
			return "login";
		}
	}

	@GetMapping("/menu")
	public String mostrarMenu(HttpSession session, Model model) {
		VeterinarioEntity veterinario = (VeterinarioEntity) session.getAttribute("veterinario");
		if (veterinario == null) {
			return "redirect:/";
		}
		return "menu";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}