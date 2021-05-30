package nl.novi.autogarage_roy_kersten.exception;

public class UsernameNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException(String username) {
        super("Cannot find user " + username);
    }
}
