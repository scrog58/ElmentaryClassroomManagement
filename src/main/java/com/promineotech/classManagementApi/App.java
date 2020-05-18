package com.promineotech.classManagementApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("com.promineotech.classManagementApi")
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {		
    	SpringApplication.run(App.class, args);
    }
    


}
