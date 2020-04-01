package app.repository.api;


import app.model.dto.ReplyDTO;

import java.sql.SQLIntegrityConstraintViolationException;


public interface ReplyDAO
{
    ReplyDTO addReply( ReplyDTO newReply ) throws SQLIntegrityConstraintViolationException;
}
