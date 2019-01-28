import java.io.File;

public class openFile {

    public File carregaArquivo(){
        try
        {
            File csvFile = null;
            ClassLoader classLoader = getClass().getClassLoader();
            csvFile = new File(classLoader.getResource("resources//data.csv").getFile());

            return csvFile;
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }

    }
}
