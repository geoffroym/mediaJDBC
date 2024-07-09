import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ShapeService ss = new ShapeService();
        try {
            Circle circle = ss.addCircle(Color.BLACK, true, 1.0, new MovablePoint());
            System.out.println(circle);
        } catch (SQLException e) {
            System.out.println("Exception caught:"+ e.getMessage());
            e.printStackTrace();
        }
        /*Circle c = new Circle(1,Color.BLACK, true, 1.0, new MovablePoint()); //standard value for movable point
        Circle c1 = new Circle(2);
        Circle c2 = new Circle(3, 5.5);

        MovablePoint mp2 = new MovablePoint(6.0, 1.0);
        MovablePoint mp3 = new MovablePoint(5.0, 3.0);
        Rectangle r = new Rectangle(4, Color.GRAY, false, 1.0, 2.0, mp2, mp3);
        Rectangle r1 = new Rectangle(5);
        Rectangle r2 = new Rectangle(6, 3.3, 6.6);

        MovablePoint mp4 = new MovablePoint(3.0, 4.0);
        MovablePoint mp5 = new MovablePoint(-2.0, 6.5);
        Rectangle r3 = new Rectangle(7, Color.GRAY, true, 5.0, 2.5, mp4, mp5);

        MovablePoint mp6 = new MovablePoint(1.0, 2.0);
        MovablePoint mp7 = new MovablePoint(1.0, 2.0);
        Square s = new Square(8, Color.CYAN, false, 4.0, mp6, mp7);

        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(c);
        shapes.add(c1);
        shapes.add(c2);
        shapes.add(r);
        shapes.add(r1);
        shapes.add(r2);
        shapes.add(r3);
        shapes.add(s);

        for (Shape shape : shapes){
            System.out.println(shape);
            System.out.println("---");
        }

        for (Shape shape: shapes){
            if (shape instanceof Circle) {
                ((Circle) shape).uniqueCircleMethod();
            } else if (shape instanceof Square){
                ((Square) shape).uniqueSquareMethod();
            } else if (shape instanceof Rectangle) {
                ((Rectangle) shape).uniqueRectangleMethod();
            }
        }*/
    }
}