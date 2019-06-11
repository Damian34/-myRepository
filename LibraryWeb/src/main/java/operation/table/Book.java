package operation.table;

public class Book {

    private int id;
    private String title;
    private String author;
    private String isbn;
    private int year;
    private boolean borrowed;
    private String status = "dostêpna";

    public Book(int id, String title, String author, String isbn, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getYear() {
        return year;
    }

    public String getStatus() {
        return status;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
        if (this.borrowed) {
            status = "po¿yczona";
        } else {
            status = "dostêpna";
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(id).append(" ").append(title).append(" ").append(year).append(" ").append(isbn);
        return builder.toString();
    }

    public String toStringWithStatusOfBorrow() {
        StringBuilder builder = new StringBuilder();
        builder.append(id).append(" ").append(title).append(" ").append(year)
                .append(" ").append(isbn).append(" ").append(status);
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Book) {
            Book other = (Book) obj;
            return this.year == other.year && this.isbn.equals(other.isbn)
                    && this.author.equals(other.author) && this.title.equals(other.title);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.title.hashCode() + this.author.hashCode() + this.isbn.hashCode() + this.year;
    }
}
