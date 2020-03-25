package app.model.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


public class RequestReplyDTO extends RequestPostDTO
{
    @Positive
    private int parentPostId;


    public RequestReplyDTO() {}


    public RequestReplyDTO( @NotNull String message,
                            @Positive int parentPostId )
    {
        super( message );
        this.parentPostId = parentPostId;
    }


    public String getMessage()
    {
        return super.getMessage();
    }


    public int getParentPostId()
    {
        return parentPostId;
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
