package app.model.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


public class UserDTO
{
    @PositiveOrZero
    private int id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private List<PostDTO> posts;

    @NotNull
    private List<String> follows;  // list of followed forum user names


    public UserDTO() {}


    public UserDTO( @PositiveOrZero int id,
                    @NotNull String username,
                    @NotNull String password,
                    @NotNull @Email String email,
                    @NotNull String firstName,
                    @NotNull String lastName,
                    @NotNull List<PostDTO> posts,
                    @NotNull List<String> follows )
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
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


    public String getPassword()
    {
        return password;
    }


    public String getEmail()
    {
        return email;
    }


    public String getFirstName()
    {
        return firstName;
    }


    public String getLastName()
    {
        return lastName;
    }


    public List<PostDTO> getPosts()
    {
        return posts;
    }


    public List<String> getFollows()
    {
        return follows;
    }


    @Override
    public String toString()
    {
        return username + " (id: " + this.getId() + "; " + firstName + " " + lastName.toUpperCase() + "; " + email + ")";
    }
}
