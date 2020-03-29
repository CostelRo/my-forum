package app.service.converters;


import app.model.dto.PostDTO;
import app.model.dto.ReplyDTO;
import app.model.dto.RequestPostDTO;
import app.model.dto.ResponsePostDTO;
import app.model.dto.ResponseReplyDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class PostConverter
{
    public static PostDTO fromRequestDTOtoDTO( int activeUserId, RequestPostDTO post )
    {
        if( post == null ) return null;

        return new PostDTO( 0,
                            activeUserId,
                            post.getMessage(),
                            LocalDateTime.now(),
                            new ArrayList<>(),
                            new ArrayList<>() );
    }


    public static ResponsePostDTO fromDTOtoResponseDTO( PostDTO post )
    {
        if( post == null ) return null;

        return new ResponsePostDTO( post.getId(),
                                    post.getAuthorID(),
                                    post.getMessage(),
                                    post.getTimestamp(),
                                    ReplyConverter.fromListOfDTOtoListOfResponseDTO( post.getReplies() ),
                                    post.getLikes() );
//                                    LikeConverter.fromListOfDTOtoListOfResponseDTO( post.getLikes() ) );
    }


    public static List<Integer> getListOfIDsFromListOfPostDTO( List<PostDTO> posts )
    {
        List<Integer> result = new ArrayList<>();

        if( posts != null )
        {
            for( PostDTO post : posts )
            {
                if( post != null ) result.add( post.getId() );
            }
        }

        return result;
    }


    public static List<ResponsePostDTO> fromListOfDTOtoListOfResponseDTO( List<PostDTO> posts )
    {
        List<ResponsePostDTO> result = new ArrayList<>();

        for( PostDTO post : posts )
        {
            if( post != null )
            {
                if( post instanceof ReplyDTO )
                {
                    ReplyDTO reply = (ReplyDTO) post;

                    result.add( new ResponseReplyDTO( reply.getID(),
                                                      reply.getAuthorID(),
                                                      reply.getMessage(),
                                                      reply.getTimestamp(),
                                                      ReplyConverter.fromListOfDTOtoListOfResponseDTO( reply.getReplies() ),
                                                      post.getLikes(),
//                                                      LikeConverter.fromListOfDTOtoListOfResponseDTO ( reply.getLikes() ),
                                                      reply.getParentPostId() ) );
                    // TODO add replies & likes!
                }
                else
                {
                    result.add( new ResponsePostDTO( post.getId(),
                                                     post.getAuthorID(),
                                                     post.getMessage(),
                                                     post.getTimestamp(),
                                                     ReplyConverter.fromListOfDTOtoListOfResponseDTO( post.getReplies() ),
                                                     post.getLikes() ) );
//                                                     LikeConverter.fromListOfDTOtoListOfResponseDTO( post.getLikes() ) ) );
                }
            }
        }

        return result;
    }
}
