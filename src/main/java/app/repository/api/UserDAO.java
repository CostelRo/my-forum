package app.repository.api;


import app.model.dto.UserDTO;
import app.repository.exceptions.UserNotFound;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;


public interface UserDAO
{
    UserDTO addUser( UserDTO newUser ) throws SQLIntegrityConstraintViolationException; // DuplicateEntryForUniqueDBRecord;

    List<UserDTO> getAllUsers();

    UserDTO getUserByUsername( String username, boolean isNewUser );

    List<UserDTO> getListOfUsersByPartialName( String name );

    int followUser( int activeUserId, int followedId ) throws UserNotFound, SQLIntegrityConstraintViolationException;

    int unfollowUser( int activeUserId, int followedId );

    int unregisterUser( int activeUserId );
}
