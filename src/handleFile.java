import sun.util.calendar.BaseCalendar;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class handleFile {

    // Quantas nacionalidades (coluna `nationality`) diferentes existem no arquivo?
    public int q1(){

        File arquivoCSV = null;
        ClassLoader classLoader = getClass().getClassLoader();
        arquivoCSV = new File(classLoader.getResource("resources//data.csv").getFile());
        BufferedReader br = null;
        String[] linha = null;
        List<String> colunaNationality = new ArrayList<String>();
        long nationalitynoDuplicates = 0;
        String line = "";
        String csvSplitBy = ",";

        try
        {
            br = new BufferedReader(new FileReader(arquivoCSV));
            while ((line = br.readLine()) != null) {
                linha = line.split(csvSplitBy);
                colunaNationality.add(linha[14]);
            }

            nationalitynoDuplicates = colunaNationality.stream().distinct().count();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        int len = (int)(long) nationalitynoDuplicates;
        return len;
    }

    // Quantos clubes (coluna `club`) diferentes existem no arquivo?
    // Obs: Existem jogadores sem clube.
    public int q2(File arquivoCSV) {

        BufferedReader br = null;
        String[] linha = null;
        List<String> colunaClub = new ArrayList<String>();
        long clubnoDuplicates = 0;
        String line = "";
        String csvSplitBy = ",";

        try
        {
            br = new BufferedReader(new FileReader(arquivoCSV));
            while ((line = br.readLine()) != null) {
                linha = line.split(csvSplitBy);
                if(linha[3].length() != 0){
                    colunaClub.add(linha[3]);
                }
            }

            clubnoDuplicates = colunaClub.stream().distinct().count();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        int len = (int)(long) clubnoDuplicates;

        return len;
    }

    // Liste o primeiro nome (coluna `full_name`) dos 20 primeiros jogadores.
    public List<String> q3(File arquivoCSV) {

        BufferedReader br = null;
        String[] linha = null;
        String[] primeiroNome = null;
        List<String> colunaFullName = new ArrayList<String>();
        int i = 0;
        String line = "";
        String csvSplitBy = ",";

        try
        {
            br = new BufferedReader(new FileReader(arquivoCSV));
            while ((line = br.readLine()) != null && i <= 20) {
                linha = line.split(csvSplitBy);
                primeiroNome = linha[2].split("\\s");
                colunaFullName.add(primeiroNome[0]);
                i++;
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return colunaFullName;
    }

    // Quem são os top 10 jogadores que possuem as maiores cláusulas de rescisão?
    // (utilize as colunas `full_name` e `eur_release_clause`)
    public List<String> q4(File arquivoCSV) {

        class rescisao implements Comparable<rescisao>{
            private double valorRescisao;
            private String nomeJogador;

            public double getValorRescisao() {
                return valorRescisao;
            }

            public void setValorRescisao(double valorRescisao) {
                this.valorRescisao = valorRescisao;
            }

            public String getNomeJogador() {
                return nomeJogador;
            }

            public void setNomeJogador(String nomeJogador) {
                this.nomeJogador = nomeJogador;
            }

            @Override
            public int compareTo(rescisao outroValor) {
                if (this.valorRescisao > outroValor.getValorRescisao()) {
                    return -1;
                }
                if (this.valorRescisao < outroValor.getValorRescisao()) {
                    return 1;
                }
                return 0;
            }

        }

        List<rescisao> release = new ArrayList<rescisao>();
        List<String> rescisao10 = new ArrayList<String>();

        BufferedReader br = null;
        int i = 0;
        int cont = 0;
        double resc = 0;

        String[] linha = null;

        String line = "";
        String csvSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(arquivoCSV));
            while ((line = br.readLine()) != null) {
                linha = line.split(csvSplitBy);

                if (linha[18].length() != 0) {
                    try {
                        rescisao r = new rescisao();
                        r.setNomeJogador(linha[2]);

                        resc = Double.parseDouble(linha[18]);
                        r.setValorRescisao(resc);

                        release.add(r);
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }

            }

            Collections.sort(release);

            for (rescisao resci : release){
                if(cont <= 9) {
                    rescisao10.add(resci.getNomeJogador());
                }
                if(cont == 10) break;
                cont++;
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return rescisao10;
    }

    // Quem são os 10 jogadores mais velhos (use como critério de desempate o campo `eur_wage`)?
    // (utilize as colunas `full_name` e `birth_date`)
    public List<String> q5(File arquivoCSV) {

        class Idade implements Comparable<Idade>{
            private LocalDate dataNascimento;
            private String nomeJogador;
            private Float criterioDesempate;

            public LocalDate getDataNascimento() {
                return dataNascimento;
            }

            public void setDataNascimento(LocalDate dataNascimento) {
                this.dataNascimento = dataNascimento;
            }

            public String getNomeJogador() {
                return nomeJogador;
            }

            public void setNomeJogador(String nomeJogador) {
                this.nomeJogador = nomeJogador;
            }

            public Float getCriterioDesempate() {
                return criterioDesempate;
            }

            public void setCriterioDesempate(Float criterioDesempate) {
                this.criterioDesempate = criterioDesempate;
            }

            @Override
            public int compareTo(Idade outroValor) {
                if(this.getDataNascimento() == outroValor.getDataNascimento()){
                    if (this.criterioDesempate > outroValor.getCriterioDesempate()) {
                        return -1;
                    }
                    if (this.criterioDesempate < outroValor.getCriterioDesempate()) {
                        return 1;
                    }
                }
                return this.getDataNascimento().compareTo(outroValor.dataNascimento);
            }

        }

        List<Idade> idades = new ArrayList<Idade>();
        List<String> jogadoreVelhos = new ArrayList<String>();

        BufferedReader br = null;

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("uuu-MM-dd");
        LocalDate data = null;

        Float desempate;
        Integer cont = 0;

        String[] linha = null;

        String line = "";
        String csvSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(arquivoCSV));
            while ((line = br.readLine()) != null) {
                linha = line.split(csvSplitBy);

                if (linha[8].length() != 0 && !linha[8].contentEquals("birth_date")) {
                    try {
                        Idade i = new Idade();
                        i.setNomeJogador(linha[2]);
                        data = LocalDate.parse(linha[8], formato);
                        desempate = Float.parseFloat(linha[17]);
                        i.setCriterioDesempate(desempate);
                        i.setDataNascimento(data);

                        idades.add(i);
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }

            }

            Collections.sort(idades);

            for (Idade idade : idades){
                if(cont <= 9) {
                    jogadoreVelhos.add(idade.getNomeJogador());
                }
                if(cont == 10) break;
                cont++;
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return jogadoreVelhos;
    }

    // Conte quantos jogadores existem por idade. Para isso, construa um mapa onde as chaves são as idades e os valores a contagem.
    // (utilize a coluna `age`)
    public Map<Integer, Integer> q6(File arquivoCSV) {

        Map<Integer,Long> mapaIdades = new HashMap<Integer,Long>();
        Map<Integer,Integer> idadesFinal = new HashMap<Integer,Integer>();

        BufferedReader br = null;

        String[] linha = null;
        Integer valor = 0;

        List<Integer> idade = new ArrayList<Integer>();

        String line = "";
        String csvSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(arquivoCSV));
            while ((line = br.readLine()) != null) {
                linha = line.split(csvSplitBy);

                if (linha[6].length() != 0 && !linha[6].contentEquals("age")) {
                        idade.add(Integer.parseInt(linha[6]));
                }
            }
            mapaIdades = idade.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

            for(Map.Entry<Integer, Long> entry: mapaIdades.entrySet()) {
                valor = (int)(long) entry.getValue();
                idadesFinal.put(entry.getKey(),valor);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return idadesFinal;
    }
}
