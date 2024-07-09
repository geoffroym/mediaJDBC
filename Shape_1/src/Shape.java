import java.awt.*;

public abstract class Shape implements Movable{
    //when a class is abstract, you cannot make an object of this class
    /* since Shape is abstract, even though it implements Movable
    it doesn't need to implement the methods from Movable
    but the classes that extends Shape inherit the implementation,
    so they must implement the methods */
    private int id;
    private Color color;
    private boolean filled;

    public Shape(int id){
        this(id, Color.RED, true);
    }

    public Shape(int id, Color color, boolean filled){
        this.id = id;
        this.color =color;
        this.filled = filled;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "id=" + id +
                ", color=" + color +
                ", filled=" + filled +
                '}';
    }

    public abstract double getArea();
    public abstract double getPerimeter();
    //abstract methods in an abstract class, even though they aren't implemented
    //they make sure that all classes that extends to this class
    //must have this method.


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }
}
