package data;

public class DataGetterManager {

    private static MiDataGetter miDataGetter;
    private static MiDataRecorder miDataRecorder;

    public static void setMiDataGetter(MiDataGetter dataGetter) {
        miDataGetter = dataGetter;
    }

    public static MiDataGetter getMiDataGetter() {
        return miDataGetter;
    }

    public static void setMiDataRecorder(MiDataRecorder dataRecorder) {
        miDataRecorder = dataRecorder;
    }

    public static MiDataRecorder getMiDataRecorder() {
        return miDataRecorder;
    }
}
