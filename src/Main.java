
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final BankManager bankManager = new BankManager();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            showMenu();

            try {
                int choice = readInt("Chọn chức năng: ");

                switch (choice) {
                    case 1:
                        createRegularAccount();
                        break;

                    case 2:
                        createSavingAccount();
                        break;

                    case 3:
                        depositMoney();
                        break;

                    case 4:
                        withdrawMoney();
                        break;

                    case 5:
                        transferMoney();
                        break;

                    case 6:
                        displayAccount();
                        break;

                    case 7:
                        displayAllAccounts();
                        break;

                    case 8:
                        displayTotalBalance();
                        break;

                    case 0:
                        running = false;
                        System.out.println("Đã thoát chương trình.");
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                }

            } catch (InvalidAmountException
                     | InsufficientBalanceException e) {

                System.out.println("Giao dịch thất bại: " + e.getMessage());

            } catch (IllegalArgumentException e) {

                System.out.println("Thao tác không hợp lệ: " + e.getMessage());

            } catch (Exception e) {

                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("===== BANK ACCOUNT MANAGEMENT =====");
        System.out.println("1. Tạo tài khoản thường");
        System.out.println("2. Tạo tài khoản tiết kiệm");
        System.out.println("3. Nạp tiền");
        System.out.println("4. Rút tiền");
        System.out.println("5. Chuyển tiền");
        System.out.println("6. Xem thông tin một tài khoản");
        System.out.println("7. Xem tất cả tài khoản");
        System.out.println("8. Tính tổng số dư");
        System.out.println("0. Thoát");
    }

    private static void createRegularAccount() {
        String accountNumber = readText("Nhập số tài khoản: ");
        String holderName = readText("Nhập tên chủ tài khoản: ");
        double balance = readDouble("Nhập số dư ban đầu: ");

        BankAccount account =
                new RegularAccount(accountNumber, holderName, balance);

        bankManager.addAccount(account);

        System.out.println("Tạo tài khoản thường thành công.");
    }

    private static void createSavingAccount() {
        String accountNumber = readText("Nhập số tài khoản: ");
        String holderName = readText("Nhập tên chủ tài khoản: ");
        double balance = readDouble("Nhập số dư ban đầu (tối thiểu 50000): ");
        double interestRate = readDouble("Nhập lãi suất: ");

        BankAccount account = new SavingAccount(
                accountNumber,
                holderName,
                balance,
                interestRate
        );

        bankManager.addAccount(account);

        System.out.println("Tạo tài khoản tiết kiệm thành công.");
    }

    private static void depositMoney() throws InvalidAmountException {
        String accountNumber = readText("Nhập số tài khoản: ");
        double amount = readDouble("Nhập số tiền cần nạp: ");

        BankAccount account = requireAccount(accountNumber);
        account.deposit(amount);

        System.out.println("Nạp tiền thành công.");
        System.out.println("Số dư mới: " + account.getBalance() + " VNĐ");
    }

    private static void withdrawMoney()
            throws InvalidAmountException, InsufficientBalanceException {

        String accountNumber = readText("Nhập số tài khoản: ");
        double amount = readDouble("Nhập số tiền cần rút: ");

        BankAccount account = requireAccount(accountNumber);
        account.withdraw(amount);

        System.out.println("Rút tiền thành công.");
        System.out.println("Số dư mới: " + account.getBalance() + " VNĐ");
    }

    private static void transferMoney()
            throws InvalidAmountException, InsufficientBalanceException {

        String fromAcc = readText("Nhập tài khoản nguồn: ");
        String toAcc = readText("Nhập tài khoản nhận: ");
        double amount = readDouble("Nhập số tiền cần chuyển: ");

        bankManager.transferMoney(fromAcc, toAcc, amount);

        System.out.println("Chuyển tiền thành công.");
    }

    private static void displayAccount() {
        String accountNumber = readText("Nhập số tài khoản cần tìm: ");

        BankAccount account = requireAccount(accountNumber);
        account.displayInfo();
    }

    private static void displayAllAccounts() {
        List<BankAccount> accounts = bankManager.getAllAccounts();

        if (accounts.isEmpty()) {
            System.out.println("Chưa có tài khoản nào.");
            return;
        }

        for (BankAccount account : accounts) {
            System.out.println("------------------------------");
            account.displayInfo();
        }
    }

    private static void displayTotalBalance() {
        List<BankAccount> accounts = bankManager.getAllAccounts();

        double total = bankManager.calculateTotalBalance(accounts);

        System.out.println("Tổng số dư tất cả tài khoản: " + total + " VNĐ");
    }

    private static BankAccount requireAccount(String accountNumber) {
        BankAccount account = bankManager.findAccount(accountNumber);

        if (account == null) {
            throw new IllegalArgumentException(
                    "Không tìm thấy tài khoản " + accountNumber + ".");
        }

        return account;
    }

    private static String readText(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static int readInt(String message) {
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private static double readDouble(String message) {
        System.out.print(message);
        return Double.parseDouble(scanner.nextLine().trim());
    }
}