import java.util.Scanner;

public class GestorNota {
    private  static final Scanner scanner = new Scanner(System.in);
    private  static final CalculadoraNota calculadora = new CalculadoraNota();

    public static void main(String[] args) {
        var executando = true;
        while(executando){
            exibirMenu();
            var opcao = Integer.parseInt(scanner.nextLine());
            executando = switch (opcao){
                case 1-> {execAdicionar();yield true; }
                case 2-> {execCalcularTotal(); yield true; }
                case 3-> {execListarPremium(); yield true; }
                case 4-> {execEncontrarMaisCaro(); yield true; }
                case 5-> {
                    System.out.println("Fim do programa");
                    yield false;
                }
                default -> {
                    System.out.println("Opcao invalida");
                    yield true;
                }
            };
        }
    }
    private static void exibirMenu(){
        System.out.println("\t\t=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("\t\tGESTAO DE NOTA FISCAL (SINTAXE MODERNA)");
        System.out.println("\t\t1.. Adicionar item na nota");
        System.out.println("\t\t2.. Calcular Valor Total da Nota");
        System.out.println("\t\t3.. Listar Itens Premium (Preco > 100)");
        System.out.println("\t\t4.. Visualizar Item Mais Caro (Optional)");
        System.out.println("\t\t5.. SAIR");
        System.out.println("\t\tEscolha sua opcao: ");
    }
    public static void execAdicionar(){
        try{
            System.out.println("Digite o nome do produto: ");
            var produto = scanner.nextLine();
            System.out.println("Digite a quantidade de compra: ");
            var quantidade = Integer.parseInt(scanner.nextLine());
            System.out.println("Digite o preco unitario do produto: ");
            var precoUnitario = Double.parseDouble(scanner.nextLine());
            calculadora.adicionarItem(new ItemNota(produto, quantidade, precoUnitario));
            System.out.println("Item de Nota Cadastrado com Sucesso");
        }catch(NumberFormatException ex){
            System.out.println("Entrada numérica inválida. Digite corretamente");
        }
    }
    private static void execCalcularTotal(){
        var total = calculadora.calcularTotalGeral();
        System.out.println("Total geral da nota R$ " + total);
    }
    private static void execEncontrarMaisCaro(){
        //O método retorna um Optional que deve ser tratado
        var itemMaisCaro = calculadora.encontrarItemMaisCaro();
        itemMaisCaro.ifPresentOrElse(
                item -> System.out.printf("Item mais caro %s, preco: %.2f%n",
                        item.produto(), item.precoUnitario()),
                () -> System.out.println("A nota fiscal esta vazia.")
        );
    }

    private static void execListarPremium(){
        var premium = calculadora.listarProdutosPremium();
        if(premium.isEmpty()){
            System.out.println("Nenhum produto premium encontrado");
        }else{
            System.out.println("Itens premium encotrados");
            premium.forEach(produto -> System.out.println(" * " + produto));
        }
    }


}
