package app.model.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;


public class ResponseUserDTO
{
    @Positive
    private int id;

    @NotNull
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private List<Integer> posts;

    @NotNull
    private List<String> follows;


    public ResponseUserDTO() {}


    public ResponseUserDTO( @Positive int id,
                            @NotNull String username,
                            @NotNull String firstName,
                            @NotNull String lastName,
                            @NotNull List<Integer> posts,
                            @NotNull List<String> follows )
    {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
        this.follows = follows;
    }


    public int getId()
    {
        return id;
    }


    public String getUsername()
    {
        return username;
    }


    public String getFirstName()
    {
        return firstName;
    }


    public String getLastName()
    {
        return lastName;
    }


    public List<Integer> getPosts()
    {
        return posts;
    }


    public List<String> getFollows()
    {
        return follows;
    }

    public String toString()
    {
        return username + " (" + firstName + " " + lastName.toUpperCase() + ")";
    }
}
