package app.repository;


import app.model.dto.PostDTO;
import app.repository.api.PostDAO;
import app.repository.dbconnection.MySQLConnection;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Repository
public class PostDAOImpl implements PostDAO
{
    @Override
    public PostDTO addPost( PostDTO newPost )
    {
        PostDTO result = null;

        final String queryInsertPost = "INSERT INTO posts (author_id, message) VALUES (?, ?)";
        final String queryGetInsertedPost = "SELECT * FROM posts "
                                            + "WHERE id = (SELECT MAX(id) FROM posts WHERE author_id = ?)";

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement( queryInsertPost );
             PreparedStatement pstmt2 = conn.prepareStatement( queryGetInsertedPost ) )
        {
            // insert the new post
            pstmt1.setInt( 1, newPost.getAuthorID() );
            pstmt1.setString( 2, newPost.getMessage() );
            pstmt1.executeUpdate();

            // get the last post (just inserted) from database
            pstmt2.setInt( 1, newPost.getAuthorID() );
            ResultSet rs = pstmt2.executeQuery();

            while( rs.next() )
            {
                result = new PostDTO( rs.getInt( "id" ),
                                      rs.getInt( "author_id" ),
                                      rs.getString( "message" ),
                                      rs.getTimestamp( "timestamp" ).toLocalDateTime(),
                                      new ArrayList<>(),
                                      new ArrayList<>() );
            }

            rs.close();
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return result;
    }


    @Override
    public List<PostDTO> getOwnPosts( int activeUserId, Timestamp timestamp )
    {
        List<PostDTO> result = new ArrayList<>();

        String timeLimit = (timestamp != null) ? " AND `timestamp` > ?" : "";
        final String query = "SELECT id, author_id, message, `timestamp` FROM posts WHERE author_id = ?" + timeLimit;

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement( query ) )
        {
            pstmt.setInt( 1, activeUserId );
            if( timestamp != null ) { pstmt.setTimestamp( 2, timestamp ); }

            ResultSet rs = pstmt.executeQuery();

            result = createListOfPostDTOfromResultSet( rs );

            rs.close();
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return result;
    }


    @Override
    public List<PostDTO> getFeed( int activeUserId )
    {
        List<PostDTO> result = new ArrayList<>();

        String query = "SELECT id, author_id, message, `timestamp` FROM posts "
                        + "WHERE author_id IN (SELECT followed_user_id FROM followers WHERE user_id = ?)";

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement( query ) )
        {
            pstmt.setInt( 1, activeUserId );
            ResultSet rs = pstmt.executeQuery();

            result = createListOfPostDTOfromResultSet( rs );

            rs.close();
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return result;
    }


    private List<PostDTO> createListOfPostDTOfromResultSet( ResultSet rs )
    {
        if( rs == null ) return new ArrayList<>();

        List<PostDTO> result = new ArrayList<>();

        try
        {
            while( rs.next() )
            {
                result.add( new PostDTO( rs.getInt( "id" ),
                                         rs.getInt( "author_id" ),
                                         rs.getString( "message" ),
                                         rs.getTimestamp( "timestamp" ).toLocalDateTime(),
                                         new ArrayList<>(),
                                         new ArrayList<>() ) );
            }
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return result;
    }


    @Override
    public int deletePost( int activeUserId, int postId )
    {
        int result = 0;

        final String query = "DELETE FROM posts WHERE author_id = ? AND id = ?";

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement( query ))
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
