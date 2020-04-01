package app.controller;


import app.model.dto.RequestReplyDTO;
import app.model.dto.ResponseReplyDTO;
import app.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.SQLIntegrityConstraintViolationException;


@RestController
@RequestMapping( "/replies/" )
public class ReplyController
{
    private ReplyService replyService;


    @Autowired
    public ReplyController( ReplyService replyService )
    {
        this.replyService = replyService;
    }


    @RequestMapping( value = "/", method = RequestMethod.POST, consumes = "application/json" )
    ResponseEntity<ResponseReplyDTO> addReply( @Valid @RequestHeader @Positive int activeUserId,
                                               @Valid @RequestBody @NotNull RequestReplyDTO newReply )
                                               throws SQLIntegrityConstraintViolationException
    {
        ResponseReplyDTO insertedReply = replyService.registerNewReply( activeUserId, newReply );

        return new ResponseEntity<>( insertedReply, ( insertedReply != null ) ? HttpStatus.CREATED
                                                                              : HttpStatus.NOT_FOUND );
    }
}
