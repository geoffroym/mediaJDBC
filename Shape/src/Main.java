import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Shape> shapes = new ArrayList<>();
        Circle circle = new Circle();
        circle.setRadius(6.8);
        circle.setColor(Color.BLACK);
        circle.setFilled(true);
        System.out.println(circle);
        System.out.println("Area: " + circle.getArea());
        System.out.println("Perimeter: " + circle.getPerimeter());
        System.out.println("---");

        Rectangle rectangle = new Rectangle();
        //System.out.println(rectangle.toString());
        rectangle.setLength(10.6);
        rectangle.setWidth(4.75);
        rectangle.setColor(Color.BLUE);
        circle.setFilled(false);
        System.out.println(rectangle);
        System.out.println("Area: " + rectangle.getArea());
        System.out.println("Perimeter: " + rectangle.getPerimeter());
        System.out.println("---");

        Square square = new Square();
        square.setSide(8.8);
        square.setColor(Color.PINK);
        square.setFilled(true);
        System.out.println(square);
        System.out.println("Area: " + square.getArea());
        System.out.println("Perimeter: " + square.getPerimeter());
        System.out.println("---");

        shapes.add(circle);
        shapes.add(rectangle);
        shapes.add(square);


        System.out.println("All shapes x1 position");
        for(Shape shape: shapes){
            System.out.println(shape);
        }

        circle.moveUp(40.2);
        square.moveDown(7.5);
        rectangle.moveLeft(19.0);

        /*System.out.println("All shapes after moving:");
        for(Shape shape: shapes){
        }*/


        for (Shape shape: shapes){
            if (shape instanceof Circle) {
                ((Circle) shape).uniqueCircleMethod();
            } else if (shape instanceof Square){
                ((Square) shape).uniqueSquareMethod();
            } else if (shape instanceof Rectangle) {
                ((Rectangle) shape).uniqueRectangleMethod();
            }
        }
    }
}