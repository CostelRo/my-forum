package app.service.converters;


import app.model.dto.PostDTO;
import app.model.dto.RequestPostDTO;
import app.model.dto.ResponsePostDTO;

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
                                    LikeConverter.fromListOfDTOtoListOfResponseDTO( post.getLikes() ) );
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


    public static List<ResponsePostDTO> fromListOfDTOtoListOfResponseDTO( List<PostDTO> ownPosts )
    {
        List<ResponsePostDTO> result = new ArrayList<>();

        for( PostDTO post : ownPosts )
        {
            if( post != null )
            {
                result.add( new ResponsePostDTO( post.getId(),
                                                 post.getAuthorID(),
                                                 post.getMessage(),
                                                 post.getTimestamp(),
                                                 new ArrayList<>(  ),
                                                 new ArrayList<>(  ) ) );
//                                                 post.ReplyConverter.fromListOfDTOtoListOfResponseDTO( post.getReplies() ),
//                                                 post.LikeConverter.fromListOfDTOtoListOfResponseDTO( post.getLikes() ) ) );
                // TODO insert list of ReplyDTO and LikeDTO, from their DAOImpl
            }
        }

        return result;
    }
}
