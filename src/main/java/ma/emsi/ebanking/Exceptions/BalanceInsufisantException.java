package ma.emsi.ebanking.Exceptions;

public class BalanceInsufisantException extends Exception {
    public BalanceInsufisantException(String soldeInsufisant) {
        super(soldeInsufisant);
    }
}
