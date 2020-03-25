package app.repository.api;


import app.model.dto.LikeDTO;


public interface LikeDAO
{
    LikeDTO addLike( LikeDTO newLike );

    int deleteLike( int activeUserId, int postId );
}
