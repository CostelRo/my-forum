package app.model.dto;


import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


public class ReplyDTO extends PostDTO
{
    /* Author ID will be sent through the POST Request header; security to be implemented later. */

    @NotNull
    private int parentPostId;

    @NotNull
    private boolean isPublic;


    public ReplyDTO() {}


    public ReplyDTO( @NotNull int id,
                     @NotNull int authorID,
                     @NotNull String message,
                     @NotNull LocalDateTime timestamp,
                     @NotNull List<ReplyDTO> replies,
                     @NotNull List<LikeDTO> likes,
                     @NotNull int parentPostId,
                     @NotNull boolean isPublic )
    {
        super( id, authorID, message, timestamp, replies, likes );
        this.parentPostId = parentPostId;
        this.isPublic = isPublic;
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


    public List<LikeDTO> getLikes()
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
        return "(" + super.getTimestamp() + ") " + super.getMessage();
    }
}
