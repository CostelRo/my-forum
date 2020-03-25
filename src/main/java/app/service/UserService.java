package app.service;


import app.model.dto.RequestUserDTO;
import app.model.dto.ResponseUserDTO;
import app.model.dto.UserDTO;
import app.repository.api.UserDAO;
import app.repository.exceptions.ActiveUsersCannotFollowThemselves;
import app.repository.exceptions.ActiveUsersCannotUnfollowThemselves;
import app.repository.exceptions.DuplicateEntryForUniqueDBRecord;
import app.repository.exceptions.UserNotFound;
import app.service.converters.UserConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService
{
    private UserDAO userDAO;


    @Autowired
    public UserService( UserDAO userDAO )
    {
        this.userDAO = userDAO;
    }


    public UserDAO getUserDAO()
    {
        return userDAO;
    }


    public ResponseUserDTO registerNewUser( RequestUserDTO newUser ) throws DuplicateEntryForUniqueDBRecord
    {
        if( newUser == null ) throw new IllegalArgumentException( "Missing user object!" );
        if( isMissingMandatoryUserData( newUser ) ) throw new IllegalArgumentException( "User mandatory data missing." );

        UserDTO addedUser = userDAO.addUser( UserConverter.fromRequestDTOtoDTO( newUser ) );

        return UserConverter.fromDTOtoResponseDTO( addedUser );
    }


    private boolean isMissingMandatoryUserData( RequestUserDTO newUser )
    {
        return newUser.getUsername() == null || newUser.getUsername().length() == 0
                || newUser.getPassword() == null || newUser.getPassword().length() == 0
                || newUser.getEmail() == null || newUser.getEmail().length() == 0
                || newUser.getFirstName() == null || newUser.getFirstName().length() == 0
                || newUser.getLastName() == null || newUser.getLastName().length() == 0;
    }


    public List<ResponseUserDTO> getAllUsers()
    {
        List<UserDTO> usersList = userDAO.getAllUsers();

        return UserConverter.fromListOfUserDTOtoListOfResponseUserDTO( usersList );
    }


    public List<ResponseUserDTO> searchByName( String name )
    {
        if( name == null || name.length() == 0 ) return new ArrayList<>();

        return UserConverter.fromListOfUserDTOtoListOfResponseUserDTO( userDAO.getListOfUsersByPartialName( name ) );
    }


    public int followUser( int activeUserId, int followedId )
                           throws UserNotFound, ActiveUsersCannotFollowThemselves
    {
        if( activeUserId <= 0 || followedId <= 0 )
        {
            throw new IllegalArgumentException( "Both user IDs must be valid." );
        }
        if( activeUserId == followedId )
        {
            throw new ActiveUsersCannotFollowThemselves( "Users cannot follow themselves." );
        }

        return userDAO.followUser( activeUserId, followedId );
    }


    public int unfollowUser( int activeUserId, int followedId ) throws ActiveUsersCannotUnfollowThemselves
    {
        if( activeUserId <= 0 || followedId <= 0 )
        {
            throw new IllegalArgumentException( "Both user IDs must be valid." );
        }
        if( activeUserId == followedId )
        {
            throw new ActiveUsersCannotUnfollowThemselves( "Users cannot un-follow themselves." );
        }

        return userDAO.unfollowUser( activeUserId, followedId ) == 1
               ? followedId
               : 0;
    }
}
