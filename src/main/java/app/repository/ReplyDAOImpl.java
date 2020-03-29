package app.repository;


import app.model.dto.ReplyDTO;
import app.repository.api.ReplyDAO;
import app.repository.dbconnection.MySQLConnection;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Repository
public class ReplyDAOImpl implements ReplyDAO
{
    @Override
    public ReplyDTO addReply( ReplyDTO newReply )
    {
        ReplyDTO result = null;

        final String queryInsertReply = "INSERT INTO posts (author_id, message, parent_post_id) VALUES (?, ?, ?)";
        final String queryGetInsertedReply = "SELECT * FROM posts "
                                            + "WHERE id = (SELECT MAX(id) FROM posts WHERE author_id = ?)";

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement( queryInsertReply );
             PreparedStatement pstmt2 = conn.prepareStatement( queryGetInsertedReply ) )
        {
            // insert the new reply
            pstmt1.setInt( 1, newReply.getAuthorID() );
            pstmt1.setString( 2, newReply.getMessage() );
            pstmt1.setInt( 3, newReply.getParentPostId() );
            pstmt1.executeUpdate();

            // get the last reply (just inserted) from database
            pstmt2.setInt( 1, newReply.getAuthorID() );
            ResultSet rs = pstmt2.executeQuery();

            while( rs.next() )
            {
                result = new ReplyDTO( rs.getInt( "id" ),
                                       rs.getInt( "author_id" ),
                                       rs.getString( "message" ),
                                       rs.getTimestamp( "timestamp" ).toLocalDateTime(),
                                       new ArrayList<>(),
                                       new ArrayList<>(),
                                       rs.getInt( "parent_post_id" ) );
            }

            rs.close();
        }
//        catch( SQLIntegrityConstraintViolationException sqlicvex )
//        {
//            sqlicvex.printStackTrace();
//        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return result;
    }


//    private List<PostDTO> createListOfPostDTOfromResultSet( ResultSet rs )
//    {
//        if( rs == null ) return new ArrayList<>();
//
//        List<PostDTO> result = new ArrayList<>();
//
//        try
//        {
//            while( rs.next() )
//            {
//                result.add( new PostDTO( rs.getInt( "id" ),
//                                         rs.getInt( "author_id" ),
//                                         rs.getString( "message" ),
//                                         rs.getTimestamp( "timestamp" ).toLocalDateTime(),
//                                         new ArrayList<>(),
//                                         new ArrayList<>() ) );
//            }
//        }
//        catch( SQLException sqlex )
//        {
//            sqlex.printStackTrace();
//        }
//
//        return result;
//    }


//    @Override
//    public int deleteReply( int activeUserId, int replyId )
//    {
//        int result = 0;
//
//        final String query = "DELETE FROM posts WHERE author_id = ? AND id = ?";
//
//        try( Connection conn = MySQLConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement( query ))
//        {
//            pstmt.setInt( 1, activeUserId );
//            pstmt.setInt( 2, replyId );
//
//            result = pstmt.executeUpdate();
//        }
//        catch( SQLException sqlex )
//        {
//            sqlex.printStackTrace();
//        }
//
//        return result;
//    }
}
