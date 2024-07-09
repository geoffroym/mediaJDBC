public class Rectangle extends Shape{
    private double width;
    private double length;
    private MovablePoint topLeft;
    private MovablePoint bottomRight;
    @Override
    public double getArea(){
        return width * length;
    }
    @Override
    public double getPerimeter(){
        return 2 * (length + width);
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
    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", length=" + length +
                ", topLeft=" + topLeft +
                ", bottomRight=" + bottomRight +
                "} " + super.toString();
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public MovablePoint getTopLeft(){return topLeft;}

    public void setTopLeft(MovablePoint topLeft) {
        this.topLeft = topLeft;
    }

    public MovablePoint getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(MovablePoint bottomRight) {
        this.bottomRight = bottomRight;
    }
    public void uniqueRectangleMethod(){
        System.out.println("I'm a rectangle");
    }
}
