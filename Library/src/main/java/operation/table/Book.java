package operation.table;

public class Book {

    private int id;
    private String title;
    private String author;
    private String isbn;
    private int year;
    private boolean borrowed;

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

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(id).append(" ").append(title).append(" ").append(year).append(" ").append(isbn);
        return builder.toString();
    }

    public String toStringWithStatusOfBorrow() {
        StringBuilder builder = new StringBuilder();
        builder.append(id).append(" ").append(title).append(" ").append(year).append(" ").append(isbn);
        if (borrowed) {
            builder.append(" pożyczona");
        } else {
            builder.append(" dostępna");
        }
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
