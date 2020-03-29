package app.service.converters;


import app.model.dto.ReplyDTO;
import app.model.dto.RequestReplyDTO;
import app.model.dto.ResponseReplyDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ReplyConverter
{
    public static ReplyDTO fromRequestDTOtoDTO( int activeUserId, RequestReplyDTO reply )
    {
        if( reply == null ) return null;

        return new ReplyDTO( 0,
                             activeUserId,
                             reply.getMessage(),
                             LocalDateTime.now(),
                             new ArrayList<>(),
                             new ArrayList<>(),
                             reply.getParentPostId() );
    }


    public static ResponseReplyDTO fromDTOtoResponseDTO( ReplyDTO reply )
    {
        if( reply == null ) return null;

        return new ResponseReplyDTO( reply.getId(),
                                     reply.getAuthorID(),
                                     reply.getMessage(),
                                     reply.getTimestamp(),
                                     ReplyConverter.fromListOfDTOtoListOfResponseDTO( reply.getReplies() ),
                                     reply.getLikes(),
//                                     LikeConverter.fromListOfDTOtoListOfResponseDTO( reply.getLikes() ),
                                     reply.getParentPostId() );
    }


//    public static List<Integer> getListOfIDsFromListOfPostDTO( List<PostDTO> posts )
//    {
//        List<Integer> result = new ArrayList<>();
//
//        if( posts != null )
//        {
//            for( PostDTO post : posts )
//            {
//                if( post != null ) result.add( post.getId() );
//            }
//        }
//
//        return result;
//    }


    public static List<ResponseReplyDTO> fromListOfDTOtoListOfResponseDTO( List<ReplyDTO> replies )
    {
        List<ResponseReplyDTO> result = new ArrayList<>();

        for( ReplyDTO reply : replies )
        {
            if( reply != null )
            {
                result.add( new ResponseReplyDTO( reply.getId(),
                                                  reply.getAuthorID(),
                                                  reply.getMessage(),
                                                  reply.getTimestamp(),
                                                  ReplyConverter.fromListOfDTOtoListOfResponseDTO( reply.getReplies() ),
                                                  reply.getLikes(),
//                                                  LikeConverter.fromListOfDTOtoListOfResponseDTO( reply.getLikes() ),
                                                  reply.getParentPostId() ) );
            }
        }

        return result;
    }
}
