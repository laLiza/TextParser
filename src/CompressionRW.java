import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class CompressionRW implements IFileEngine
{
  public  String InnerFName;

  public boolean isCompressed(String fileInputPath){
     String fTypePattern = fileInputPath.substring(fileInputPath.length()-3);
     if (fTypePattern.equalsIgnoreCase("zip")){
       return true;
     }
    else{
      return false;
     }
   }

  public void unZip(String  fileInputPath){

    Path source = Paths.get( fileInputPath);
    Path target = Paths.get("C:\\Users\\lizam\\IdeaProjects\\TextParser");

    try {
      unzipFolder(source, target);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void unzipFolder(Path source, Path target) throws IOException {

    try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source.toFile()))) {

      ZipEntry zipEntry = zis.getNextEntry();
      while (zipEntry != null) {
        boolean isDirectory = false;
        InnerFName = "temp/" + zipEntry.getName();
        if (zipEntry.getName().endsWith(File.separator)) {
          isDirectory = true;
        }

        Path newPath = zipSlipProtect(zipEntry, target);

        if (isDirectory) {
          Files.createDirectories(newPath);
        } else {
          if (newPath.getParent() != null) {
            if (Files.notExists(newPath.getParent())) {
              Files.createDirectories(newPath.getParent());
            }
          }
          Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
        }
        zipEntry = zis.getNextEntry();
      }
      zis.closeEntry();

    }

  }

  // protect zip slip attack
  public static Path zipSlipProtect(ZipEntry zipEntry, Path targetDir)
          throws IOException {

    // test zip slip vulnerability
    // Path targetDirResolved = targetDir.resolve("../../" + zipEntry.getName());

    Path targetDirResolved = targetDir.resolve("temp/"+zipEntry.getName());

    // make sure normalized file still has targetDir as its prefix
    // else throws exception
    Path normalizePath = targetDirResolved.normalize();
    if (!normalizePath.startsWith(targetDir)) {
      throw new IOException("Bad zip entry: " + zipEntry.getName());
    }

    return normalizePath;
  }
  public static void Zip(String fileInputPath, String FileOutputPath) throws IOException{
    String sourceFile = fileInputPath;
    FileOutputStream fos = new FileOutputStream(FileOutputPath);
    ZipOutputStream zipOut = new ZipOutputStream(fos);
    File fileToZip = new File(sourceFile);
    FileInputStream fis = new FileInputStream(fileToZip);
    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
    zipOut.putNextEntry(zipEntry);
    byte[] bytes = new byte[1024];
    int length;
    while((length = fis.read(bytes)) >= 0) {
      zipOut.write(bytes, 0, length);
    }
    zipOut.close();
    fis.close();
    fos.close();
  }

    public void readFile ( String fileInputPath) throws Exception{

      InnerFName  = fileInputPath;
      while(isCompressed(InnerFName)){
        unZip(InnerFName);
      }

    }

  public void writeFile ( String fileInputPath) throws Exception{
    Scanner in = new Scanner(System.in);
    System.out.println("Input name of the new zipped file:");
    String fileOutputPath = in.nextLine();
    Zip(fileInputPath, "result/"+fileOutputPath);

  }
}
