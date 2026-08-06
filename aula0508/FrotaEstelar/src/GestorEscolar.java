import java.util.Scanner;

public class GestorEscolar {

    Frota frota = new Frota();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorEscolar gerenciar = new GestorEscolar();
        int opcao = 0;
        do{
            System.out.println("+==============================+");
            System.out.println("+   Menu do Gestor de Frotas   +");
            System.out.println("+==============================+");
            System.out.println("+   1. Cadastrar Nova Nave     +");
            System.out.println("+   2. Listar Frota            +");
            System.out.println("+   3. Consultar por Registro  +");
            System.out.println("+   4. Atualizar Status Nave   +");
            System.out.println("+   5. Remover da Frota        +");
            System.out.println("+   6. Relatório de Prontidão  +");
            System.out.println("+   7. SAIR                    +");
            System.out.printf("+   Escolha sua opcao: ");
            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao){
                case 1 -> gerenciar.execCadastrar();
                case 2 -> gerenciar.execListar();
                case 3 -> gerenciar.execConsultar();
                case 4 -> gerenciar.execAtualizarStatus();
                case 5 -> gerenciar.execRemover();
                case 6 -> gerenciar.execRelatorioProntidao();
                case 7 -> System.out.println("FIM");
                default -> System.out.println("Opcao invalida");
            }
        }while(opcao != 7);
    }
    public void execCadastrar() {
        //Cadastrar Nave: Capturar dados e validar a autonomia (não pode ser negativa).
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o Registro da Nave: ");
        String registro = sc.nextLine();
        System.out.println("Digite o Modelo da Nave: ");
        String modelo = sc.nextLine();
        int autonomiaAnosLuz = 0;
        do {
            System.out.println("Digite a Autonomia em Anos Luz");
            autonomiaAnosLuz = Integer.parseInt(sc.nextLine());
            if (autonomiaAnosLuz < 0) {
                System.out.println("Autononia em ANOS LUZ não pode ser negativa");
            }
        } while (autonomiaAnosLuz < 0);
        System.out.println("Status da Nave definida como Operacional");
        Nave nave = new Nave(registro, modelo, autonomiaAnosLuz, StatusNave.OPERACIONAL);
        System.out.println("Nave adicionada a frota");
        frota.adicionar(nave);
    }

    public void execListar(){
        //Listar Frota: Exibir todas as naves cadastradas usando var para iterar.
        System.out.println("Lista das Naves Cadastradas");
        for(var nave : frota.naves){
            System.out.println("Nave Registro: " + nave.registro());
            System.out.println("Modelo: " + nave.modelo());
            System.out.println("Autonomia em Anos Luz: " + nave.autonomiaAnosLuz());
            System.out.println("Nível de Prontidão: " + nave.verificarNivelProntidao());
        }
    }

    public void execConsultar(){
        //Consultar por Registro: Buscar uma nave específica e exibir seus detalhes técnicos.
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o Registro a ser Encontrado: ");
        String registroBuscado = sc.nextLine();
        for(var nave : frota.listarTodas()){
            if(registroBuscado.equals(nave.registro())){
                System.out.println("Nave Registro: " + nave.registro());
                System.out.println("Modelo: " + nave.modelo());
                System.out.println("Autonomia em Anos Luz: " + nave.autonomiaAnosLuz());
                System.out.println("Nível de Prontidão: " + nave.verificarNivelProntidao());
                return;
            }
        }
        System.out.println("Nave não encontrada");
    }

    public void execAtualizarStatus(){
        //Atualizar Status: Localizar uma nave e permitir alterar
        // seu StatusNave através de um sub-menu
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o Registro a ser Encontrado: ");
        String registroBuscado = sc.nextLine();
        Nave naveOriginal = null;
        for(var nave : frota.listarTodas()){
            if(registroBuscado.equals(nave.registro())){
               naveOriginal = nave;
               break;
            }
        }
        if(naveOriginal==null){
            System.out.println("Nave não encontrada");
        }else{
            int opcao = 1;
            System.out.println("Escolha o Status pelo Numero");
            System.out.println("1. Operacional");
            System.out.println("2. Em Reparo");
            System.out.println("3. Desativada");
            System.out.println("4. Em Missão");
            System.out.println("5. Em Teste de Vôo");
            System.out.println("Digite o numero: ");
            opcao = Integer.parseInt(sc.nextLine());
            StatusNave statusNave = switch (opcao){
                case 2 -> StatusNave.EM_REPARO;
                case 3 -> StatusNave.DESATIVADA;
                case 4 -> StatusNave.EM_MISSAO;
                case 5 -> StatusNave.TESTE_DE_VOO;
                default -> StatusNave.OPERACIONAL;
            };
            Nave naveComAlteracao = naveOriginal.alterarStatus(statusNave);
            frota.naves.remove(naveOriginal);
            frota.adicionar(naveComAlteracao);
            System.out.println("Status da Nave Alterada");
        }
    }

    public void execRemover(){
        //Remover da Frota: Excluir uma nave do sistema pelo número de registro.
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o Registro a ser Encontrado: ");
        String registroBuscado = sc.nextLine();
        Nave naveEncontrada = null;
        for(var nave : frota.listarTodas()) {
            if (registroBuscado.equals(nave.registro())) {
                naveEncontrada = nave;
                System.out.println("Nave Removida com Sucesso");
                frota.naves.remove(naveEncontrada);
                return;
            }
        }
        System.out.println("Nave Não Encontrada");
    }

    public void execRelatorioProntidao(){
        //Relatório de Prontidão: Gerar um resumo estatístico
        // simplificado da frota (ex: total de naves prontas vs. em
        // manutenção)
        if (frota.naves.isEmpty()) {
            System.out.println("A frota está vazia no momento.");
            return;
        }
        System.out.println("=== RELATÓRIO DE PRONTIDÃO DA FROTA ===");
        System.out.println("Total de Naves: " + frota.naves.size());
        System.out.println("----------------------------------------");
        // Percorre cada status do Enum e conta quantas naves correspondem a ele
        for (StatusNave status : StatusNave.values()) {
            int qtd = 0;
            for (Nave nave : frota.naves) {
                if (nave.status() == status) {
                    qtd++;
                }
            }
            // Exibe apenas os status que possuem pelo menos uma nave (opcional)
            if (qtd > 0) {
                System.out.printf("- %s: %d nave(s)%n", status, qtd);
            }
        }
    }
}
