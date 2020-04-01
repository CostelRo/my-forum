package app.repository.api;


import app.model.dto.LikeDTO;

import java.sql.SQLIntegrityConstraintViolationException;


public interface LikeDAO
{
    LikeDTO addLike( LikeDTO newLike ) throws SQLIntegrityConstraintViolationException;

    int deleteLike( int activeUserId, int postId );
}
