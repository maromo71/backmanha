import java.util.*;
import java.util.stream.Collectors;

public class CalculadoraNota {
    private final List<ItemNota> itens = new ArrayList<>();

    //Adicionar um item na nota fiscal
    public void adicionarItem(ItemNota item){
        itens.add(item);
    }

    public List<ItemNota> getItens(){
        //Retornar uma visao da lista (não modificável)
        return Collections.unmodifiableList(itens);
    }

    //Tarefa 1: Cálculo do Total geral da nota com uso de Stream
    public double calcularTotalGeral(){
        return itens.stream()
                .mapToDouble(ItemNota::calcularSubtotal)
                .sum();
    }
    //Tarefa 2: Filtro (precoUnitario > 100).
    //Mapear os nomes e colocar em ordem alfabetica
    public List<String> listarProdutosPremium(){
        return itens.stream()
                .filter(item -> item.precoUnitario() > 100)
                .map(ItemNota::produto)
                .sorted()
                .collect(Collectors.toList());
    }

    //Tarefa 3: Uso do Optional para devolver ou nao o item mais caro
    // pode ser que nao exista.
    public Optional<ItemNota> encontrarItemMaisCaro(){
        return itens.stream()
                .max(Comparator.comparingDouble(ItemNota::precoUnitario));
    }

}
