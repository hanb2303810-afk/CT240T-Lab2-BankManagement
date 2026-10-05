
public class SavingAccount extends BankAccount {

    private double interestRate;

    public SavingAccount(
            String accountNumber,
            String holderName,
            double balance,
            double interestRate) {

        super(accountNumber, holderName, balance);

        if (balance < 50_000) {
            throw new IllegalArgumentException(
                    "Tài khoản tiết kiệm phải có số dư tối thiểu 50.000 VNĐ.");
        }

        if (interestRate < 0 || !Double.isFinite(interestRate)) {
            throw new IllegalArgumentException(
                    "Lãi suất phải là một số không âm hợp lệ.");
        }

        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        if (interestRate < 0 || !Double.isFinite(interestRate)) {
            throw new IllegalArgumentException(
                    "Lãi suất phải là một số không âm hợp lệ.");
        }

        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount)
            throws InsufficientBalanceException, InvalidAmountException {

        if (amount <= 0 || !Double.isFinite(amount)) {
            throw new InvalidAmountException(
                    "Số tiền rút phải lớn hơn 0 và hợp lệ.");
        }

        double remainingBalance = getBalance() - amount;

        if (remainingBalance < 50_000) {
            throw new InsufficientBalanceException(
                    "Không thể rút tiền vì số dư sau giao dịch phải còn ít nhất 50.000 VNĐ.");
        }

        setBalance(remainingBalance);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Lãi suất: " + interestRate);
    }
}
