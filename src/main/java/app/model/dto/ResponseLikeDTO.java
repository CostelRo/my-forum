package app.model.dto;


public class ResponseLikeDTO
{
    private int userId;
    private int postId;


    public ResponseLikeDTO() {}


    public ResponseLikeDTO( int userId,
                            int postId )
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
