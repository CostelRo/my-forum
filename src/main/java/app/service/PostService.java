package app.service;


import app.model.dto.PostDTO;
import app.model.dto.RequestPostDTO;
import app.model.dto.ResponsePostDTO;
import app.repository.api.PostDAO;
import app.service.converters.PostConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class PostService
{
    private PostDAO postDAO;


    @Autowired
    public PostService( PostDAO postDAO )
    {
        this.postDAO = postDAO;
    }


    public PostDAO getPostDAO()
    {
        return postDAO;
    }


    public ResponsePostDTO registerNewPost( int activeUserId, RequestPostDTO newPost )
    {
        if( newPost == null ) throw new IllegalArgumentException( "Missing Post object." );
        if( activeUserId <= 0 ) throw new IllegalArgumentException( "Incorrect active user ID." );

        PostDTO result = postDAO.addPost( PostConverter.fromRequestDTOtoDTO( activeUserId, newPost ) );

        if( result != null ) { return PostConverter.fromDTOtoResponseDTO( result ); }
        else { return null; }
    }


    public List<ResponsePostDTO> getOwnPosts( int activeUserId, LocalDateTime timestamp )
    {
        List<PostDTO> ownPosts = ( timestamp == null )
                                 ? postDAO.getOwnPosts( activeUserId, null )
                                 : postDAO.getOwnPosts( activeUserId, Timestamp.valueOf( timestamp ) );

        return PostConverter.fromListOfDTOtoListOfResponseDTO( ownPosts );
    }


    public List<ResponsePostDTO> getFeed( int activeUserId )
    {
        if( activeUserId <= 0 ) throw new IllegalArgumentException( "User ID must be bigger than 0." );

        return PostConverter.fromListOfDTOtoListOfResponseDTO( postDAO.getFeed( activeUserId ) );
    }


    public int deletePost( int activeUserId, int postId )
    {
        if( activeUserId <= 0 || postId <= 0 )
        {
            throw new IllegalArgumentException( "User-ID and post-ID must be valid." );
        }

        return postDAO.deletePost( activeUserId, postId ) == 1
               ? postId
               : 0;
    }
}
