import java.util.Arrays;
import java.util.stream.Collectors;

public class Sample05 {
    public static void main(String[] args) {
        var frutas = Arrays.asList("Amora", "Pera", "Abacaxi", "Manga");
        frutas.forEach(System.out::println);
        var frutasMaiusculos = frutas
                .stream()
                .sorted()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println(frutasMaiusculos);

        var frutasComInicialA = frutas
                .stream()
                .map(String::toUpperCase)
                .filter(fruta -> fruta.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Lista das que comecam com A: ");
        System.out.println(frutasComInicialA);

        var frutasComInicialAparte = frutasMaiusculos
                .stream()
                .filter(fruta -> fruta.startsWith("A"))
                .collect(Collectors.toList());
        System.out.println(frutasComInicialAparte);
    }
}
