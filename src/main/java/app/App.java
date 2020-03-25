package app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Home page contents.
 */
@RestController
@EnableAutoConfiguration
@ComponentScan
public class App
{
    @RequestMapping( "/" )
    String home()
    {
        return "My REST Forum Engine homepage.";
    }


    public static void main(String[] args)
    {
        SpringApplication.run( App.class, args );
    }
}

