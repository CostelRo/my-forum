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


    public String toString()
    {
        return super.getMessage();
    }
}
