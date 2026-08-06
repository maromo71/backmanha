import java.util.ArrayList;
import java.util.List;

public class Prateleira {
    private List<Vinil> discos;

    public Prateleira(){
        //Iniciar a prateleira com uma colecao vazia
        //de discos
        discos = new ArrayList<>();
    }
    public void adicionar(Vinil v){
        discos.add(v);
        System.out.println("Vinil adicionado a colecao");
    }
    public List<Vinil>listarTodos(){
        return discos;
    }
    public Vinil buscarPorTitulo(String titulo){
        for(Vinil vinil : discos){
            if(vinil.titulo().equals(titulo)){
                return vinil;
            }
        }
        return null;
    }

}
