package ca.sheridancollege;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginAccessDeniedHandler accessDeniedHandler;

	@Autowired
	private MyUserDetails userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private SuccessLoginHandler customSuccessHandler;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/professor/**").hasRole("ADMIN")
				.antMatchers("/client/**").hasRole("CLIENT")
				.antMatchers("/student/**").hasRole("STUDENT") 																		
				.antMatchers("/", "/js/**", "/css/**", "/images/**", "/resources/**", "/**").permitAll().anyRequest()
				.authenticated()
				.and()
				.formLogin().loginPage("/login").permitAll().successHandler(customSuccessHandler)
				.and()
				.logout().logoutUrl("/logout").invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
				.permitAll()
				.and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}

}
