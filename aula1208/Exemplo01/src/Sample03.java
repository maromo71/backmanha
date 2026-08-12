import org.w3c.dom.ls.LSOutput;

import java.util.stream.IntStream;

public class Sample03 {
    public static void main(String[] args) {
        //Programacao funcional - novidade
        int[] valores = {1, 2, 4, 3, 5, 7, 9, 6, 8, 12, 15, 21};
        //iteração interna imprimindo os elementos já ordenados
        IntStream.of(valores)
                .sorted()
                .forEach(valor -> System.out.printf("%d ", valor));
        System.out.println(); //pular uma linha
        //maior valor
        int maior = IntStream.of(valores).max().getAsInt();
        int menor = IntStream.of(valores).min().getAsInt();
        int somaComum = IntStream.of(valores).sum();
        System.out.println("Soma dos valores: " + somaComum);
        System.out.println("Maior valor: " + maior);
        System.out.println("Menor valor: " + menor);
        int somaComReduce = IntStream.of(valores).reduce(0, (x, y) -> x + y);
        System.out.println("Soma com reduce: " + somaComReduce);
        int somaQuadrado = IntStream.of(valores).reduce(0, (x, y)-> x + y * y);
        System.out.println("Soma dos quadrados: "+ somaQuadrado);
        System.out.println();
        //Ordenar a lista mostrando apenas os pares da lista
        System.out.println("Valores pares ordenados");
        IntStream.of(valores)
                .filter(valor -> valor % 2 == 0)
                .sorted()
                .forEach(valor -> System.out.printf("%d ", valor));

    }
}
