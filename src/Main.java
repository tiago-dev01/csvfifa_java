import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        File arquivo;
        int numeroNacionalidades = 0;
        int qtsClubes = 0;
        List<String> primeiroNome = new ArrayList<String>();
        List<String> topRecisoes = new ArrayList<String>();
        List<String> maioresIdades = new ArrayList<String>();
        Map<Integer,Integer> groupIdade = new HashMap<Integer,Integer>();

        openFile of = new openFile();
        arquivo = of.carregaArquivo();

        handleFile hf = new handleFile();
        numeroNacionalidades = hf.q1();
        //qtsClubes = hf.q2(arquivo);
        //primeiroNome = hf.q3(arquivo);
        //topRecisoes = hf.q4(arquivo);
        //maioresIdades = hf.q5(arquivo);
        groupIdade = hf.q6(arquivo);

        System.out.println("Qtd de nacionalidades: " + numeroNacionalidades);
        System.out.println("Qtd de clubes: " + qtsClubes);

    }
}
