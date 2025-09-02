import java.time.LocalDate;

public class LoanRecord {
    // 定义成员变量
    private String studentId;      // 借书人学号
    private String isbn;           // 借阅图书的ISBN
    private LocalDate borrowDate;  // 借阅日期
    private LocalDate dueDate;     // 到期日期
    private boolean returned;

    // 构造方法
    public LoanRecord(String studentId, String isbn, LocalDate borrowDate, LocalDate dueDate) {
        this.studentId = studentId;
        this.isbn = isbn;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returned = false;  // 新借阅记录默认未归还
    }// 是否已归还

    public String getStudentId() {
        return studentId;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    // 对象转换成字符串
    @Override
    public String toString() {
        return "LoanRecord{studentId='" + studentId + '\'' +
                ", isbn='" + isbn + '\'' +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returned=" + returned +
                '}';
    }
}
