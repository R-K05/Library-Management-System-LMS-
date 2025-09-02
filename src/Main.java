import java.util.Scanner;

/**
 * 图书馆管理系统主程序
 * 负责用户界面交互和程序流程控制
 * 通过控制台菜单与用户交互，调用Library类的方法完成各种功能
 */
public class Main {
    // 图书馆系统实例，管理所有业务逻辑
    private static Library library = new Library();

    // 扫描器对象，用于读取用户输入
    private static Scanner scanner = new Scanner(System.in);

    /**
     * 程序入口点
     * 显示欢迎信息，启动主循环，处理用户选择
     * @param args 命令行参数（本程序不使用）
     */
    public static void main(String[] args) {
        System.out.println("=== 欢迎使用图书馆管理系统 ===");

        // 主循环：持续显示菜单并处理用户选择，直到用户选择退出
        while (true) {
            showMainMenu();
            int choice = getIntInput("请选择操作：");

            // 根据用户选择调用相应的功能模块
            switch (choice) {
                case 1:
                    userLogin();
                    break;
                case 2:
                    showAllBooks();
                    break;
                case 3:
                    bookManagement();
                    break;
                case 4:
                    userManagement();
                    break;
                case 5:
                    borrowManagement();
                    break;
                case 6:
                    searchBooks();
                    break;
                case 7:
                    library.showStatistics();
                    break;
                case 8:
                    library.logout();
                    break;
                case 0:
                    System.out.println("感谢使用，再见！");
                    return; // 退出程序
                default:
                    System.out.println("无效选择，请重新输入！");
            }

            // 每个操作完成后暂停，等待用户按回车继续
            System.out.println("\n按回车键继续...");
            scanner.nextLine();
        }
    }

    /**
     * 显示主菜单
     * 列出系统所有主要功能选项
     */
    private static void showMainMenu() {
        System.out.println("\n=== 主菜单 ===");
        System.out.println("1. 用户登录");
        System.out.println("2. 查看所有图书");
        System.out.println("3. 图书管理");
        System.out.println("4. 用户管理");
        System.out.println("5. 借阅管理");
        System.out.println("6. 图书查询");
        System.out.println("7. 系统统计");
        System.out.println("8. 退出登录");
        System.out.println("0. 退出系统");
    }

    /**
     * 用户登录功能
     * 提示用户输入学号，调用Library的login方法进行登录验证
     */
    private static void userLogin() {
        System.out.println("\n=== 用户登录 ===");
        String studentId = getStringInput("请输入学号：");
        library.login(studentId);
    }

    /**
     * 显示所有图书
     * 调用Library的listAllBooks方法显示图书馆中的所有图书
     */
    private static void showAllBooks() {
        library.listAllBooks();
    }

    /**
     * 图书管理模块
     * 提供图书的增删改查功能
     * 包含子菜单循环，直到用户选择返回主菜单
     */
    private static void bookManagement() {
        // 图书管理子菜单循环
        while (true) {
            library.showBookManagementMenu();
            int choice = getIntInput("请选择操作：");

            switch (choice) {
                case 1:
                    // 添加图书
                    library.addBookFromInput();
                    break;
                case 2:
                    // 删除图书
                    library.removeBookFromInput();
                    break;
                case 3:
                    // 修改库存
                    library.updateStockFromInput();
                    break;
                case 4:
                    // 查看所有图书
                    library.listAllBooks();
                    break;
                case 0:
                    // 返回主菜单
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }

            // 每个操作后暂停
            System.out.println("\n按回车键继续...");
            scanner.nextLine();
        }
    }

    /**
     * 用户管理模块
     * 提供用户注册和查看功能
     * 包含子菜单循环，直到用户选择返回主菜单
     */
    private static void userManagement() {
        // 用户管理子菜单循环
        while (true) {
            library.showUserManagementMenu();
            int choice = getIntInput("请选择操作：");

            switch (choice) {
                case 1:
                    // 注册新用户
                    library.registerUserFromInput();
                    break;
                case 2:
                    // 查看所有用户
                    library.listAllUsers();
                    break;
                case 0:
                    // 返回主菜单
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }

            // 每个操作后暂停
            System.out.println("\n按回车键继续...");
            scanner.nextLine();
        }
    }

    /**
     * 借阅管理模块
     * 提供借书、还书、查看借阅记录等功能
     * 需要用户先登录才能使用
     * 包含子菜单循环，直到用户选择返回主菜单
     */
    private static void borrowManagement() {
        // 检查用户是否已登录
        if (library.getCurrentUser() == null) {
            System.out.println("请先登录！");
            return;
        }

        // 借阅管理子菜单循环
        while (true) {
            library.showBorrowManagementMenu();
            int choice = getIntInput("请选择操作：");

            switch (choice) {
                case 1:
                    // 借阅图书
                    borrowBook();
                    break;
                case 2:
                    // 归还图书
                    returnBook();
                    break;
                case 3:
                    // 查看我的借阅记录
                    library.showMyLoans();
                    break;
                case 4:
                    // 查看所有借阅记录（管理员功能）
                    library.showAllLoanRecords();
                    break;
                case 0:
                    // 返回主菜单
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }

            // 每个操作后暂停
            System.out.println("\n按回车键继续...");
            scanner.nextLine();
        }
    }

    /**
     * 图书查询模块
     * 提供按书名、作者、ISBN查询图书的功能
     * 包含子菜单循环，直到用户选择返回主菜单
     */
    private static void searchBooks() {
        // 图书查询子菜单循环
        while (true) {
            library.showSearchMenu();
            int choice = getIntInput("请选择查询方式：");

            switch (choice) {
                case 1:
                    // 按书名查询
                    library.searchByTitle();
                    break;
                case 2:
                    // 按作者查询
                    library.searchByAuthor();
                    break;
                case 3:
                    // 按ISBN查询
                    library.searchByIsbn();
                    break;
                case 0:
                    // 返回主菜单
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }

            // 每个操作后暂停
            System.out.println("\n按回车键继续...");
            scanner.nextLine();
        }
    }

    /**
     * 借阅图书功能
     * 显示所有图书，让用户选择要借阅的图书
     * 调用Library的borrowBook方法完成借阅
     */
    private static void borrowBook() {
        System.out.println("\n=== 借阅图书 ===");
        // 先显示所有可借图书
        library.listAllBooks();
        // 获取用户要借阅的图书ISBN
        String isbn = getStringInput("请输入要借阅的图书ISBN：");
        // 执行借阅操作
        library.borrowBook(isbn);
    }

    /**
     * 归还图书功能
     * 显示用户当前的借阅记录，让用户选择要归还的图书
     * 调用Library的returnBook方法完成归还
     */
    private static void returnBook() {
        System.out.println("\n=== 归还图书 ===");
        // 先显示用户当前的借阅记录
        library.showMyLoans();
        // 获取用户要归还的图书ISBN
        String isbn = getStringInput("请输入要归还的图书ISBN：");
        // 执行归还操作
        library.returnBook(isbn);
    }

    // ==================== 辅助方法 ====================

    /**
     * 获取用户输入的整数
     * 处理用户输入，确保输入的是有效数字
     * @param prompt 提示信息
     * @return 用户输入的整数，如果输入无效则返回-1
     */
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            // 尝试将用户输入转换为整数
            /*
            Integer.parseInt() 字符串转整数
            scanner.nextLine() - 获取用户输入
            */
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            // 如果转换失败，提示用户输入格式错误
            System.out.println("输入格式错误，请输入数字！");
            return -1; // 返回-1表示输入无效
        }
    }

    /**
     * 获取用户输入的字符串
     * 读取用户输入的一行文本
     * @param prompt 提示信息
     * @return 用户输入的字符串
     */
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}