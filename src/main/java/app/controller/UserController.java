package app.controller;


import app.model.dto.RequestUserDTO;
import app.model.dto.ResponseUserDTO;
import app.repository.exceptions.ActiveUsersCannotFollowThemselves;
import app.repository.exceptions.ActiveUsersCannotUnfollowThemselves;
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

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.SQLIntegrityConstraintViolationException;
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
    ResponseEntity<Object> registerUser( @Valid @RequestBody @NotNull RequestUserDTO newUser )
                                        throws SQLIntegrityConstraintViolationException
    {
        ResponseUserDTO insertedUser = userService.registerNewUser( newUser );
        return new ResponseEntity<>( insertedUser, ( insertedUser != null ) ? HttpStatus.CREATED
                                                                            : HttpStatus.NOT_FOUND );
    }


    @RequestMapping( value = "/", method = RequestMethod.GET, produces = "application/json" )
    ResponseEntity<List<ResponseUserDTO>> getAllUsers()
    {
        return new ResponseEntity<>( userService.getAllUsers(), HttpStatus.OK );
    }


    @RequestMapping( value = "/{name}/", method = RequestMethod.GET, produces = "application/json" )
    public ResponseEntity<List<ResponseUserDTO>> getUserByName( @Valid @PathVariable @NotEmpty String name )
    {
        return new ResponseEntity<>( userService.searchByName( name ), HttpStatus.OK );
    }


    @RequestMapping( value = "/follow/{followedId}/", method = RequestMethod.POST, consumes = "application/json" )
    public ResponseEntity<Object> followUser( @Valid @RequestHeader @Positive int activeUserId,
                                              @Valid @PathVariable @Positive int followedId )
                                              throws SQLIntegrityConstraintViolationException
    {
        try
        {
            int followedUserId = userService.followUser( activeUserId, followedId );
            return new ResponseEntity<>( followedUserId, HttpStatus.CREATED );
        }
        catch( UserNotFound | ActiveUsersCannotFollowThemselves ex )
        {
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.FORBIDDEN );
        }
    }


    @RequestMapping( value = "/unfollow/{unfollowedId}/", method = RequestMethod.DELETE, consumes = "application/json" )
    public ResponseEntity<Object> unfollowUser( @Valid @RequestHeader @Positive int activeUserId,
                                                @Valid @PathVariable @Positive int unfollowedId )
    {
        try
        {
            int result = userService.unfollowUser( activeUserId, unfollowedId );
            return new ResponseEntity<>( result, HttpStatus.NO_CONTENT );
        }
        catch( ActiveUsersCannotUnfollowThemselves ex )
        {
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.FORBIDDEN );
        }
    }


    @RequestMapping( value = "/", method = RequestMethod.DELETE, consumes = "application/json" )
    public ResponseEntity<Object> unregister( @Valid @RequestHeader @Positive int activeUserId )
    {
        userService.unregisterUser( activeUserId );

        return new ResponseEntity<>( activeUserId, HttpStatus.NO_CONTENT );
    }
}
