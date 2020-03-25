package app.repository.exceptions;


public class DuplicateEntryForUniqueDBRecord extends Exception
{
    public DuplicateEntryForUniqueDBRecord( String message, Throwable cause )
    {
        super( message, cause );
    }


    public DuplicateEntryForUniqueDBRecord( String message )
    {
        super( message );
    }
}
