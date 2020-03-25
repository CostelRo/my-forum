package app.repository;


import app.model.dto.PostDTO;
import app.model.dto.UserDTO;
import app.repository.api.UserDAO;
import app.repository.dbconnection.MySQLConnection;
import app.repository.exceptions.DuplicateEntryForUniqueDBRecord;
import app.repository.exceptions.UserNotFound;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO
{
    @Override
    public UserDTO addUser( UserDTO newUser ) throws DuplicateEntryForUniqueDBRecord
    {
        final String query = "INSERT INTO users (first_name, last_name, email, username, password) VALUES (?, ?, ?, ?, ?)";

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement( query ) )
        {
            pstmt.setString( 1, newUser.getFirstName() );
            pstmt.setString( 2, newUser.getLastName() );
            pstmt.setString( 3, newUser.getEmail() );
            pstmt.setString( 4, newUser.getUsername() );
            pstmt.setString( 5, newUser.getPassword() );

            pstmt.executeUpdate();
        }
        catch( SQLIntegrityConstraintViolationException icvex )
        {
            throw new DuplicateEntryForUniqueDBRecord( "Unique data is already used by another user." );
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return getUserByUsername( newUser.getUsername(), true );
    }


    @Override
    public List<UserDTO> getAllUsers()
    {
        List<UserDTO> result = new ArrayList<>();

        final String query = "SELECT id, first_name, last_name, email, username FROM users";

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement( query );
             ResultSet rs = pstmt.executeQuery() )
        {
            result = createListOfUserDTOfromResultSets( rs );
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return result;
    }


    @Override
    public UserDTO getUserByUsername( String username, boolean isNewUser )
    {
        UserDTO result = null;

        if( username != null && username.length() > 0 )
        {
            if( isNewUser )    // new users can only have very little data in database
            {
                final String queryNewUserData = "SELECT id, first_name, last_name, email, username FROM users "
                                                + "WHERE username = ?";

                try( Connection conn = MySQLConnection.getConnection();
                     PreparedStatement pstmtNewUser = conn.prepareStatement( queryNewUserData ) )
                {
                    pstmtNewUser.setString( 1, username);
                    ResultSet rsNewUser = pstmtNewUser.executeQuery();

                    result = createUserDTOfromResultSets( rsNewUser, null, true );

                    rsNewUser.close();
                }
                catch ( SQLException sqlex )
                {
                    sqlex.printStackTrace();
                }
            }
            else    // old users might have a lot of data in database
            {
                final String queryUserMainData = "SELECT u.id, u.first_name, u.last_name, u.email, u.username, "
                                                    + "p.id postID, p.message, p.timestamp, p.parent_post_id "
                                                    + "FROM users u "
                                                    + "JOIN posts p ON u.id = p.author_id "
                                                    + "WHERE u.username = ? "
                                                    + "ORDER BY postID";

                final String queryFollowedUsers = "SELECT u.username followedUsername "
                                                    + "FROM users u JOIN "
                                                                    + "(SELECT u.username, f.followed_user_id "
                                                                    + "FROM users u JOIN followers f ON u.id = f.user_id "
                                                                    + "WHERE u.username = ?) j "
                                                    + "ON u.id = j.followed_user_id "
                                                    + "ORDER BY followedUsername";

                /*
                 * queries for searching multiple users + their messages & followed users (if >1 username, not just "?")

                final String queryUserMainData = "SELECT u.id userID, u.first_name, u.last_name, u.email, u.username, "
                                             + "p.id postID, p.message, p.timestamp, p.parent_post_id, "
                                             + "FROM users u "
                                             + "JOIN posts p ON userID = p.author_id "
                                             + "WHERE u.username = ? "
                                             + "ORDER BY userID, postID";

                final String queryFollowedUsers = "SELECT j.username followingUsername, u.username followedUsername "
                                                  + "FROM users u JOIN "
                                                                  + "(SELECT u.username, f.followed_user_id "
                                                                  + "FROM users u JOIN followers f ON u.id = f.user_id "
                                                                  + "WHERE u.username = ?) j "
                                                  + "ON u.id = j.followed_user_id "
                                                  + "ORDER BY followingUsername, followedUsername";
                  */

                try ( Connection conn = MySQLConnection.getConnection();
                      PreparedStatement pstmtUserMainData = conn.prepareStatement( queryUserMainData );
                      PreparedStatement pstmtFollowedUsers = conn.prepareStatement( queryFollowedUsers ) )
                {
                    pstmtUserMainData.setString( 1, username );
                    ResultSet rsUserMainData = pstmtUserMainData.executeQuery();

                    pstmtFollowedUsers.setString( 1, username );
                    ResultSet rsFollowedUsers = pstmtFollowedUsers.executeQuery();

                    result = createUserDTOfromResultSets( rsUserMainData, rsFollowedUsers, false );

                    rsUserMainData.close();
                    rsFollowedUsers.close();
                }
                catch ( SQLException sqlex )
                {
                    sqlex.printStackTrace();
                }
            }
        }

        return result;
    }


    /**
     * Creates a list of UserDTO that have their username, first or last name containing a certain string.
     * The list contains only the users' identification data (without their posts & followed-users),
     * in order to avoid sending large volumes of data if the list is large.
     * The users' passwords are hidden by default (they are never extracted from the database).
     *
     * @param  name the string to be searched for
     * @return      a list of UserDTO
     */
    @Override
    public List<UserDTO> getListOfUsersByPartialName( String name )
    {
        List<UserDTO> result = new ArrayList<>();

        final String query = "SELECT id, first_name, last_name, email, username FROM users "
                             + "WHERE username LIKE ? OR first_name LIKE ? OR last_name LIKE ?";

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement( query ) )
        {
            pstmt.setString( 1, "\"%" + name + "%\"" );
            pstmt.setString( 2, "\"%" + name + "%\"" );
            pstmt.setString( 3, "\"%" + name + "%\"" );

            ResultSet rs = pstmt.executeQuery();

            result = createListOfUserDTOfromResultSets( rs );

            // check if Result Set actually contains any rows/records received from database
//            if( rs.isBeforeFirst() ) { System.out.println( ">> We found data with UserDAO!" ); }
//            System.out.println( ">> found # users with \"" + name + "\": " + result.size() );

            rs.close();
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return result;
    }


    /*
     * This method creates a UserDTO with all their data (identification, posts & likes, followed-users).
     * The user passwords are hidden by default (they are never extracted from the database).
     */
    private UserDTO createUserDTOfromResultSets( ResultSet rsUserMainData,
                                                 ResultSet rsFollowedUsers,
                                                 boolean isNewUser )
    {
        if( rsUserMainData == null ) { return null; }

        int userID = 0;
        String username = null;
        String email = null;
        String firstName = null;
        String lastName = null;
        List<PostDTO> posts = new ArrayList<>();
        List<String> follows = new ArrayList<>();

        // (1) create userDTO without their followed-users
        try
        {
            while ( rsUserMainData.next() )
            {
                // (1.1) only get this data once (all database user IDs are larger than 0)
                if( userID <= 0 )
                {
                    userID = rsUserMainData.getInt( "id" );
                    username = rsUserMainData.getString( "username" );
                    email = rsUserMainData.getString( "email" );
                    firstName = rsUserMainData.getString( "first_name" );
                    lastName = rsUserMainData.getString( "last_name" );
                }

                // (1.2) create each Post from database (new users have no posts yet)
                if( !isNewUser )
                {
                    PostDTO userPost = new PostDTO( rsUserMainData.getInt( "postID" ),
                                                    rsUserMainData.getInt( "id" ),
                                                    rsUserMainData.getString( "message" ),
                                                    rsUserMainData.getTimestamp( "timestamp" ).toLocalDateTime(),
                                                    new ArrayList<>(),
                                                    new ArrayList<>() );
                    // TODO add Replies & Likes using ReplyDAO & LikeDAO, except for new users!

                    posts.add( userPost );
                }
            }

            rsUserMainData.close();
        }
        catch ( SQLException sqlex ) { sqlex.printStackTrace(); }

        // (2) create list of followed users (new users cannot have followed users yet)
        if( !isNewUser && rsFollowedUsers != null )
        {
            try
            {
                while( rsFollowedUsers.next() )
                {
                    follows.add( rsFollowedUsers.getString( 1 ) );
                }

                rsFollowedUsers.close();
            }
            catch ( SQLException sqlex ) { sqlex.printStackTrace(); }
        }

        // (3) create & return the full user (user password is hidden by default)
        return new UserDTO( userID, username, "[hidden password]", email, firstName, lastName, posts, follows );
    }


    private List<UserDTO> createListOfUserDTOfromResultSets( ResultSet rs )
    {
        List<UserDTO> result = new ArrayList<>();

        if( rs != null )
        {
            try
            {
                while( rs.next() )
                {
                    result.add( new UserDTO( rs.getInt( 1 ),
                                             rs.getString( "username" ),
                                             "[hidden password]",
                                             rs.getString( "email" ),
                                             rs.getString( "first_name" ),
                                             rs.getString( "last_name" ),
                                             new ArrayList<>(),
                                             new ArrayList<>() ) );
                    // TODO add Posts & Follows using PostDAO, except for new users!

                }

                rs.close();
            }
            catch( SQLException sqlex )
            {
                sqlex.printStackTrace();
            }
        }

        return result;
    }


    @Override
    public int followUser( int activeUserId, int followedId )
                           throws UserNotFound, IllegalArgumentException
    {
        // only pre-existing IDs are allowed by database
        final String query = "INSERT INTO followers (user_id, followed_user_id ) VALUES (?, ?)";

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement( query ) )
        {
            pstmt.setInt( 1, activeUserId );
            pstmt.setInt( 2, followedId );

            pstmt.executeUpdate();
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return followedId;
    }


    @Override
    public int unfollowUser( int activeUserId, int followedId )
    {
        int result = 0;

        final String query = "DELETE FROM followers WHERE user_id = ? AND followed_user_id = ?";

        try( Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement( query ) )
        {
            pstmt.setInt( 1, activeUserId );
            pstmt.setInt( 2, followedId );

            result = pstmt.executeUpdate();
        }
        catch( SQLException sqlex )
        {
            sqlex.printStackTrace();
        }

        return result;
    }
}
