package com.lab.jpa.colecaovinisjpa.controller;
import com.lab.jpa.colecaovinisjpa.model.Usuario;
import com.lab.jpa.colecaovinisjpa.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }
    @PostMapping
    @ResponseBody
    public Usuario criar(@RequestBody Usuario usuario){
        return repository.save(usuario);
    }

    @GetMapping
    @ResponseBody
    public List<Usuario>listar(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Usuario listarPorId(@PathVariable("id")Long id){
        return repository.findById(id).orElse(null);
    }
}
