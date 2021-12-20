import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class EncryptionRW {
    public String fileOutputPath = "temp/tempenc";
    public boolean isEncrypted(String fileInputPath){
        String fTypePattern = fileInputPath.substring(fileInputPath.length()-3);
        System.out.println(fTypePattern);
        if (fTypePattern.equalsIgnoreCase("enc")){
            return true;
        }
        else{
            return false;
        }
    }
    public static void encryptedFile(String secretKey, String fileInputPath, String fileOutPath)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException,
            IllegalBlockSizeException, BadPaddingException {
        var key = new SecretKeySpec(secretKey.getBytes(), "AES");
        var cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        var fileInput = new File(fileInputPath);
        var inputStream = new FileInputStream(fileInput);
        var inputBytes = new byte[(int) fileInput.length()];
        inputStream.read(inputBytes);

        var outputBytes = cipher.doFinal(inputBytes);

        var fileEncryptOut = new File(fileOutPath);
        var outputStream = new FileOutputStream(fileEncryptOut);
        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();

    }

    public static void decryptedFile (String secretKey, String fileInputPath, String fileOutPath)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException,
            IllegalBlockSizeException, BadPaddingException {
        var key = new SecretKeySpec(secretKey.getBytes(), "AES");
        var cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        var fileInput = new File(fileInputPath);
        var inputStream = new FileInputStream(fileInput);
        var inputBytes = new byte[(int) fileInput.length()];
        inputStream.read(inputBytes);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        var fileEncryptOut = new File(fileOutPath);
        var outputStream = new FileOutputStream(fileEncryptOut);
        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();

    }

    public void readFile (String fileInputPath) throws Exception{
        if(isEncrypted(fileInputPath)){
            Scanner in = new Scanner(System.in);
            System.out.println("Input secret key to decrypt the file:");
            String key = in.nextLine();// possible size of key: 128/192/256
            decryptedFile(key, fileInputPath, fileOutputPath);
        }

    }

    public void writeFile ( String fileInputPath) throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.println("Input secret key for the new encrypted file:");
        String key = in.nextLine();
        System.out.println("Input name of the new encrypted file:");
        String fileOutputPath = in.nextLine();
       // var key = "KeyIsVerySecret2";// possible size of key: 128/192/256
        encryptedFile(key,  fileInputPath, fileOutputPath);
    }


}
