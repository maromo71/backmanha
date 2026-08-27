package com.lab.jpa.colecaovinisjpa.model;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
}
