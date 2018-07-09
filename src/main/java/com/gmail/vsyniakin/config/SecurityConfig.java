package com.gmail.vsyniakin.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    private BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        		.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/recipes").permitAll()
                .antMatchers("/acc/moderation/**").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers("/acc/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/acc/**").permitAll()
                .antMatchers("/add_recipe").hasAnyRole("ADMIN", "USER", "MODERATOR")
                .antMatchers("/edit_recipe/**").hasAnyRole("ADMIN", "USER", "MODERATOR")
                .antMatchers("/send_message").authenticated()
                .antMatchers("/add_value_rating").authenticated()
                .antMatchers("/recipe").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/exc/access_denied")
                .and()
                .formLogin()
                .loginPage("/form_login")
                .loginProcessingUrl("/login_submit")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .defaultSuccessUrl("/success", true)
                .failureForwardUrl("/access_fail")
                .and()
                .logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
    }
}
