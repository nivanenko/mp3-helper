import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileUtil {
    private ArrayList<Mp3File> getMp3Files(String path) {
        File directory = new File(path);
        File[] fileList = directory.listFiles();
        ArrayList<Mp3File> mp3Files = new ArrayList<>();
        if (fileList == null) return null;

        try {
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isFile()) {
                    mp3Files.add(i, new Mp3File(fileList[i].getAbsolutePath()));
                }
            }
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        } catch (UnsupportedTagException e) {
            System.out.println("Unsupported tag error: " + e.getMessage());
        } catch (InvalidDataException e) {
            System.out.println("Invalid data error: " + e.getMessage());
        }
        return mp3Files;
    }

    private ArrayList<String> getPathList(String inputPath) {
        File directory = new File(inputPath);
        File[] files = directory.listFiles();
        if (files == null) return null;

        ArrayList<String> pathList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                pathList.add(i, files[i].getAbsolutePath());
            }
        }
        return pathList;
    }

    public void createFiles(String inputPath, String outputPath) {
        ArrayList<Mp3File> fileList = getMp3Files(inputPath);
        ArrayList<String> pathList = getPathList(inputPath);
        File file;

        if (fileList != null && pathList != null) {
            try {
                for (int i = 0; i < fileList.size(); i++) {
                    if (fileList.get(i).hasId3v2Tag()) {
                        ID3v2 tag = fileList.get(i).getId3v2Tag();
                        String artist = tag.getArtist();
                        String album = tag.getAlbum().replaceAll("[\\-\\+\\.\\^\\?:,]", "");
                        String title = tag.getTitle().replaceAll("[\\-\\+\\.\\^\\?:,]", "");
                        String track = tag.getTrack();
                        track = track.substring(0, 1);

                        file = new File(outputPath + File.separator + artist);
                        String artistPath = file.getAbsolutePath();

                        if (!file.exists()) {
                            file.mkdirs();
                        }

                        file = new File(artistPath + File.separator + album);
                        if (!file.exists()) {
                            file.mkdirs();
                        }

                        Path destPath = Paths.get(file.getAbsolutePath() +
                                File.separator + track + ". " + artist + " - " + title + ".mp3");
                        destPath.normalize();
                        File destFile = destPath.toFile();

                        // Creating temp files
                        if (!destFile.exists()) destFile.createNewFile();

                        // Save MP3 into created temp files
                        fileList.get(i).save(destFile.getAbsolutePath());
                    }
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}