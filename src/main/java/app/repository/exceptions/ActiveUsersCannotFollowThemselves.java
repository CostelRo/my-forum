package app.repository.exceptions;


public class ActiveUsersCannotFollowThemselves extends Exception
{
    public ActiveUsersCannotFollowThemselves( String message, Throwable cause )
    {
        super( message, cause );
    }


    public ActiveUsersCannotFollowThemselves( String message )
    {
        super( message );
    }
}
