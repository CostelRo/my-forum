package app.service;


import app.model.dto.LikeDTO;
import app.model.dto.ResponseLikeDTO;
import app.repository.api.LikeDAO;
import app.service.converters.LikeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;


@Service
public class LikeService
{
    private LikeDAO likeDAO;


    @Autowired
    public LikeService( LikeDAO likeDAO )
    {
        this.likeDAO = likeDAO;
    }


    public LikeDAO getLikeDAO()
    {
        return likeDAO;
    }


    public ResponseLikeDTO registerNewLike( int activeUserId, int postId )
                                            throws SQLIntegrityConstraintViolationException
    {
        if( activeUserId <= 0 || postId <= 0 ) { throw new IllegalArgumentException( "User-ID and post-ID must be valid." ); }

        LikeDTO result = likeDAO.addLike( new LikeDTO( activeUserId, postId ) );

        if( result != null ) { return LikeConverter.fromDTOtoResponseDTO( result ); }
        else { return null; }
    }


    public int deleteLike( int activeUserId, int postId )
    {
        if( activeUserId <= 0 || postId <= 0 ) { throw new IllegalArgumentException( "User-ID and post-ID must be valid." ); }

        return likeDAO.deleteLike( activeUserId, postId ) == 1
               ? postId
               : 0;
    }
}
