package com.lab.jpa.colecaovinisjpa.repository;

import com.lab.jpa.colecaovinisjpa.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> id(Long id);
}
