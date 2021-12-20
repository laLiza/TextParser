import java.io.*;

public class TxtDataReader  {
    FileWriter fw;
    public TxtDataReader(String fName){
        try {
            fw = new FileWriter(fName);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public TxtDataReader(){}
    public void write( String line) throws IOException{
        // fw.write(line);
        System.out.println("wr");
    }
    public void writeLine( String line) throws IOException{
        fw.write(line+"\n");
    }
    public void closeFile()
    {
        try {
            fw.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void read(String fName) throws IOException{
        //System.out.println("TXTRead");
        // Создание объекта FileReader
        FileReader fr = new FileReader(fName);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        TxtDataReader fileWriter = new TxtDataReader("result/res.txt");
        String textRes ="";
        while (line != null) {

            fileWriter.writeLine(MathParser.ParseString(line));
            textRes += (MathParser.ParseString(line)+"\n");


            line = br.readLine();

        }
        fileWriter.closeFile();
        br.close();
        System.out.println(textRes);
    }

}