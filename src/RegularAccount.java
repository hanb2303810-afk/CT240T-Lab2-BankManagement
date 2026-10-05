
public class RegularAccount extends BankAccount {

    public RegularAccount(
            String accountNumber,
            String holderName,
            double balance) {

        super(accountNumber, holderName, balance);
    }

    @Override
    public void withdraw(double amount)
            throws InsufficientBalanceException, InvalidAmountException {

        if (amount <= 0 || !Double.isFinite(amount)) {
            throw new InvalidAmountException(
                    "Số tiền rút phải lớn hơn 0 và hợp lệ.");
        }

        if (amount > getBalance()) {
            throw new InsufficientBalanceException(
                    "Số dư không đủ để thực hiện giao dịch.");
        }

        setBalance(getBalance() - amount);
    }
}