import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BookRegister {
    //create arrayList books that takes in Book(from class Book)
    ArrayList<Book> books = new ArrayList<>();

    public void removeBook(String title){
        for (int i = 0; i < books.size(); i++){
            if (title.equalsIgnoreCase(books.get(i).getTitle())){
                books.remove(i);
            }
        }
    }

    //receives as parameter a book from Book, and adds a book to BookRegister class
    public void addBook(Book book){
        books.add(book);
    }

    //size() to arrayLists is the equivalent of length to Strings
    public int amountOfBooksRegistered(){
        return books.size();
    }

    //loops in the array and prints the method from Book (toString())
    public void showAllBooks(){
        for (Book book: books){
            System.out.println(book.toString());
        }
    }

    //looping in the ArrayList and checking if the getGenre of the book is equal the
    //genre from the parameter.
    //If so, prints the toString method from Book class
    public void showByGenre(Genre genre){
        for (Book book : books){
            if (book.getGenre() == genre){
                System.out.println(book.toString());
            }

        }
    }

    //do tha same here, but since it's a String, I did it with .equals
    public void showByAuthor(String author){
        for (Book b: books){
            if (b.getAuthor().equals(author)){
                System.out.println(b.toString());
            }
        }
    }

    public void initRegister(){
        books = new ArrayList<>();

        Book book1 = new Book("The Hobbit", "J.R.R.Tolkien", 320, Genre.FANTASY, 978884529, "1954-07-29", 3);
        addBook(book1);

        //create new different objects for the class Chapter and ass values
        Chapter chapter1 = new Chapter("An Unexpected Party", 25);
        Chapter chapter2 = new Chapter("Roast Mutton", 31);
        //create arraylist and add objects in it
        ArrayList<Chapter> chapters = new ArrayList<>();
        chapters.add(chapter1);
        chapters.add(chapter2);

        Book book2 = new Book("Silmarillion", "J.R.R.Tolkien", 384, Genre.FANTASY, 763329080, "1937-09-21", 4);
        addBook(book2);
    }

    public void writeBookFile() throws IOException {
        File file = new File("books.txt");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        for (Book b: books){
            writer.write(b.getTitle() + "\n");
            writer.write(b.getAuthor() + "\n");
            writer.write(b.getGenre() + "\n");
            writer.write(b.getIsbn() + "\n");
            writer.write(b.getNumberOfPages() + "\n");
            writer.write(b.getMinutesPerPage() + "\n");
            writer.write(b.getPublished() + "\n");
            writer.write("\n");
        }
        writer.close();

    }
}

