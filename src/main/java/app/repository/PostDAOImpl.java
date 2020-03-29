package app.repository;


import app.model.dto.PostDTO;
import app.model.dto.ReplyDTO;
import app.repository.api.PostDAO;
import app.repository.dbconnection.MySQLConnection;

import com.sun.rowset.CachedRowSetImpl;
import org.springframework.stereotype.Repository;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
            int insertedRows = pstmt1.executeUpdate();

            // get the last post (just inserted) from database
            if( insertedRows > 0 )
            {
                pstmt2.setInt( 1, newPost.getAuthorID() );
                ResultSet rs = pstmt2.executeQuery();

                while ( rs.next() )
                {
                    result = new PostDTO( rs.getInt( "id" ),
                                          rs.getInt( "author_id" ),
                                          rs.getString( "message" ),
                                          rs.getTimestamp( "timestamp" ).toLocalDateTime(),
                                          new ArrayList<>(),        // new posts have no replies yet
                                          new ArrayList<>() );      // new posts have no likes yet
                }

                rs.close();
            }
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
        final String query = "SELECT * FROM posts WHERE author_id = ?" + timeLimit;

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement( query ) )
        {
            pstmt.setInt( 1, activeUserId );
            if( timestamp != null ) { pstmt.setTimestamp( 2, timestamp ); }

            ResultSet rs = pstmt.executeQuery();

            // a CachedRowSet is better for passing around
            CachedRowSet crs = new CachedRowSetImpl();
            crs.populate( rs );

            // extracting data
            result = createListOfPostDTOfromResultSet( activeUserId, crs );

            // closing the result sets
            crs.close();
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

        String query = "SELECT * FROM posts "
                        + "WHERE author_id IN (SELECT followed_user_id FROM followers WHERE user_id = ?)";

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement( query ) )
        {
            pstmt.setInt( 1, activeUserId );
            ResultSet rs = pstmt.executeQuery();

            // a CachedRowSet is better for passing around
            CachedRowSet crs = new CachedRowSetImpl();
            crs.populate( rs );

            // extracting data
            result = createListOfPostDTOfromResultSet( activeUserId, crs );

            // closing the result sets
            crs.close();
            rs.close();
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return result;
    }


    private List<PostDTO> createListOfPostDTOfromResultSet( int activeUserId, ResultSet crs )
    {
        List<PostDTO> result = new ArrayList<>();

        if( crs != null && activeUserId > 0 )
        {
            Map<Integer, PostDTO> tempPosts = new HashMap<>();
            int postID;
            int parentPostId;

            // (1) Create Post/Reply object, without Likes & Replies data
            try
            {
                while( crs.next() )
                {
                    postID = crs.getInt( "id" );
                    parentPostId = crs.getInt( "parent_post_id" );

                    if( parentPostId > 0 )
                    {
                        tempPosts.putIfAbsent( postID, new ReplyDTO( postID,
                                                                     crs.getInt( "author_id" ),
                                                                     crs.getString( "message" ),
                                                                     crs.getTimestamp( "timestamp" ).toLocalDateTime(),
                                                                     new ArrayList<>(),
                                                                     new ArrayList<>(),
                                                                     parentPostId ) );
                    }
                    else
                    {
                        tempPosts.putIfAbsent( postID, new PostDTO( postID,
                                                                    crs.getInt( "author_id" ),
                                                                    crs.getString( "message" ),
                                                                    crs.getTimestamp( "timestamp" ).toLocalDateTime(),
                                                                    new ArrayList<>(),
                                                                    new ArrayList<>() ) );
                    }
                }
            }
            catch( SQLException sqlex )
            {
                sqlex.printStackTrace();
            }

            // (2) Add associated Likes & Replies to the Post/Reply objects:
            // (2a) prepare list of post-IDs so we can execute below only 1 database query for all post IDs
            StringBuilder idListAsString = new StringBuilder();
            for( int id : tempPosts.keySet() ) { idListAsString.append( id ).append( ", " ); }
            idListAsString.replace( idListAsString.length() - 2, idListAsString.length(), "" );

            // (2b) get Like data & insert it into their corresponding Posts
            Map<Integer, List<String>> likesData = getLikeInfoForListOfPostID( idListAsString.toString() );
            for( int postId : likesData.keySet() )
            {
                tempPosts.get( postId ).setLikes( likesData.get( postId ) );
            }

            // (2c) get Reply data & insert it into their corresponding Posts
            Map<Integer, List<ReplyDTO>> repliesData = getReplyInfoForListOfPostID( idListAsString.toString() );

            for( int postId : repliesData.keySet() )
            {
                tempPosts.get( postId ).setReplies( repliesData.get( postId ) );
            }

            // sort posts list by post-ID (as defined in Post object)
            List<PostDTO> sortedPosts = new ArrayList<>( tempPosts.values() );
            Collections.sort( sortedPosts );
            result.addAll( sortedPosts );
        }

        return result;
    }


    private Map<Integer, List<String>> getLikeInfoForListOfPostID( String postIdList )
    {
        Map<Integer, List<String>> result = new HashMap<>();

        if( postIdList != null )
        {
            // get Likes data from database
            final String query = "SELECT u.username, l.post_id FROM likes l LEFT JOIN users u ON l.user_id = u.id "
                                 + "WHERE l.post_id IN (" + postIdList + ")";

            try( Connection conn = MySQLConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement( query );
                 ResultSet rs = pstmt.executeQuery() )
            {
                while( rs.next() )
                {
                    try
                    {
                        int currentPostId = rs.getInt( "post_id" );
                        result.putIfAbsent( currentPostId, new ArrayList<>() );
                        result.get( currentPostId ).add( rs.getString( "username" ) );
                    }
                    catch( SQLException sqlex )
                    {
                        sqlex.printStackTrace();
                    }
                }
            }
            catch( SQLException sqlex )
            {
                sqlex.printStackTrace();
            }
        }

        return result;
    }


    private Map<Integer, List<ReplyDTO>> getReplyInfoForListOfPostID( String postIdList )
    {
        Map<Integer, List<ReplyDTO>> result = new HashMap<>();

        if( postIdList != null )
        {

            // get Replies data from database
            final String query = "SELECT * FROM posts "
                                 + "WHERE parent_post_id IN (SELECT id FROM posts WHERE id IN (" + postIdList + "))";

            try( Connection conn = MySQLConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement( query );
                 ResultSet rs = pstmt.executeQuery() )
            {
                while( rs.next() )
                {
                    try
                    {
                        int parentPostId = rs.getInt( "parent_post_id" );
                        result.putIfAbsent( parentPostId, new ArrayList<>() );
                        result.get( parentPostId ).add( new ReplyDTO( rs.getInt( "id" ),
                                                                          rs.getInt( "author_id" ),
                                                                          rs.getString( "message" ),
                                                                          rs.getTimestamp( "timestamp" ).toLocalDateTime(),
                                                                          new ArrayList<>(),
                                                                          new ArrayList<>(),
                                                                          parentPostId ) );
                    }
                    catch( SQLException sqlex )
                    {
                        sqlex.printStackTrace();
                    }
                }
            }
            catch( SQLException sqlex )
            {
                sqlex.printStackTrace();
            }
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
