package app.service;


import app.model.dto.ReplyDTO;
import app.model.dto.RequestReplyDTO;
import app.model.dto.ResponseReplyDTO;
import app.repository.api.ReplyDAO;
import app.service.converters.ReplyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;


@Service
public class ReplyService
{
    private ReplyDAO replyDAO;


    @Autowired
    public ReplyService( ReplyDAO replyDAO )
    {
        this.replyDAO = replyDAO;
    }


    public ReplyDAO getReplyDAO()
    {
        return replyDAO;
    }


    public ResponseReplyDTO registerNewReply( int activeUserId, RequestReplyDTO newReply )
                                              throws SQLIntegrityConstraintViolationException
    {
        if( newReply == null ) throw new IllegalArgumentException( "Missing Reply object." );
        if( activeUserId <= 0 ) throw new IllegalArgumentException( "Incorrect active user ID." );

        ReplyDTO result = replyDAO.addReply( ReplyConverter.fromRequestDTOtoDTO( activeUserId, newReply ) );

        if( result != null ) { return ReplyConverter.fromDTOtoResponseDTO( result ); }
        else { return null; }
    }
}
