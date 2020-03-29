package app.repository;


import app.model.dto.LikeDTO;
import app.repository.api.LikeDAO;
import app.repository.dbconnection.MySQLConnection;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Repository
public class LikeDAOImpl implements LikeDAO
{
    @Override
    public LikeDTO addLike( LikeDTO newLike )
    {
        LikeDTO result = null;

        // authors cannot add a like to their own posts
        final String query = "INSERT INTO likes (user_id, post_id) "
                             + "SELECT ?, ? "
                             + "WHERE ? IN (SELECT id FROM users) AND ? IN (SELECT id FROM posts) "
                             + "AND NOT EXISTS (SELECT author_id, id FROM posts WHERE author_id = ? AND id = ?)";

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement( query ) )
        {
            // insert the new post
            pstmt.setInt( 1, newLike.getUserId() );
            pstmt.setInt( 2, newLike.getPostId() );
            pstmt.setInt( 3, newLike.getUserId() );
            pstmt.setInt( 4, newLike.getPostId() );
            pstmt.setInt( 5, newLike.getUserId() );
            pstmt.setInt( 6, newLike.getPostId() );
            int status = pstmt.executeUpdate();

            if( status == 1 ) { result = newLike; }
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return result;
    }


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
