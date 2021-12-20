
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class EncryptControlTest {

    @Test
    public void encryptionTest() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException, BadPaddingException, InvalidKeyException {

        PathControl inputPath = new PathControl("/Users/liza/IdeaProjects/ReadWrite/TestFiles/testFile.txt");
        inputPath.CreateDirs();
        PathControl outputFile;

        FileReader fr = new FileReader(inputPath.getFilePath());
        Scanner scan = new Scanner(fr);

        String inputFileLine = scan.nextLine();
        fr.close();

        EncryptControl encryptControl = new EncryptControl("KeyIsVerySecret1");
        outputFile = encryptControl.EncryptFile(inputPath,inputPath.TempDir());
        outputFile = encryptControl.DecryptFile(outputFile,inputPath.TempDir());

        fr = new FileReader(outputFile.getFilePath());
        scan = new Scanner(fr);

        String outputFileLine = scan.nextLine();

        inputPath.ClearOutput();
        inputPath.ClearTemp();

        Assert.assertEquals(inputFileLine,outputFileLine);

    }
}