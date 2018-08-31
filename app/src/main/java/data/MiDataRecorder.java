package data;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MiDataRecorder implements DataGetter {

    private List<String> recordList = new ArrayList<String>();
    private boolean isStart = false;
    private int level = 0;

    @Override
    public void dataCallBack(float[] data) {
        if (isStart) {
            addRecordData(data[0], data[1], data[2], data[3]);
            addRecordData(data[0], data[4], data[5], data[6]);
            addRecordData(data[0], data[7], data[8], data[9]);
        }
    }

    private void addRecordData(float count, float x, float y, float z) {
        String recordLine = String.format("%.0f,%f,%f,%f,%d", count, x, y, z, level);
        recordList.add(recordLine);
    }

    public void start() {
        if (recordList.isEmpty())
            addRecordHeader();
        isStart = true;
    }

    private void addRecordHeader() {
        recordList.add("Count,X,Y,Z,Level");
    }

    public void restart() {
        recordList.clear();
        start();
    }

    public void stop() {
        isStart = false;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String save(String fileName) throws IOException {
        String dirPath = "/sdcard/Fishing";
        checkAndMakeDir(dirPath);
        String filePath = dirPath + "/" + fileName;
        File file = new File(filePath);
        PrintWriter writer = new PrintWriter(new FileWriter(file));

        for (String line : recordList)
            writer.println(line);
        writer.flush();
        writer.close();

        return file.getAbsolutePath();
    }

    private void checkAndMakeDir(String dir) {
        File dirFile = new File(dir);
        if (!dirFile.exists())
            dirFile.mkdir();
    }
}
