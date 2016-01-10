public class Main {
    public static void main(String[] args) {
        String inputPath = "D:\\Input";
        String outputPath = "D:\\Output";
        FileHelper helper = new FileHelper();
        helper.createFiles(inputPath, outputPath);
    }
}