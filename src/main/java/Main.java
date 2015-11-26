public class Main {
    public static void main(String[] args) {
        String inputPath = "D:\\Input";
        String outputPath = "D:\\Output";
        FileUtil fileUtil = new FileUtil();
        fileUtil.createFiles(inputPath, outputPath);
    }
}
