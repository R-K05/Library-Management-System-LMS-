public class Book {
    // 定义成员变量
    public String title; // 书名
    public String author; // 作者
    public String isbn; // 唯一标识
    public int stock; // 当前可借库存

    // 构造方法
    public Book(String title, String author, String isbn, int stock) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.stock = stock;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    // 对象转换成字符串
    @Override
    public String toString() {
        return "Book{title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", stock=" + stock +
                '}';
    }
}
