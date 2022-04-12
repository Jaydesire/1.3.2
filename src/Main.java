import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        //Создать три экземпляра класса GameProgress
        GameProgress save1 = new GameProgress(99, 2, 10, 122.2);
        GameProgress save2 = new GameProgress(85, 5, 15, 156.8);
        GameProgress save3 = new GameProgress(75, 8, 21, 626.3);

        ArrayList<GameProgress> savesList = new ArrayList<>();
        savesList.add(save1);
        savesList.add(save2);
        savesList.add(save3);


        //Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
        String pathToSavegamesFolder = "src/savegames";

        for (int i = 0; i < savesList.size(); i++) {

            try (FileOutputStream saveFos = new FileOutputStream(pathToSavegamesFolder + "/save" + (i + 1) + ".dat");
                 ObjectOutputStream saveOos = new ObjectOutputStream(saveFos)) {
                saveOos.writeObject(savesList.get(i));
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }

        }

        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(pathToSavegamesFolder + "/Save.zip"));
            ArrayList<byte[]> bufferList = new ArrayList<>(); //Колхоз

            for (int i = 0; i < savesList.size(); i++) {
                FileInputStream saveFis = new FileInputStream(pathToSavegamesFolder + "/save" + (i + 1) + ".dat");
                ZipEntry entry = new ZipEntry("packed_save" + (i + 1) + ".dat");
                zipOutputStream.putNextEntry(entry);
                bufferList.add(new byte[saveFis.available()]);
                saveFis.read(bufferList.get(i));
                zipOutputStream.write(bufferList.get(i));
                zipOutputStream.closeEntry();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }


    }
}
