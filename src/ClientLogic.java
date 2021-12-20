import java.io.IOException;

public class ClientLogic {
    public static void main(String[] args) throws Exception {
        FileEngine FEnginer = new FileEngine();
        FEnginer.FolderControl();
        FEnginer.ProcessFile();
    }
}
