package app.service.converters;


import app.model.dto.RequestUserDTO;
import app.model.dto.ResponseUserDTO;
import app.model.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;


public class UserConverter
{
    public static UserDTO fromRequestDTOtoDTO( RequestUserDTO user )
    {
        if( user == null ) return null;

        return new UserDTO( 0,
                            user.getUsername(),
                            user.getPassword(),
                            user.getEmail(),
                            user.getFirstName(),
                            user.getLastName(),
                            new ArrayList<>(),
                            new ArrayList<>() );
    }


    public static ResponseUserDTO fromDTOtoResponseDTO( UserDTO user )
    {
        if( user == null ) return null;

        return new ResponseUserDTO( user.getId(),
                                    user.getUsername(),
                                    user.getFirstName(),
                                    user.getLastName(),
                                    PostConverter.getListOfIDsFromListOfPostDTO( user.getPosts() ),
                                    user.getFollows() );
    }


    public static List<String> FromListOfUserDTOtoListOfFollowedUsernames( List<UserDTO> users )
    {
        List<String> result = new ArrayList<>();

        if( users != null )
        {
            for( UserDTO user : users )
            {
                if( user != null ) { result.add( user.getUsername() ); }
            }
        }

        return result;
    }


    public static List<ResponseUserDTO> fromListOfUserDTOtoListOfResponseUserDTO( List<UserDTO> usersList )
    {
        List<ResponseUserDTO> result = new ArrayList<>();

        if( usersList != null )
        {
            for( UserDTO user : usersList )
            {
                if( user != null ) { result.add( fromDTOtoResponseDTO( user ) ); }
            }
        }

        return result;
    }
}
