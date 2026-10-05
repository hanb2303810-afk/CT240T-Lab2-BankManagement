
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankManager {

    private final Map<String, BankAccount> accounts = new HashMap<>();

    public void addAccount(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException(
                    "Tài khoản không được để trống.");
        }

        String accountNumber = account.getAccountNumber();

        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException(
                    "Số tài khoản " + accountNumber + " đã tồn tại.");
        }

        accounts.put(accountNumber, account);
    }

    public BankAccount findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void transferMoney(
            String fromAcc,
            String toAcc,
            double amount)
            throws InsufficientBalanceException, InvalidAmountException {

        if (amount <= 0 || !Double.isFinite(amount)) {
            throw new InvalidAmountException(
                    "Số tiền chuyển phải lớn hơn 0 và hợp lệ.");
        }

        BankAccount sender = findAccount(fromAcc);
        BankAccount receiver = findAccount(toAcc);

        if (sender == null) {
            throw new IllegalArgumentException(
                    "Không tìm thấy tài khoản nguồn: " + fromAcc);
        }

        if (receiver == null) {
            throw new IllegalArgumentException(
                    "Không tìm thấy tài khoản nhận: " + toAcc);
        }

        if (fromAcc.equals(toAcc)) {
            throw new IllegalArgumentException(
                    "Tài khoản nguồn và tài khoản nhận phải khác nhau.");
        }

        // Rút tiền từ tài khoản nguồn trước.
        sender.withdraw(amount);

        // Sau khi rút thành công, nạp tiền vào tài khoản nhận.
        receiver.deposit(amount);
    }

    public double calculateTotalBalance(
            List<? extends BankAccount> accounts) {

        if (accounts == null) {
            throw new IllegalArgumentException(
                    "Danh sách tài khoản không được để trống.");
        }

        double total = 0;

        for (BankAccount account : accounts) {
            if (account == null) {
                throw new IllegalArgumentException(
                        "Danh sách có tài khoản không hợp lệ.");
            }

            total += account.getBalance();
        }

        return total;
    }

    public List<BankAccount> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }
}
