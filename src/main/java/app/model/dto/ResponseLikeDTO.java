package app.model.dto;


import javax.validation.constraints.Positive;


public class ResponseLikeDTO
{
    @Positive
    private int userId;

    @Positive
    private int postId;


    public ResponseLikeDTO() {}


    public ResponseLikeDTO( @Positive int userId,
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
