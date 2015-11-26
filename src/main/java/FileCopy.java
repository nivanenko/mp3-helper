import java.io.*;

public class FileCopy {
    private static boolean checkForExist = true;

    public static void main(String[] args) {
        File[] files = listFiles(args[1].trim(), args[0].trim());
        if (files == null) {
            System.out.println("Sorry!!! No file to copy");
            return;
        }


        File toDir = new File(args[2].trim());
        if (!toDir.exists()) {
            if (!toDir.mkdir()){
                System.out.println("Sorry!!! Destination Folder Invalid");
                return;
            } else {
                System.out.println("Created new Folder " + toDir.getAbsolutePath());
            }

        }
        if (!toDir.isDirectory()) {
            System.out.println("Sorry!!! Destination is not FileCopy directory");
        }
        for (File file : files) {
            try {
                copy(file, new File(toDir.getAbsolutePath() + File.separator + file.getName()));
            } catch (IOException e) {
                System.out.println("Sorry!!! Unable to copy. Exception Occured " + e );
            }
        }
    }

    private static void copy(File inputFile, File outputFile) throws IOException {

        if (checkForExist) {
            if (outputFile.exists()) {
                boolean proceed = false;
                do {
                    System.out.println("Warning!!! Output file exist. Replace it? Yes(y) / No(n) / Yes to all (FileCopy)");
                    BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
                    String ans = consoleInput.readLine().toLowerCase();
                    if (ans.startsWith("n")) {
                        System.out.println(inputFile.getName() + " Aborted");
                        return;
                    }
                    if (ans.startsWith("FileCopy")) {
                        checkForExist = false;
                        proceed = true;
                    }
                    if (ans.startsWith("y")) {
                        proceed = true;
                    }
                } while(!proceed);
            }
        }

        System.out.println(inputFile.getName() + " Coping....");
        FileReader in = new FileReader(inputFile);
        FileWriter out = new FileWriter(outputFile);
        int c;

        while ((c = in.read()) != -1)
            out.write(c);

        in.close();
        out.close();

        System.out.println(inputFile.getName() + " Copied");

    }

    private static File[] listFiles(String dirName, String type) {
        File dir = new File(dirName);
        FilenameFilter filter = new FilenameFilter(type);
        File[] files = dir.listFiles(filter);
        if (files.length < 1 ) return null;
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
        return files;
    }
}

class FilenameFilter implements java.io.FilenameFilter {

    private String type;

    public FilenameFilter(String type) {
        if (type.startsWith(".")) {
            this.type = type.substring(1);
        } else if (type.startsWith("*.")) {
            this.type = type.substring(2);
        } else {
            this.type = type;
        }
        System.out.println(this.type);

    }

    public boolean accept(File dir, String name) {
        return name.endsWith("." + type);

    }
}