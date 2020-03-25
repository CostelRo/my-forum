package app.model.dto;


import javax.validation.constraints.NotNull;


public class LikeDTO
{
    @NotNull
    private int userId;

    @NotNull
    private int postId;


    public LikeDTO() {}


    public LikeDTO( @NotNull int userId,
                    @NotNull int postId )
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
        return "by " + userId;
    }
}
