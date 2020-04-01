package app.model.dto;


import java.time.LocalDateTime;
import java.util.List;


public class ResponseReplyDTO extends ResponsePostDTO
{
    private int parentPostId;


    public ResponseReplyDTO() {}


    public ResponseReplyDTO( int id,
                             int authorID,
                             String message,
                             LocalDateTime timestamp,
                             List<ResponseReplyDTO> replies,
                             List<String> likes,
                             int parentPostId )
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


    public List<String> getLikes()
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
        return "Post #" + super.getId() + " by user #" + super.getAuthorID()
                + " (reply to post #" + this.getParentPostId() + ")" + super.getAuthorID()
                + ", from " + super.getTimestamp() + "\n" + super.getMessage();
    }
}
