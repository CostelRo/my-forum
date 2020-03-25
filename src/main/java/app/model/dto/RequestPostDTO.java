package app.model.dto;


import javax.validation.constraints.NotNull;


public class RequestPostDTO
{
    /* Author ID will be sent through the POST Request header; security to be implemented later. */

    @NotNull
    private String message;

//    @NotNull
//    private LocalDateTime timestamp;


    public RequestPostDTO() {}


    public RequestPostDTO( @NotNull String message )
    {
        this.message = message;
    }


    public String getMessage()
    {
        return message;
    }


//    public LocalDateTime getTimestamp()
//    {
//        return timestamp;
//    }


//    @Override
//    public boolean equals( Object obj )
//    {
//        if( obj == null || !(obj instanceof RequestPostDTO) ) return false;
//
//        RequestPostDTO other = (RequestPostDTO) obj;
//        return this.timestamp.equals( other.getTimestamp() );
//    }


//    @Override
//    public int hashCode()
//    {
//        return timestamp.hashCode();
//    }


//    @Override
//    public String toString()
//    {
//        return "(" + timestamp + ") " + message;
//    }
    public String toString()
    {
        return message;
    }
}
