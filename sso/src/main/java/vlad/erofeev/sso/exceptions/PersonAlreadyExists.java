package vlad.erofeev.sso.exceptions;

public class PersonAlreadyExists extends Exception{

    public PersonAlreadyExists(String email) {
        super(String.format("Person with email = %s already exists", email));
    }
}
