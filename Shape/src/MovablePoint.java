public class MovablePoint implements Movable {
    private double y, x;

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void moveUp(double distance) {
        y += distance;  //this.y += distance;
    }

    @Override
    public void moveDown(double distance) {
        y -= distance;
    }

    @Override
    public void moveRight(double distance) {
        x += distance;
    }

    @Override
    public void moveLeft(double distance) {
        x -= distance;
    }

    @Override
    public String toString() {
        return "MovablePoint{" +
                "y=" + y +
                ", x=" + x +
                '}';
    }
}
