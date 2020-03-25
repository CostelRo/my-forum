package app.model.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;


public class PostDTO
{
    /* Author ID will be sent through the POST Request header; security to be implemented later. */

    @Positive
    private int id;

    @PositiveOrZero
    private int authorID;

    @NotNull
    private String message;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    private List<ReplyDTO> replies;

    @NotNull
    private List<LikeDTO> likes;


    public PostDTO() {}


    public PostDTO( @Positive int id,
                    @PositiveOrZero int authorID,
                    @NotNull String message,
                    @NotNull LocalDateTime timestamp,
                    @NotNull List<ReplyDTO> replies,
                    @NotNull List<LikeDTO> likes )
    {
        this.id = id;
        this.authorID = authorID;
        this.message = message;
        this.timestamp = timestamp;
        this.replies = replies;
        this.likes = likes;
    }


    public int getId()
    {
        return id;
    }


    public int getAuthorID()
    {
        return authorID;
    }


    public String getMessage()
    {
        return message;
    }


    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }


    public List<ReplyDTO> getReplies()
    {
        return replies;
    }


    public List<LikeDTO> getLikes()
    {
        return likes;
    }


    @Override
    public boolean equals( Object obj )
    {
        if( obj == null || !(obj instanceof PostDTO) ) return false;

        PostDTO other = (PostDTO) obj;
        return this.timestamp.equals( other.getTimestamp() );
    }


    @Override
    public int hashCode()
    {
        return timestamp.hashCode();
    }


    @Override
    public String toString()
    {
        return "[post #" + id + " by " + authorID +", on " + timestamp + "]\n" + message;
    }
}
