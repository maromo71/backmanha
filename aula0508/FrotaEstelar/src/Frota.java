import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Frota {
    public List<Nave> naves;

    public Frota(){
        naves = new ArrayList<>();
    }
    public void adicionar(Nave n) {
        naves.add(n);
    }

    Optional<Nave> buscarPorRegistro(String registro) {
        Nave naveEncontrada = null;
        for (Nave n : naves) {
            if (n.registro().equals(registro)) {
                naveEncontrada = n;
                break;
            }
        }
        return Optional.ofNullable(naveEncontrada);
    }
    public boolean remover(String registro){
        Nave naveEncontrada = null;
        for(Nave n: naves){
            if(n.registro().equals(registro)){
                naveEncontrada = n;
                break;
            }
        }
        if(naveEncontrada!=null){
            naves.remove(naveEncontrada);
            System.out.println("Nave removida com sucesso");
            return true;
        }else{
            System.out.println("Nave não encontrada");
            return false;
        }
    }
    public List<Nave> listarTodas(){
        return naves;
    }
}
