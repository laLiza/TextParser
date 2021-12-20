import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;




public class CompressionTest {

    @Test
    public void zipTest() throws IOException {

        PathControl inputPath = new PathControl("/Users/дшяф/IdeaProjects/ReadWrite/TestFiles/testFile.txt");
        inputPath.CreateDirs();
        PathControl outputFile;

        FileReader fr = new FileReader(inputPath.getFilePath());
        Scanner scan = new Scanner(fr);

        String inputFileLine = scan.nextLine();
        fr.close();

        outputFile = ZipControl.Zip(inputPath,inputPath.TempDir(),"testZip.zip");
        outputFile = ZipControl.Unzip(outputFile,inputPath.TempDir());

        fr = new FileReader(outputFile.getFilePath());
        scan = new Scanner(fr);

        String outputFileLine = scan.nextLine();

        inputPath.ClearOutput();
        inputPath.ClearTemp();

        Assert.assertEquals(inputFileLine,outputFileLine);

    }

    @Test
    public void zipTest_UnzipNotZip() {

        PathControl inputPath = new PathControl("/Users/liza/IdeaProjects/ReadWrite/TestFiles/testFile.txt");
        inputPath.CreateDirs();

        try {
            ZipControl.Unzip(inputPath,inputPath.TempDir());
            Assert.fail("Try to unzip not a zip archive");

        } catch (IOException e) {

            inputPath.ClearOutput();
            inputPath.ClearTemp();

        }

    }

}