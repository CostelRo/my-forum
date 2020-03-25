package app.controller;


import app.model.dto.RequestUserDTO;
import app.model.dto.ResponseUserDTO;
import app.repository.exceptions.ActiveUsersCannotFollowThemselves;
import app.repository.exceptions.ActiveUsersCannotUnfollowThemselves;
import app.repository.exceptions.DuplicateEntryForUniqueDBRecord;
import app.repository.exceptions.UserNotFound;
import app.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@RequestMapping( "/users/" )
public class UserController
{
    private UserService userService;


    @Autowired
    public UserController( UserService userService )
    {
        this.userService = userService;
    }


    @RequestMapping( value = "/", method = RequestMethod.POST, consumes = "application/json" )
    ResponseEntity<ResponseUserDTO> registerUser( @RequestBody @NotNull RequestUserDTO newUser )
                                                  throws DuplicateEntryForUniqueDBRecord
    {
        // TODO use security EVERYWHERE: now, the active-user ID is sent through the request header

        ResponseUserDTO insertedUser = userService.registerNewUser( newUser );

        return new ResponseEntity<>( insertedUser, ( insertedUser != null )
                                                     ? HttpStatus.CREATED
                                                     : HttpStatus.NOT_FOUND );
    }


    @RequestMapping( value = "/", method = RequestMethod.GET, produces = "application/json" )
    ResponseEntity<List<ResponseUserDTO>> getAllUsers()
    {
        return new ResponseEntity<>( userService.getAllUsers(), HttpStatus.OK );
    }


    @RequestMapping( value = "/{name}/", method = RequestMethod.GET, produces = "application/json" )
    public ResponseEntity<List<ResponseUserDTO>> getUserByName( @PathVariable @NotNull String name )
    {
        return new ResponseEntity<>( userService.searchByName( name ), HttpStatus.OK );
    }


    @RequestMapping( value = "/follow/{followedId}/", method = RequestMethod.PUT, consumes = "application/json" )
    public ResponseEntity<Integer> followUser( @RequestHeader @Positive int activeUserId,
                                               @PathVariable @Positive int followedId )
                                               throws UserNotFound, ActiveUsersCannotFollowThemselves
    {
        int followedUserId = userService.followUser( activeUserId, followedId );

        return new ResponseEntity<>( followedUserId, HttpStatus.CREATED );
    }


    @RequestMapping( value = "/unfollow/{unfollowedId}/", method = RequestMethod.DELETE, consumes = "application/json" )
    public ResponseEntity<Integer> unfollowUser( @RequestHeader @Positive int activeUserId,
                                                 @PathVariable @Positive int unfollowedId )
                                                 throws ActiveUsersCannotUnfollowThemselves
    {
        int result = userService.unfollowUser( activeUserId, unfollowedId );

        return new ResponseEntity<>( result, HttpStatus.NO_CONTENT );
    }
}

