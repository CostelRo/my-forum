package app.model.dto;


import java.util.List;


public class ResponseUserDTO
{
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private List<Integer> posts;
    private List<String> follows;


    public ResponseUserDTO() {}


    public ResponseUserDTO( int id,
                            String username,
                            String firstName,
                            String lastName,
                            List<Integer> posts,
                            List<String> follows )
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
