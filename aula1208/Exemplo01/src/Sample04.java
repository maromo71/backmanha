import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Sample04 {
    public static void main(String[] args) {
        Integer[] values = { 23, 11, 10, 101, 2, 90, 26};
        //Imprimir como lista
        System.out.printf("Lista atual: %s\n", Arrays.asList(values));
        //Nova variavel. Uma copia da lista original ja ordenada
        var listaOrdenada = Arrays
                .stream(values)
                .sorted()
                .collect(Collectors.toList());
        //imprimindo a lista
        System.out.println("Lista ordenada: ");
        listaOrdenada.forEach(System.out::println);
        //pares ordenados maiores que 10
        var paresOrdenadosMaioresQ10 = Arrays
                        .stream(values)
                        .filter(v -> v % 2 == 0)
                        .filter(v -> v > 10)
                        .sorted()
                        .collect(Collectors.toList());
        System.out.println(paresOrdenadosMaioresQ10);




    }
}
