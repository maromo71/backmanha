

import java.util.Scanner;

public class GerenciarColecao {
    Prateleira prateleleira = new Prateleira();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GerenciarColecao gerenciarColecao = new GerenciarColecao();
        int opcao = 0;
        do{
            System.out.println("Digite sua opcao: ");
            System.out.println("1. Cadastrar Vinil");
            System.out.println("2. Procurar Vinil");
            System.out.println("3. Mostrar a lista de Vinis");
            System.out.println("9. Sair");
            opcao = Integer.parseInt(sc.nextLine());
            switch (opcao){
                case 1 -> gerenciarColecao.execCadastrar();
                case 2 -> gerenciarColecao.execConsultar();
                case 3 -> gerenciarColecao.execListar();
                case 9 -> System.out.println("Fim");
                default -> System.out.println("Opção Inválida");
            }
        }while(opcao != 9);
    }

    public void execCadastrar(){
        //Cadastrar um novo vinil a nossa colecao
        Scanner sc = new Scanner(System.in);
        String titulo, artista;
        int ano;
        EstadoDeConservacao estado;
        System.out.println("Digite o titulo do vinil a ser cadastrado: ");
        titulo = sc.nextLine();
        System.out.println("Digite o nome do artista: ");
        artista = sc.nextLine();
        System.out.println("Digite o ano do Vinil");
        ano = Integer.parseInt(sc.nextLine());
        System.out.println("Digite o estado de conserva");
        System.out.println("1. Novo");
        System.out.println("2. Excelente");
        System.out.println("3. Usado");
        System.out.println("4. Raro");
        System.out.println("5. Danificado");
        int opcao = Integer.parseInt(sc.nextLine());
        estado = switch (opcao){
            case 2 -> EstadoDeConservacao.EXCELENTE;
            case 3 -> EstadoDeConservacao.USADO;
            case 4-> EstadoDeConservacao.RARO;
            case 5-> EstadoDeConservacao.DANIFICADO;
            default -> EstadoDeConservacao.NOVO;
        };
        var vinil = new Vinil(titulo, artista, ano, estado);
        prateleleira.adicionar(vinil);

    }

    public void execConsultar(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o titulo a ser encontrado: ");
        String tituloBuscado = sc.nextLine();
        var vinil = prateleleira.buscarPorTitulo(tituloBuscado);
        if(vinil !=null){
            System.out.println("Vinil Econtrado");
            System.out.println("Titulo: " + vinil.titulo());
            System.out.println("Artista: " + vinil.artista());
            System.out.println("Ano: " + vinil.ano());
            System.out.println("Estado de Conservação: " + vinil.estado());
        }else{
            System.out.println("Titulo nao encontrado");
        }
    }

    public void execListar(){
        var lista = prateleleira.listarTodos();
        for(Vinil vinil : lista){
            System.out.println("Disco Titulo: " + vinil.titulo());
            System.out.println("Artista: " + vinil.artista());
            System.out.println("Ano de Producao: " + vinil.ano());
            System.out.println("Estado de Conservacao: " + vinil.estado());
        }
    }
}
