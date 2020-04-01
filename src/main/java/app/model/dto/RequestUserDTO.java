package app.model.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class RequestUserDTO
{
    @NotEmpty( message = "The Username must be provided." )
    @Size( min = 8, max = 20, message = "Username must be between {min} and {max} characters." )
    private String username;

    @NotEmpty( message = "The Password must be provided." )
    @Size( min = 8, max = 20, message = "Password must be between {min} and {max} characters." )
    private String password;

    @NotEmpty( message = "The Email must be provided." )
    @Email( message = "A valid Email format must be used." )
    private String email;

    @NotEmpty( message = "The First Name must be provided." )
    private String firstName;

    @NotEmpty( message = "The Last Name must be provided." )
    private String lastName;


    public RequestUserDTO() {}


    public RequestUserDTO( String username,
                           String password,
                           String email,
                           String firstName,
                           String lastName )
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
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


    @Override
    public boolean equals( Object obj )
    {
        if( obj == null || !(obj instanceof RequestUserDTO) ) return false;

        RequestUserDTO other = (RequestUserDTO) obj;
        return this.username.equals( other.username ) || this.email.equals( other.email );
    }


    @Override
    public String toString()
    {
        return username + " (" + firstName + " " + lastName.toUpperCase() + ", " + email + ")";
    }
}
