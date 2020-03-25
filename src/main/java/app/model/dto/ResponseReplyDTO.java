package app.model.dto;


import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


public class ResponseReplyDTO extends ResponsePostDTO
{
    @NotNull
    private int parentPostId;

    @NotNull
    private boolean isPublic;


    public ResponseReplyDTO() {}


    public ResponseReplyDTO( @NotNull int id,
                             @NotNull int authorID,
                             @NotNull String message,
                             @NotNull LocalDateTime timestamp,
                             @NotNull List<ResponseReplyDTO> replies,
                             @NotNull List<ResponseLikeDTO> likes,
                             @NotNull int parentPostId,
                             @NotNull boolean isPublic )
    {
        super( id, authorID, message, timestamp, replies, likes );
        this.parentPostId = parentPostId;
        this.isPublic = isPublic;
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


    public boolean isPublic()
    {
        return isPublic;
    }


    @Override
    public String toString()
    {
        return "[post #" + super.getId() + " by " + super.getAuthorID() +", from " + super.getTimestamp() + "]\n"
                + super.getMessage();
    }
}
