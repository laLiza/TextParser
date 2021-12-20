import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileEngine {
    public static String CurrentFileName;
   //public boolean isCurrentFileCompressed(){return fa
    // lse;}

    public boolean isCurrentFileEncrypted(){return false;}
    public String CheckFileFormat(String fileName){

        String fTypeExtention ;
        int DotIndex = fileName.lastIndexOf(".");
        fTypeExtention = fileName.substring(DotIndex+1);


        return fTypeExtention;
    }
    public void ProcessFile() throws Exception {

        String fileName = "test.txt";
        CompressionRW ComprRW = new CompressionRW();
        EncryptionRW EncrRW  = new EncryptionRW();
        TxtDataReader TxtRW  = new TxtDataReader();

        switch (CheckFileFormat(fileName)) {
            case ("zip"): {
                ComprRW.readFile(fileName);
                fileName = ComprRW.InnerFName;
                if (CheckFileFormat(fileName).equalsIgnoreCase("enc")) {
                    EncrRW.readFile(fileName);
                    fileName = EncrRW.fileOutputPath;

                }
                    TxtRW.read(fileName);

                break;
            }
            case ("enc"): {
                EncrRW.readFile(fileName);
                fileName = EncrRW.fileOutputPath;
                try {
                    ComprRW.readFile(fileName);

                }
                catch (Exception e){

                }
                finally {
                    TxtRW.read(fileName);
                }
                break;
            }
            default: {
                TxtRW.read(fileName);
            }
        }

    }
    public static void deleteFolder(File file) throws IOException {

        if (file.isDirectory()) {

            //directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();

            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    deleteFolder(fileDelete);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
//                    System.out.println("Directory is deleted : " + file.getAbsolutePath());
                }
            }

        } else {
            //if file, then delete it
            file.delete();
//            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }

    public static void FolderControl() throws IOException {
        deleteFolder(new File("result"));
        deleteFolder(new File("temp"));
        Files.deleteIfExists(Paths.get("result"));
        Files.createDirectory(Paths.get("result"));
        Files.createDirectory(Paths.get("temp"));
    }
}
