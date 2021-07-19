package nl.novi.autogarage_roy_kersten.config;

import nl.novi.autogarage_roy_kersten.filter.JwtRequestFilter;
import nl.novi.autogarage_roy_kersten.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Secure the endpoints with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()

                 //Set authorization roles for endpoint /customers/
                .antMatchers(HttpMethod.GET, "/customers/**").hasAnyRole("ADMIN_CLERK","MECHANIC","BACKOFFICE","CASHIER")
                .antMatchers(HttpMethod.PUT, "/customers/**").hasAnyRole("ADMIN_CLERK")
                .antMatchers(HttpMethod.POST, "/customers/**").hasAnyRole("ADMIN_CLERK")
                .antMatchers(HttpMethod.DELETE, "/customers/**").hasAnyRole("ADMIN_CLERK")

                 //Set authorization roles for endpoint /cars/
                .antMatchers(HttpMethod.GET, "/cars/**").hasAnyRole("ADMIN_CLERK","MECHANIC","BACKOFFICE","CASHIER")
                .antMatchers(HttpMethod.PUT, "/cars/**").hasAnyRole("ADMIN_CLERK")
                .antMatchers(HttpMethod.POST, "/cars/**").hasAnyRole("ADMIN_CLERK")
                .antMatchers(HttpMethod.DELETE, "/cars/**").hasAnyRole("ADMIN_CLERK")

                 //Set authorization roles for endpoint /services/inspections/
                .antMatchers(HttpMethod.GET, "/services/inspections/**").hasAnyRole("ADMIN_CLERK","MECHANIC","BACKOFFICE","CASHIER")
                .antMatchers(HttpMethod.PUT, "/services/inspections/**").hasAnyRole("ADMIN_CLERK","MECHANIC")
                .antMatchers(HttpMethod.POST, "/services/inspections/**").hasAnyRole("ADMIN_CLERK", "MECHANIC")
                .antMatchers(HttpMethod.DELETE, "/services/inspections/**").hasAnyRole("ADMIN_CLERK","MECHANIC")

                 //Set authorization roles for endpoint /services/repairs/
                .antMatchers(HttpMethod.GET, "/services/repairs/**").hasAnyRole("ADMIN_CLERK","MECHANIC","BACKOFFICE","CASHIER")
                .antMatchers(HttpMethod.PUT, "/services/repairs/**").hasAnyRole("MECHANIC")
                .antMatchers(HttpMethod.POST, "/services/repairs/**").hasAnyRole("MECHANIC")
                .antMatchers(HttpMethod.DELETE, "/services/repairs/**").hasAnyRole("MECHANIC")

                 //Set authorization roles for endpoint /servicelines/
                .antMatchers(HttpMethod.GET, "/servicelines/**").hasAnyRole("ADMIN_CLERK","MECHANIC","BACKOFFICE","CASHIER")
                .antMatchers(HttpMethod.PUT, "/servicelines/**").hasAnyRole("MECHANIC")
                .antMatchers(HttpMethod.POST, "/servicelines/**").hasAnyRole("MECHANIC")
                .antMatchers(HttpMethod.DELETE, "/servicelines/**").hasAnyRole("MECHANIC")

                 //Set authorization roles for endpoint /items/parts/
                .antMatchers(HttpMethod.GET, "/items/parts/**").hasAnyRole("ADMIN_CLERK","MECHANIC","BACKOFFICE","CASHIER")
                .antMatchers(HttpMethod.PUT, "/items/parts/**").hasAnyRole("BACKOFFICE")
                .antMatchers(HttpMethod.POST, "/items/parts/**").hasAnyRole("BACKOFFICE")
                .antMatchers(HttpMethod.DELETE, "/items/parts/**").hasAnyRole("BACKOFFICE")

                 //Set authorization roles for endpoint /items/activities/
                .antMatchers(HttpMethod.GET, "/items/activities/**").hasAnyRole("ADMIN_CLERK","MECHANIC","BACKOFFICE","CASHIER")
                .antMatchers(HttpMethod.PUT, "/items/activities/**").hasAnyRole("BACKOFFICE")
                .antMatchers(HttpMethod.POST, "/items/activities/**").hasAnyRole("BACKOFFICE")
                .antMatchers(HttpMethod.DELETE, "/items/activities/**").hasAnyRole("BACKOFFICE")

                 //Set authorization roles for endpoint /invoices/inspections/
                .antMatchers(HttpMethod.GET, "/invoices/inspections/**").hasAnyRole("ADMIN_CLERK","MECHANIC","BACKOFFICE","CASHIER")
                .antMatchers(HttpMethod.PUT, "/invoices/inspections/**").hasAnyRole("MECHANIC", "CASHIER")
                .antMatchers(HttpMethod.POST, "/invoices/inspections/**").hasAnyRole("MECHANIC","CASHIER")
                .antMatchers(HttpMethod.DELETE, "/invoices/inspections/**").hasAnyRole("MECHANIC", "CASHIER")

                 //Set authorization roles for endpoint /invoices/repairs/
                .antMatchers(HttpMethod.GET, "/invoices/repairs/**").hasAnyRole("ADMIN_CLERK","MECHANIC","BACKOFFICE","CASHIER")
                .antMatchers(HttpMethod.PUT, "/invoices/repairs/**").hasAnyRole("CASHIER")
                .antMatchers(HttpMethod.POST, "/invoices/repairs/**").hasAnyRole("CASHIER")
                .antMatchers(HttpMethod.DELETE, "/invoices/repairs/**").hasAnyRole("CASHIER")

                 //Set authorization roles for endpoint /users/
                .antMatchers("/users/**").hasRole("ADMIN")

                 //Endpoints related to JWT token
                .antMatchers("/authenticated").authenticated()
                .antMatchers("/authenticate").permitAll()

                .anyRequest().permitAll()
                .and()
                .cors().and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


    }


}
