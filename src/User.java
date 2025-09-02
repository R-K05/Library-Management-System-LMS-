public class User {
    // 定义成员变量
    private String name;       // 姓名
    private String studentId;

    // 构造方法
    public User(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    // 对象转换成字符串
    @Override
    public String toString() {
        return "User{name='" + name + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }

}
