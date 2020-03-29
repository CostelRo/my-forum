package app.service.converters;


import app.model.dto.LikeDTO;
import app.model.dto.ResponseLikeDTO;

import java.util.ArrayList;
import java.util.List;


public class LikeConverter
{
    public static LikeDTO fromRequestDTOtoDTO( int activeUserId, int postId )
    {
        return new LikeDTO( activeUserId, postId );
    }


    public static ResponseLikeDTO fromDTOtoResponseDTO( LikeDTO like )
    {
        return new ResponseLikeDTO( like.getUserId(), like.getPostId() );
    }


    public static List<ResponseLikeDTO> fromListOfDTOtoListOfResponseDTO( List<LikeDTO> likes )
    {
        List<ResponseLikeDTO> result = new ArrayList<>();

        for( LikeDTO like : likes )
        {
            if( like != null )
            {
                result.add( new ResponseLikeDTO( like.getUserId(), like.getPostId() ) );
            }
        }

        return result;
    }
}
