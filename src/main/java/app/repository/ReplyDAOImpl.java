package app.repository;


import app.model.dto.ReplyDTO;
import app.repository.api.ReplyDAO;
import app.repository.dbconnection.MySQLConnection;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;


@Repository
public class ReplyDAOImpl implements ReplyDAO
{
    @Override
    public ReplyDTO addReply( ReplyDTO newReply ) throws SQLIntegrityConstraintViolationException
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
                                       new ArrayList<>(),                           // new replies have no replies yet
                                       new ArrayList<>(),                           // new replies have no likes yet
                                       rs.getInt( "parent_post_id" ) );
            }

            rs.close();
        }
        catch( SQLIntegrityConstraintViolationException sqlicvex )
        {
            throw sqlicvex;
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return result;
    }
}
