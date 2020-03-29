package app.model.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;


public class PostDTO implements Comparable<PostDTO>
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
    private List<String> likes;    // usernames


    public PostDTO() {}


    public PostDTO( @Positive int id,
                    @PositiveOrZero int authorID,
                    @NotNull String message,
                    @NotNull LocalDateTime timestamp,
                    @NotNull List<ReplyDTO> replies,
                    @NotNull List<String> likes )
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


    public List<String> getLikes()
    {
        return likes;
    }


    public void setReplies( List<ReplyDTO> replies )
    {
        this.replies = replies;
    }


    public void setLikes( List<String> likes )
    {
        this.likes = likes;
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
        return "Post #" + id + " by user #" + authorID +", on " + timestamp + "\n" + message;
    }


    @Override
    public int compareTo( PostDTO other )
    {
        if( other == null ) return 1;

        return this.id - other.id;
    }
}
