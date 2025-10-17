public class Check {
    public boolean getAnswer(int x, float y, float r) {
        return checkCircle(x, y, r) || checkRectangle(x, y, r) || checkTriangle(x, y, r);
    }

    public boolean checkRectangle(int x, float y, float r) {
        return x >= 0 && x <= r/2 && y >= 0 && y <= r;
    }

    public boolean checkCircle(int x, float y, float r) {
        return x <= 0 && y <= 0 && Math.sqrt(x*x + y*y) <= r/2;
    }

    public boolean checkTriangle(int x, float y, float r) {
        return x >= 0 && y <= 0 && -2*x + y >= -r;
    }
}
