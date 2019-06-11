package libraryweb.config;

import java.util.HashSet;
import java.util.Set;
import operation.Storage;
import operation.table.User;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LogManager.getRootLogger();

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
        Set<User> users = new HashSet<>();
        Storage<User> storeUser = new Storage(users, User.class);
        users = (Set<User>) storeUser.read();
        users.stream().forEach(user -> {
            try {
                auth.inMemoryAuthentication()
                        .withUser(user.getUserLogin().getLogin())
                        .password(user.getUserLogin().getPassword())
                        .roles("USER");
            } catch (Exception e) {
                logger.error(e);
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                .and()
                .authorizeRequests().antMatchers("/user**", "/menuUserPath").hasAnyRole("USER", "ADMIN")
                .and()
                .authorizeRequests().antMatchers("/addBookPath", "/addUserPath", "/library").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        http.csrf().disable();
    }

}
