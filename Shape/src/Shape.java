import java.awt.*;
public abstract class Shape implements Movable{
    private Color color;
    private boolean filled;
    protected double x;
    protected double y;
    protected int id;

    @Override
    public void moveUp(double distance) {
        this.y += distance;
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

    public Color getColor() {
        return color;
    }
    public abstract double getArea();
    public abstract double getPerimeter();

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "color=" + color +
                ", filled=" + filled +
                ", x=" + x +
                ", y=" + y +
                ", id=" + id +
                '}';
    }
}
