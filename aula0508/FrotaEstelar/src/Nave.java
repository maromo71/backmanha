public record Nave(
        String registro,
        String modelo,
        int autonomiaAnosLuz,
        StatusNave status
) {
    public String verificarNivelProntidao(){
        String nivel = switch (status){
            case OPERACIONAL -> "Pronta para Salto";
            case EM_REPARO -> "Doca Seca";
            case DESATIVADA -> "Nave foi Desativada";
            case EM_MISSAO -> "Nave está em Missão";
            case TESTE_DE_VOO -> "Nave atualmente em teste de vôo";
        };
        return nivel;
    }
    public Nave alterarStatus(StatusNave novoStatus) {
        return new Nave(this.registro, this.modelo, this.autonomiaAnosLuz, novoStatus);
    }
}
