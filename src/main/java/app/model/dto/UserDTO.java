package app.model.dto;


import java.util.List;


public class UserDTO
{
    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private List<PostDTO> posts;
    private List<String> follows;  // list of followed forum user names


    public UserDTO() {}


    public UserDTO( int id,
                    String username,
                    String password,
                    String email,
                    String firstName,
                    String lastName,
                    List<PostDTO> posts,
                    List<String> follows )
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
