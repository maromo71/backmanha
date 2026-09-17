package com.lab.jpa.colecaovinisjpa.config;
import com.lab.jpa.colecaovinisjpa.model.Usuario;
import com.lab.jpa.colecaovinisjpa.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UsuarioRepository repository;

    public DataInitializer(UsuarioRepository repository){
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        var scanner = new Scanner(System.in);
        var continuar = true;
        System.out.println("-----------------------------------");
        System.out.println("| Bem Vindo ao Gestor de Usuarios |");
        System.out.println("-----------------------------------");
        while(continuar){
            System.out.println("\nMenu de Opções");
            System.out.println("1. Cadastrar Usuario..");
            System.out.println("2. listar Usuarios..");
            System.out.println("0. SAIR ");
            System.out.println("Escolha sua opcao: ");
            var opcao = Integer.parseInt(scanner.nextLine());
            continuar = switch (opcao){
                case 1 -> {
                    cadastrar(scanner);
                    yield true;
                }
                case 2 -> {
                    listar();
                    yield true;
                }
                case 3 ->{
                    listarPorId(scanner);
                    yield true;
                }
                case 0 -> {
                    System.out.println("Fim do Programa");
                    yield false;
                }
                default -> {
                    System.out.println("Opcao invalida! Tente novamente");
                    yield true;
                }
            };
        }
        System.out.println("Aplicacao finalizada");
    }

    private void cadastrar(Scanner scanner){
        System.out.println("Digite o nome do usuário: ");
        var nome = scanner.nextLine();
        var usuario = new Usuario();
        usuario.setNome(nome);
        repository.save(usuario);
        System.out.println("Usuario >>> " + nome + " salvo com sucessp");
    }

    private void listar(){
        var usuarios = repository.findAll();
        if(usuarios.isEmpty()){
            System.out.println("Nenhum usuario cadastrado..");
        }else{
            System.out.println("Lista de Usuarios do Banco: ");
            usuarios.forEach(System.out::println);
            System.out.println("----------------------------");
        }
    }

    public void listarPorId(Scanner scanner){
        System.out.println("Digite o id do usuario a procurar: ");
        Long id = Long.parseLong(scanner.nextLine());
        var usuario = repository.findById(id);
        if (usuario != null){
            System.out.println("Usuario: " + usuario);
        }
    }

}
