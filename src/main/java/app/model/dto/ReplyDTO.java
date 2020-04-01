package app.model.dto;


import java.time.LocalDateTime;
import java.util.List;


public class ReplyDTO extends PostDTO
{
    private int parentPostId;


    public ReplyDTO() {}


    public ReplyDTO( int id,
                     int authorID,
                     String message,
                     LocalDateTime timestamp,
                     List<ReplyDTO> replies,
                     List<String> likes,
                     int parentPostId )
    {
        super( id, authorID, message, timestamp, replies, likes );
        this.parentPostId = parentPostId;
    }


    public int getID()
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


    public List<ReplyDTO> getReplies()
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
    public boolean equals( Object obj )
    {
        if( obj == null || !(obj instanceof ReplyDTO) ) return false;

        ReplyDTO other = (ReplyDTO) obj;
        return super.getTimestamp().equals( other.getTimestamp() );
    }


    @Override
    public int hashCode()
    {
        return super.getTimestamp().hashCode();
    }


    @Override
    public String toString()
    {
        return "Post #" + getID() + " by user #" + getAuthorID()
                + " (reply to post #" + this.getParentPostId() + "), on " + super.getTimestamp() + "\n"
                + super.getMessage();
    }
}
