package app.repository.exceptions;


public class ActiveUsersCannotUnfollowThemselves extends Exception
{
    public ActiveUsersCannotUnfollowThemselves( String message, Throwable cause )
    {
        super( message, cause );
    }


    public ActiveUsersCannotUnfollowThemselves( String message )
    {
        super( message );
    }
}
