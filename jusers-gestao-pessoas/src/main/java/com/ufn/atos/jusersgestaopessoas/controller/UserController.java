package com.ufn.atos.jusersgestaopessoas.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ufn.atos.jusersgestaopessoas.model.User;
import com.ufn.atos.jusersgestaopessoas.model.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired // ela serv para nos comunicar com UserRepository
	private UserRepository userRepository;
	
	@GetMapping(path="/index")
	public String userHome() {
	return "index";
	}
	
	@GetMapping(path="/login")
	public String userLogin() {
	return "login";
	}
	
	@GetMapping(path="/cadastro")
	public String formNewUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "cadastro";
	}
	
	// Create
	@PostMapping(path="/cadastro")
	public @ResponseBody String addUser(@RequestParam String name, @RequestParam String email, @RequestParam String role) {
		
		// realiza o incapsulamento dos dasos
		User u = new User(2, name, email, role);
//		u.setName(name);
//		u.setEmail(email);
//		u.setRole(role);
		// salvar o novo Usuario no banco
		userRepository.save(u);
		return "redirect:/users/all";
		
	}
	
	@GetMapping("/all")
	public String listUser(Model model){
		List<User> users = userRepository.findAll();
		model.addAttribute("userList", users);
		return "listauser";
		
	}
	
	
	@GetMapping("/showUpdateForm/{id}")
	public String showUpdateForm(@PathVariable (value = "id") Integer id, Model model) {
		
		User user = userRepository.getById(id);
		
		model.addAttribute("user", user);
		
		return "updateform";
		
	}
	
	// update
	@PostMapping(path="/update")
	public @ResponseBody String updateUser(@RequestParam Integer id, @RequestParam String name, 
			@RequestParam String email) {
		User u = userRepository.findById(id).get();
		if(!name.isEmpty()) {
			u.setName(name);
		}
		if(!email.isEmpty()) {
			u.setEmail(email);
		}
		userRepository.save(u);
		
		return "redirect:/users/all";
	}

	// Delete
	@GetMapping(path="/delete/{id}")
	public String deleteUser(@PathVariable (value = "id") Integer id) {
		userRepository.deleteById(id);
		return "redirect:/users/all";
	}
}

