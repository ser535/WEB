import java.util.Arrays;

public class Validation {
    public boolean validate(Integer x, Float y, Float r) {

        if (checkX(x) && checkY(y) && checkR(r) && checkForNull(x, y, r)) {
            return true;
        }
        return false;

    }


    public boolean checkForNull(Integer x, Float y, Float r) {
        return x != null && y != null && r != null;
    }

    public boolean checkX(Integer x) {
        int[] xRange = {-3, -2, -1, 0, 1, 2, 3, 4, 5};

        return Arrays.binarySearch(xRange, x) >= 0;
    }

    public boolean checkY(Float y) {
        return y >= -3 && y <= 3;
    }

    public boolean checkR(Float r) {
        float[] rRange = {1, 1.5F, 2, 2.5F, 3};

        return Arrays.binarySearch(rRange, r) >= 0;
    }

}
