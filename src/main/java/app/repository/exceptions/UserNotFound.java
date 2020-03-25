package app.repository.exceptions;


public class UserNotFound extends Exception
{
    public UserNotFound( String message, Throwable cause )
    {
        super( message, cause );
    }


    public UserNotFound( String message )
    {
        super( message );
    }
}
