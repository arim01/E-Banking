package ma.emsi.ebanking.Exceptions;

public class CustomerNotFoundExceptions extends Exception {

    //Pour creer une exception non surveillée on fait extends runRimeExcxeption
    //sinon  Exception qui est surveillée et qui necessite try catch
    //ou throws nomException a cote de la signature de la methode
    public CustomerNotFoundExceptions(String message   ) {
        super(message);
    }
}
