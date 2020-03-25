package app.repository.api;


import app.model.dto.UserDTO;
import app.repository.exceptions.DuplicateEntryForUniqueDBRecord;
import app.repository.exceptions.UserNotFound;

import java.util.List;


public interface UserDAO
{
    UserDTO addUser( UserDTO newUser ) throws DuplicateEntryForUniqueDBRecord;

    List<UserDTO> getAllUsers();

    UserDTO getUserByUsername( String username, boolean isNewUser );

    List<UserDTO> getListOfUsersByPartialName( String name );

    int followUser( int activeUserId, int followedId ) throws UserNotFound;

    int unfollowUser( int activeUserId, int followedId );
}
