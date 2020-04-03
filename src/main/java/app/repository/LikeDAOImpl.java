package app.repository;


import app.model.dto.LikeDTO;
import app.repository.api.LikeDAO;
import app.repository.dbconnection.MySQLConnection;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


@Repository
public class LikeDAOImpl implements LikeDAO
{
    @Override
    public LikeDTO addLike( LikeDTO newLike ) throws SQLIntegrityConstraintViolationException
    {
        LikeDTO result = null;

        // authors cannot add a like to their own posts
        final String query = "INSERT INTO likes (user_id, post_id) VALUES (?, ?)";

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement( query ) )
        {
            // insert the new post
            pstmt.setInt( 1, newLike.getUserId() );
            pstmt.setInt( 2, newLike.getPostId() );
            int status = pstmt.executeUpdate();

            if( status == 1 ) { result = newLike; }
        }
        catch( SQLIntegrityConstraintViolationException sqlcvex )
        {
            throw sqlcvex;
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return result;
    }


    /**
     * Deletes a particular like from database.
     * NOTE: Likes associated with a post ID are automatically deleted from database when that Post is deleted,
     * due to the declared foreign key in database table having referential action set "ON DELETE CASCADE".
     * @param activeUserId  the database ID of the active user
     * @param postId        the database ID of the post to which the like to be deleted is associated with
     * @return              the number of database records deleted, or 0 if none
     */
    @Override
    public int deleteLike( int activeUserId, int postId )
    {
        int result = 0;

        final String query = "DELETE FROM likes WHERE user_id = ? AND post_id = ?";

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement( query ) )
        {
            pstmt.setInt( 1, activeUserId );
            pstmt.setInt( 2, postId );

            result = pstmt.executeUpdate();
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return result;
    }
}
