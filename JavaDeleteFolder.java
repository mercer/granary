import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Exception;

public class JavaDeleteFolder {

    public static void main(String[] args) throws Exception {
        File file = new File(args[0]);
//        if(file.isDirectory()){
        delete(file);
        System.out.println("deleting... " + file.getAbsolutePath());
        //       }
    }



    private static void delete(File f) throws IOException {
        if(!f.exists()) {
            System.out.println("[WARNING] File does not exist: " + f);

        }
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                delete(c);
        }
        if (!f.delete()){
            System.out.println("[WARNING] Failed to delete file: " + f);
		}
    }
}
