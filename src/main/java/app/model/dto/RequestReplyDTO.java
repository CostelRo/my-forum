package app.model.dto;


import javax.validation.constraints.Positive;


public class RequestReplyDTO extends RequestPostDTO
{
    @Positive
    private int parentPostId;


    public RequestReplyDTO() {}


    public RequestReplyDTO( String message,
                            int parentPostId )
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


    public String toString()
    {
        return super.getMessage();
    }
}
