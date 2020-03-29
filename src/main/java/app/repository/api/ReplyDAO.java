package app.repository.api;


import app.model.dto.ReplyDTO;


public interface ReplyDAO
{
    ReplyDTO addReply( ReplyDTO newReply );
}
