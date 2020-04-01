package app.model.dto;


import javax.validation.constraints.NotEmpty;


public class RequestPostDTO
{
    /* Author ID will be sent through the POST Request header; security to be implemented later. */

    @NotEmpty( message = "The Message must be provided." )
    private String message;


    public RequestPostDTO() {}


    public RequestPostDTO( String message )
    {
        this.message = message;
    }


    public String getMessage()
    {
        return message;
    }


    public String toString()
    {
        return message;
    }
}
