package app.model.dto;


import javax.validation.constraints.Positive;


public class LikeDTO
{
    @Positive
    private int userId;

    @Positive
    private int postId;


    public LikeDTO() {}


    public LikeDTO( @Positive int userId,
                    @Positive int postId )
    {
        this.userId = userId;
        this.postId = postId;
    }


    public int getUserId()
    {
        return userId;
    }


    public int getPostId()
    {
        return postId;
    }


    @Override
    public String toString()
    {
        return "Post #" + postId + " liked by user #" + userId;
    }
}
