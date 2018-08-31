package data;

/**
 * Created by kuasmis on 15/7/4.
 */
public class MiDataGetter implements DataGetter {

    private float x = 0f;
    private float y = 0f;
    private float z = 0f;

    @Override
    public void dataCallBack(float[] data) {
        x = data[1];
        y = data[2];
        z = data[3];
    }

    synchronized public double getX() {
        return x;
    }

    synchronized public double getY() {
        return y;
    }

    synchronized public double getZ() {
        return z;
    }
}
