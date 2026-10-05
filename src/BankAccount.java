public abstract class BankAccount {

    private String accountNumber;
    private String holderName;
    private double balance;

    public BankAccount(String accountNumber, String holderName, double balance) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Số tài khoản không được để trống.");
        }

        if (holderName == null || holderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên chủ tài khoản không được để trống.");
        }

        if (balance < 0) {
            throw new IllegalArgumentException("Số dư ban đầu không được âm.");
        }

        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setHolderName(String holderName) {
        if (holderName == null || holderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên chủ tài khoản không được để trống.");
        }

        this.holderName = holderName;
    }

    protected void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Số dư không được âm.");
        }

        this.balance = balance;
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Số tiền nạp phải lớn hơn 0.");
        }

        double oldBalance = this.balance;
        double newBalance = oldBalance + amount;

        if (!Double.isFinite(newBalance)) {
            throw new IllegalArgumentException("Số tiền sau giao dịch không hợp lệ.");
        }

        this.balance = newBalance;
    }

    public abstract void withdraw(double amount)
            throws InsufficientBalanceException, InvalidAmountException;

    public void displayInfo() {
        System.out.println("Số tài khoản: " + accountNumber);
        System.out.println("Chủ tài khoản: " + holderName);
        System.out.println("Số dư: " + balance + " VNĐ");
    }
}
