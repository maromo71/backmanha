import java.util.ArrayList;
import java.util.List;

public class Sample01 {
    public static void main(String[] args) {
        //Lista imutável de frutas
        var lista = List.of("Banana", "Pera", "Morango", "Abacate");
        //lista.add("Pera2"); --> não é possível neste tipo de lista
        //pois a característica da imutabilidade opera em List.of
        for(String fruta : lista){
            System.out.println(fruta);
        }

        //lista mutável de frutas
        var frutas = new ArrayList<String>();
        frutas.add("Fruta do conde");
        frutas.add("Rocambole");
        frutas.add("Melão");

        frutas.remove("Rocambole");

        for(String fruta : frutas){
            System.out.println(fruta);
        }
    }
}
