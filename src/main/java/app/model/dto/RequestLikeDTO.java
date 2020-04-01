package app.model.dto;


import javax.validation.constraints.Positive;


public class RequestLikeDTO
{
    @Positive
    private int postId;


    public RequestLikeDTO() {}


    public RequestLikeDTO( int postId )
    {
        this.postId = postId;
    }


    public int getPostId()
    {
        return postId;
    }


    @Override
    public String toString()
    {
        return "Like for post #" + postId;
    }
}
