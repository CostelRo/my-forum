package app.model.dto;


import javax.validation.constraints.NotNull;


public class RequestPostDTO
{
    /* Author ID will be sent through the POST Request header; security to be implemented later. */

    @NotNull
    private String message;


    public RequestPostDTO() {}


    public RequestPostDTO( @NotNull String message )
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
