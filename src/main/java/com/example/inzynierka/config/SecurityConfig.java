package com.example.inzynierka.config;

import com.example.inzynierka.config.jwt.JwtFilter;
import com.example.inzynierka.services.implementations.AccountServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AccountServiceImpl accountService;
    private final JwtFilter jwtFilter;

    public SecurityConfig(AccountServiceImpl accountService, JwtFilter jwtFilter) {
        this.accountService = accountService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers("/registration/*").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/sendEmailToResetPassword/{email}").permitAll()
                .antMatchers("/resetPassword/{token}").permitAll()
                .antMatchers("/validateResetPasswordToken/{token}").permitAll()

                .antMatchers("/recipe").permitAll()
                .antMatchers("/recipe/all").permitAll()
                .antMatchers("/recipe/{recipeId}").permitAll()
                .antMatchers("/{recipeId}/isRecipeInFavourited").hasAuthority("USER")
                .antMatchers("/recipe/{recipeId}/addToFavourite").hasAuthority("USER")
                .antMatchers("/recipe/{recipeId}/deleteFromFavourite").hasAuthority("USER")
                .antMatchers("/recipe/addRecipe").hasAuthority("USER")
                .antMatchers("/recipe/{recipeId}/delete").hasAuthority("USER")

                .antMatchers("/downloadFile/*").permitAll()
                .antMatchers("/account/*").hasAuthority("USER")
                .antMatchers("/pantry/individual/*").hasAuthority("USER")
                .antMatchers("/groceryList/*").hasAuthority("USER")
                .antMatchers("/accountDetails/*").hasAuthority("USER")
                .antMatchers("/dietType/*").permitAll()
                .antMatchers("/familyPantry/**").hasAuthority("USER")
                .antMatchers("/ingredient").hasAuthority("USER")
                .anyRequest().authenticated();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
