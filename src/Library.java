import java.time.LocalDate;
import java.util.*;

/**
 * 图书馆管理系统核心类
 * 负责管理图书、用户、借阅记录等所有业务逻辑
 */
public class Library {
    // 用Map存储图书，key是ISBN，value是Book对象
    private Map<String, Book> books;

    // 用Map存储用户，key是学号，value是User对象
    private Map<String, User> users;

    // 用List存储借阅记录
    private List<LoanRecord> loanRecords;

    // 当前登录的用户
    private User currentUser;

    /**
     * 构造函数：初始化图书馆系统
     * 创建空的集合来存储数据，并加载测试数据
     */
    public Library() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
        this.loanRecords = new ArrayList<>();
        this.currentUser = null;

        // 初始化一些测试数据
        initTestData();
    }

    /**
     * 初始化测试数据
     * 添加几本测试图书和一个测试用户，方便系统测试
     */
    private void initTestData() {
        // 添加几本测试图书
        addBook("Java编程思想", "Bruce Eckel", "978-7-115-123456", 3);
        addBook("算法导论", "Thomas Cormen", "978-7-115-789012", 2);
        addBook("数据结构与算法", "严蔚敏", "978-7-115-345678", 1);

        // 添加一个测试用户
        registerUser("张三", "20230001");
    }

    // ==================== 图书管理方法 ====================

    /**
     * 添加新图书到图书馆
     * @param title 书名
     * @param author 作者
     * @param isbn 图书唯一标识码
     * @param stock 初始库存数量
     */
    public void addBook(String title, String author, String isbn, int stock) {
        /*
        containsKey() 检查指定的键是否存在
        new Book() 创建Book对象
        put() 将键值对象添加到Book中
        
        */
        if (books.containsKey(isbn)) {
            System.out.println("错误：ISBN已存在！");
            return;
        }
        books.put(isbn, new Book(title, author, isbn, stock));
        System.out.println("成功添加图书：" + title);
    }

    /**
     * 从图书馆删除图书
     * @param isbn 要删除的图书ISBN
     */
    public void removeBook(String isbn) {
        if (books.remove(isbn) != null) {
            System.out.println("成功删除图书，ISBN：" + isbn);
        } else {
            System.out.println("错误：找不到该图书！");
        }
    }

    /**
     * 更新图书库存数量
     * @param isbn 图书ISBN
     * @param newStock 新的库存数量
     */
    public void updateStock(String isbn, int newStock) {
        Book book = books.get(isbn);
        if (book != null) {
            book.setStock(newStock);
            System.out.println("成功更新库存：" + book.getTitle() + " -> " + newStock + "本");
        } else {
            System.out.println("错误：找不到该图书！");
        }
    }

    /**
     * 显示所有图书信息
     * 遍历books集合，打印每本书的详细信息
     */
    public void listAllBooks() {
        System.out.println("\n=== 所有图书 ===");
        if (books.isEmpty()) {
            System.out.println("暂无图书");
            return;
        }
        for (Book book : books.values()) {
            System.out.println(book);
        }
    }

    /**
     * 显示图书管理菜单
     * 提供图书管理的各种操作选项
     */
    public void showBookManagementMenu() {
        System.out.println("\n=== 图书管理 ===");
        System.out.println("1. 添加图书");
        System.out.println("2. 删除图书");
        System.out.println("3. 修改库存");
        System.out.println("4. 查看所有图书");
        System.out.println("0. 返回主菜单");
    }

    /**
     * 从用户输入添加图书
     * 提示用户输入图书信息，然后调用addBook方法
     */
    public void addBookFromInput() {
        System.out.println("\n=== 添加图书 ===");
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入书名：");
        String title = scanner.nextLine();

        System.out.print("请输入作者：");
        String author = scanner.nextLine();

        System.out.print("请输入ISBN：");
        String isbn = scanner.nextLine();

        System.out.print("请输入库存数量：");
        try {
            int stock = Integer.parseInt(scanner.nextLine());
            addBook(title, author, isbn, stock);
        } catch (NumberFormatException e) {
            System.out.println("错误：库存数量必须是数字！");
        }
    }

    /**
     * 从用户输入删除图书
     * 先显示所有图书，让用户选择要删除的图书，并确认删除操作
     */
    public void removeBookFromInput() {
        System.out.println("\n=== 删除图书 ===");
        listAllBooks();
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入要删除的图书ISBN：");
        String isbn = scanner.nextLine();

        System.out.print("确认删除？(y/n)：");
        String confirm = scanner.nextLine();
        if ("y".equalsIgnoreCase(confirm)) {
            removeBook(isbn);
        } else {
            System.out.println("取消删除");
        }
    }

    /**
     * 从用户输入更新库存
     * 先显示所有图书，让用户选择要修改的图书，然后输入新库存数量
     */
    public void updateStockFromInput() {
        System.out.println("\n=== 修改库存 ===");
        listAllBooks();
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入图书ISBN：");
        String isbn = scanner.nextLine();

        System.out.print("请输入新库存数量：");
        try {
            int newStock = Integer.parseInt(scanner.nextLine());
            updateStock(isbn, newStock);
        } catch (NumberFormatException e) {
            System.out.println("错误：库存数量必须是数字！");
        }
    }

    // ==================== 用户管理方法 ====================

    /**
     * 注册新用户
     * @param name 用户姓名
     * @param studentId 学号（唯一标识）
     */
    public void registerUser(String name, String studentId) {
        if (users.containsKey(studentId)) {
            System.out.println("错误：学号已存在！");
            return;
        }
        users.put(studentId, new User(name, studentId));
        System.out.println("成功注册用户：" + name + " (学号：" + studentId + ")");
    }

    /**
     * 用户登录
     * @param studentId 学号
     * @return 登录是否成功
     */
    public boolean login(String studentId) {
        User user = users.get(studentId);
        if (user != null) {
            currentUser = user;
            System.out.println("登录成功！欢迎，" + user.getName());
            return true;
        } else {
            System.out.println("错误：用户不存在！");
            return false;
        }
    }

    /**
     * 用户退出登录
     * 清除当前登录用户信息
     */
    public void logout() {
        if (currentUser != null) {
            System.out.println("再见，" + currentUser.getName() + "！");
            currentUser = null;
        }
    }

    /**
     * 获取当前登录的用户
     * @return 当前登录的User对象，如果未登录则返回null
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * 显示用户管理菜单
     * 提供用户管理的各种操作选项
     */
    public void showUserManagementMenu() {
        System.out.println("\n=== 用户管理 ===");
        System.out.println("1. 注册新用户");
        System.out.println("2. 查看所有用户");
        System.out.println("0. 返回主菜单");
    }

    /**
     * 从用户输入注册新用户
     * 提示用户输入姓名和学号，然后调用registerUser方法
     */
    public void registerUserFromInput() {
        System.out.println("\n=== 注册新用户 ===");
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入姓名：");
        String name = scanner.nextLine();

        System.out.print("请输入学号：");
        String studentId = scanner.nextLine();

        registerUser(name, studentId);
    }

    /**
     * 显示所有用户信息
     * 遍历users集合，打印每个用户的详细信息
     */
    public void listAllUsers() {
        System.out.println("\n=== 所有用户 ===");
        if (users.isEmpty()) {
            System.out.println("暂无用户");
            return;
        }
        for (User user : users.values()) {
            System.out.println(user);
        }
    }

    // ==================== 借阅管理方法 ====================

    /**
     * 借阅图书
     * @param isbn 要借阅的图书ISBN
     * 检查用户是否登录、图书是否存在、库存是否充足、是否重复借阅
     */
    public void borrowBook(String isbn) {
        if (currentUser == null) {
            System.out.println("错误：请先登录！");
            return;
        }

        Book book = books.get(isbn);
        if (book == null) {
            System.out.println("错误：找不到该图书！");
            return;
        }

        if (book.getStock() <= 0) {
            System.out.println("错误：图书库存不足！");
            return;
        }

        // 检查是否已经借过这本书且未归还
        for (LoanRecord record : loanRecords) {
            if (record.getStudentId().equals(currentUser.getStudentId())
                    && record.getIsbn().equals(isbn)
                    && !record.isReturned()) {
                System.out.println("错误：您已经借过这本书了！");
                return;
            }
        }

        // 创建借阅记录
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(30); // 30天后到期
        LoanRecord record = new LoanRecord(currentUser.getStudentId(), isbn, borrowDate, dueDate);
        loanRecords.add(record);

        // 更新库存
        book.setStock(book.getStock() - 1);

        System.out.println("借阅成功！");
        System.out.println("图书：" + book.getTitle());
        System.out.println("到期时间：" + dueDate);
    }

    /**
     * 归还图书
     * @param isbn 要归还的图书ISBN
     * 查找借阅记录，标记为已归还，并更新库存
     */
    public void returnBook(String isbn) {
        if (currentUser == null) {
            System.out.println("错误：请先登录！");
            return;
        }

        // 查找借阅记录
        LoanRecord record = null;
        for (LoanRecord r : loanRecords) {
            if (r.getStudentId().equals(currentUser.getStudentId())
                    && r.getIsbn().equals(isbn)
                    && !r.isReturned()) {
                record = r;
                break;
            }
        }

        if (record == null) {
            System.out.println("错误：您没有借过这本书！");
            return;
        }

        // 标记为已归还
        record.setReturned(true);

        // 更新库存
        Book book = books.get(isbn);
        if (book != null) {
            book.setStock(book.getStock() + 1);
        }

        System.out.println("归还成功！图书：" + book.getTitle());
    }

    /**
     * 显示当前用户的借阅记录
     * 只显示未归还的图书
     */
    public void showMyLoans() {
        if (currentUser == null) {
            System.out.println("错误：请先登录！");
            return;
        }

        System.out.println("\n=== 我的借阅记录 ===");
        boolean hasLoans = false;
        for (LoanRecord record : loanRecords) {
            if (record.getStudentId().equals(currentUser.getStudentId()) && !record.isReturned()) {
                Book book = books.get(record.getIsbn());
                System.out.println("图书：" + book.getTitle() +
                        " | 借阅日期：" + record.getBorrowDate() +
                        " | 到期日期：" + record.getDueDate());
                hasLoans = true;
            }
        }
        if (!hasLoans) {
            System.out.println("暂无借阅记录");
        }
    }

    /**
     * 显示借阅管理菜单
     * 提供借阅相关的各种操作选项
     */
    public void showBorrowManagementMenu() {
        System.out.println("\n=== 借阅管理 ===");
        System.out.println("1. 借阅图书");
        System.out.println("2. 归还图书");
        System.out.println("3. 查看我的借阅记录");
        System.out.println("4. 查看所有借阅记录（管理员）");
        System.out.println("0. 返回主菜单");
    }

    /**
     * 显示所有借阅记录（管理员功能）
     * 显示所有用户的借阅记录，包括已归还和未归还的
     */
    public void showAllLoanRecords() {
        System.out.println("\n=== 所有借阅记录 ===");
        if (loanRecords.isEmpty()) {
            System.out.println("暂无借阅记录");
            return;
        }

        for (LoanRecord record : loanRecords) {
            Book book = books.get(record.getIsbn());
            User user = users.get(record.getStudentId());
            String status = record.isReturned() ? "已归还" : "未归还";

            System.out.println("用户：" + (user != null ? user.getName() : "未知") +
                    " | 图书：" + (book != null ? book.getTitle() : "未知") +
                    " | 借阅日期：" + record.getBorrowDate() +
                    " | 到期日期：" + record.getDueDate() +
                    " | 状态：" + status);
        }
    }

    // ==================== 查询功能 ====================

    /**
     * 显示图书查询菜单
     * 提供不同的查询方式选项
     */
    public void showSearchMenu() {
        System.out.println("\n=== 图书查询 ===");
        System.out.println("1. 按书名查询");
        System.out.println("2. 按作者查询");
        System.out.println("3. 按ISBN查询");
        System.out.println("0. 返回主菜单");
    }

    /**
     * 按书名查询图书
     * 支持模糊查询，不区分大小写
     */
    public void searchByTitle() {
        System.out.println("\n=== 按书名查询 ===");
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入书名关键词：");
        String keyword = scanner.nextLine().toLowerCase();

        System.out.println("\n查询结果：");
        boolean found = false;
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(keyword)) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("未找到相关图书");
        }
    }

    /**
     * 按作者查询图书
     * 支持模糊查询，不区分大小写
     */
    public void searchByAuthor() {
        System.out.println("\n=== 按作者查询 ===");
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入作者关键词：");
        String keyword = scanner.nextLine().toLowerCase();

        System.out.println("\n查询结果：");
        boolean found = false;
        for (Book book : books.values()) {
            if (book.getAuthor().toLowerCase().contains(keyword)) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("未找到相关图书");
        }
    }

    /**
     * 按ISBN精确查询图书
     * @param isbn 图书ISBN
     */
    public void searchByIsbn() {
        System.out.println("\n=== 按ISBN查询 ===");
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入ISBN：");
        String isbn = scanner.nextLine();

        Book book = books.get(isbn);
        if (book != null) {
            System.out.println("\n查询结果：");
            System.out.println(book);
        } else {
            System.out.println("未找到该图书");
        }
    }

    // ==================== 统计功能 ====================

    /**
     * 显示系统统计信息
     * 包括图书总数、用户总数、借阅统计、最受欢迎图书等
     */
    public void showStatistics() {
        System.out.println("\n=== 系统统计 ===");
        System.out.println("图书总数：" + books.size());
        System.out.println("用户总数：" + users.size());

        int totalLoans = 0;
        int activeLoans = 0;
        for (LoanRecord record : loanRecords) {
            totalLoans++;
            if (!record.isReturned()) {
                activeLoans++;
            }
        }
        System.out.println("总借阅次数：" + totalLoans);
        System.out.println("当前借出图书：" + activeLoans);

        // 显示最受欢迎的图书
        Map<String, Integer> bookPopularity = new HashMap<>();
        for (LoanRecord record : loanRecords) {
            String isbn = record.getIsbn();
            bookPopularity.put(isbn, bookPopularity.getOrDefault(isbn, 0) + 1);
        }

        if (!bookPopularity.isEmpty()) {
            System.out.println("\n最受欢迎的图书：");
            bookPopularity.entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .limit(3)
                    .forEach(entry -> {
                        Book book = books.get(entry.getKey());
                        if (book != null) {
                            System.out.println(book.getTitle() + " - 借阅次数：" + entry.getValue());
                        }
                    });
        }
    }
}