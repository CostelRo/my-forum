package app.model.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;


public class ResponseReplyDTO extends ResponsePostDTO
{
    @Positive
    private int parentPostId;


    public ResponseReplyDTO() {}


    public ResponseReplyDTO( @Positive int id,
                             @Positive int authorID,
                             @NotNull String message,
                             @NotNull LocalDateTime timestamp,
                             @NotNull List<ResponseReplyDTO> replies,
                             @NotNull List<ResponseLikeDTO> likes,
                             @Positive int parentPostId )
    {
        super( id, authorID, message, timestamp, replies, likes );
        this.parentPostId = parentPostId;
    }


    public int getId()
    {
        return super.getId();
    }


    public int getAuthorID()
    {
        return super.getAuthorID();
    }


    public String getMessage()
    {
        return super.getMessage();
    }


    public LocalDateTime getTimestamp()
    {
        return super.getTimestamp();
    }


    public List<ResponseReplyDTO> getReplies()
    {
        return super.getReplies();
    }


    public List<ResponseLikeDTO> getLikes()
    {
        return super.getLikes();
    }


    public int getParentPostId()
    {
        return parentPostId;
    }


    @Override
    public String toString()
    {
        return "[post #" + super.getId() + " by " + super.getAuthorID() +", from " + super.getTimestamp() + "]\n"
                + super.getMessage();
    }
}
