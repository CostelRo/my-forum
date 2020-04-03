package app.controller;


import app.model.dto.ResponseLikeDTO;
import app.service.LikeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.sql.SQLIntegrityConstraintViolationException;


@RestController
@RequestMapping( "/likes/" )
public class LikeController
{
    private LikeService likeService;


    @Autowired
    public LikeController( LikeService likeService )
    {
        this.likeService = likeService;
    }


    @RequestMapping( value = "/{postId}/", method = RequestMethod.POST, consumes = "application/json" )
    ResponseEntity<ResponseLikeDTO> likePost( @Valid @RequestHeader @Positive int activeUserId,
                                              @Valid @PathVariable @Positive int postId )
                                              throws SQLIntegrityConstraintViolationException
    {
        ResponseLikeDTO insertedLike = likeService.registerNewLike( activeUserId, postId );

        return new ResponseEntity<>( insertedLike, ( insertedLike != null ) ? HttpStatus.CREATED
                                                                            : HttpStatus.NOT_FOUND );
    }


    @RequestMapping( value = "/{postId}/", method = RequestMethod.DELETE, consumes = "application/json" )
    public ResponseEntity<Integer> deleteLike( @Valid @RequestHeader @Positive int activeUserId,
                                               @Valid @PathVariable @Positive int postId )
    {
        int result = likeService.deleteLike( activeUserId, postId );

        return new ResponseEntity<>( result, HttpStatus.NO_CONTENT );
    }
}
