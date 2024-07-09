import java.awt.*;

public class Rectangle extends Shape{
    private double width;
    private double height;
    private MovablePoint topLeft;
    private MovablePoint bottomRight;

    public Rectangle(int id){
        this(id, 1.0, 2.0);
    }

    public Rectangle(int id, double width, double height){
        this(id, Color.GREEN,true, width, height, new MovablePoint(), new MovablePoint());
    }

    public Rectangle(int id, Color color, boolean filled, double width, double height, MovablePoint topLeft, MovablePoint bottomRight) {
        super(id, color, filled);
        this.width = width;
        this.height = height;
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                ", topLeft=" + topLeft +
                ", bottomRight=" + bottomRight +
                "} " + super.toString();
    }

    public MovablePoint getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(MovablePoint topLeft) {
        this.topLeft = topLeft;
    }

    public MovablePoint getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(MovablePoint bottomRight) {
        this.bottomRight = bottomRight;
    }

    @Override
    public double getArea(){
        return getWidth()*getHeight();
    }

    @Override
    public double getPerimeter(){
        return  2*(getWidth()+getHeight());
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public void moveUp(double distance) {
        topLeft.moveUp(distance);
        bottomRight.moveUp(distance);
    }

    @Override
    public void moveDown(double distance) {
        topLeft.moveDown(distance);
        bottomRight.moveDown(distance);
    }

    @Override
    public void moveRight(double distance) {
        topLeft.moveRight(distance);
        bottomRight.moveRight(distance);
    }

    @Override
    public void moveLeft(double distance) {
        topLeft.moveLeft(distance);
        bottomRight.moveLeft(distance);
    }

    public void uniqueRectangleMethod(){
        System.out.println("I'm a rectangle");
    }
}
