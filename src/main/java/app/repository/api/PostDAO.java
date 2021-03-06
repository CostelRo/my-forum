package app.repository.api;


import app.model.dto.PostDTO;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.List;


public interface PostDAO
{
    PostDTO addPost( PostDTO newPost ) throws SQLIntegrityConstraintViolationException;

    List<PostDTO> getOwnPosts( int activeUserId, Timestamp timestamp );

    List<PostDTO> getFeed( int activeUserId );

    int deletePost( int activeUserId, int postId );
}
