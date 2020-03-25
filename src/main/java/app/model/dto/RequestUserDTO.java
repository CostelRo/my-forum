package app.model.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


public class RequestUserDTO
{
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


    public RequestUserDTO() {}


    public RequestUserDTO( @NotNull String username,
                           @NotNull String password,
                           @NotNull @Email String email,
                           @NotNull String firstName,
                           @NotNull String lastName )
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
