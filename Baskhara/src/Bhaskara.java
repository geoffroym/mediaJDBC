public class Bhaskara {
    int a;
    int b;
    int c;

    public int discriminant(){
        return b*b - (4 * a * c);
    }

    public void findRoots(int a, int b, int c){
        this.a = a;
        this.b = b;
        this.c = c;

        if (discriminant() < 0){
            System.out.println("The real root can not be calculated since the equation does not cross the x axis.");
        } else if (discriminant() > 0){
            double root1 = -b + (Math.sqrt((double) discriminant() /2 * a));
            double root2 = -b - (Math.sqrt((double) discriminant() /2 * a));
            System.out.println("The two roots of this equation are: " + root1 + " and " + root2);
        } else if (discriminant() == 0) {
            double root3 = (double) -b / 2 * a;
            System.out.println("Since the discriminant is equal 0, the only root is: " + root3);
        }
    }
}
