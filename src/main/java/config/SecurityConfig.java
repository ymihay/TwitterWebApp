package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import javax.sql.DataSource;


/**
 * Created by Admin on 13.07.2014.
 */
@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String userByNameSql = "select login as username, password, 1 as enabled\n" +
            "  from twt_user\n" +
            " where sys_delstate = 0\n" +
            "   and login = ?\n";

    private static final String roleByNameSql = "select ? as username, 'ROLE_USER' as authority from dual";

    @Autowired
    DataSource dataSource;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(userByNameSql)
                .authoritiesByUsernameQuery(roleByNameSql);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                        //.antMatchers("/resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/rest/login").failureUrl("/rest/login?error")
                .defaultSuccessUrl("/rest/users/")
                .usernameParameter("username").passwordParameter("password")
                .permitAll()
                .and()
                .csrf()
                .and()
                .logout()
                .permitAll()
                .and()
                .rememberMe();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }
}

