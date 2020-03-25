package app.model.dto;


import javax.validation.constraints.NotNull;


public class RequestReplyDTO extends RequestPostDTO
{
    /* Author ID will be sent through the POST Request header; security to be implemented later. */

    @NotNull
    private int parentPostId;

    @NotNull
    private boolean isPublic;


    public RequestReplyDTO() {}


    public RequestReplyDTO( @NotNull String message,
                            @NotNull int parentPostId,
                            @NotNull boolean isPublic )
    {
        super( message );
        this.parentPostId = parentPostId;
        this.isPublic = isPublic;
    }


    public String getMessage()
    {
        return super.getMessage();
    }


    public int getParentPostId()
    {
        return parentPostId;
    }


    public boolean isPublic()
    {
        return isPublic;
    }


//    @Override
//    public boolean equals( Object obj )
//    {
//        if( obj == null || !(obj instanceof RequestReplyDTO) ) return false;
//
//        RequestReplyDTO other = (RequestReplyDTO) obj;
//        return super.getTimestamp().equals( other.getTimestamp() );
//    }


//    @Override
//    public int hashCode()
//    {
//        return super.getTimestamp().hashCode();
//    }


//    @Override
//    public String toString()
//    {
//        return "(" + super.getTimestamp() + ") " + super.getMessage();
//    }
    public String toString()
    {
        return super.getMessage();
    }
}
