package app.controller;


import app.model.dto.RequestPostDTO;
import app.model.dto.ResponsePostDTO;
import app.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping( "/posts/" )
public class PostController
{
    private PostService postService;


    @Autowired
    public PostController( PostService postService )
    {
        this.postService = postService;
    }


    @RequestMapping( value = "/", method = RequestMethod.POST, consumes = "application/json" )
    ResponseEntity<ResponsePostDTO> addPost( @RequestHeader @Positive int activeUserId,
                                             @RequestBody @NotNull RequestPostDTO newPost )
    {
        ResponsePostDTO insertedPost = postService.registerNewPost( activeUserId, newPost );

        return new ResponseEntity<>( insertedPost, ( insertedPost != null )
                                                     ? HttpStatus.CREATED
                                                     : HttpStatus.NOT_FOUND );
    }


    @RequestMapping( value = "/", method = RequestMethod.GET, produces = "application/json" )
    ResponseEntity<List<ResponsePostDTO>> getOwnPosts( @RequestHeader @Positive int activeUserId,
                                                       @RequestHeader( required = false )
                                                       @DateTimeFormat( iso = DateTimeFormat.ISO.DATE_TIME )
                                                       LocalDateTime timestamp )
    {
        List<ResponsePostDTO> ownPosts = postService.getOwnPosts( activeUserId, timestamp );

        return new ResponseEntity<>( ownPosts, ( ownPosts != null )
                                                 ? HttpStatus.OK
                                                 : HttpStatus.NOT_FOUND );
    }


    @RequestMapping( value ="/feed/", method = RequestMethod.GET, produces = "application/json" )
    ResponseEntity<List<ResponsePostDTO>> getFeed( @RequestHeader @Positive int activeUserId )
    {
        List<ResponsePostDTO> feed = postService.getFeed( activeUserId );

        return new ResponseEntity<>( feed, ( feed != null )
                                             ? HttpStatus.OK
                                             : HttpStatus.NOT_FOUND );
    }


    @RequestMapping( value = "/{postId}/", method = RequestMethod.DELETE, consumes = "application/json" )
    public ResponseEntity<Integer> deletePost( @RequestHeader @Positive int activeUserId,
                                               @PathVariable @Positive int postId )
    {
        int result = postService.deletePost( activeUserId, postId );

        return new ResponseEntity<>( result, HttpStatus.NO_CONTENT );
    }
}
