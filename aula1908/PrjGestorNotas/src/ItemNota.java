//1. Modelo de Dados
// Representa os itens de uma nota fiscal
public record ItemNota(
        String produto,
        int quantidade,
        double precoUnitario) {
    //Método para calcular o subtotal de cada item da nota
    public double calcularSubtotal(){
        return quantidade * precoUnitario;
    }
}
