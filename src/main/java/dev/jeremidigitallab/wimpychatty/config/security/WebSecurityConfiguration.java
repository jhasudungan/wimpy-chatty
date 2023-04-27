package dev.jeremidigitallab.wimpychatty.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@Bean
	public UserDetailsService createBeanUserDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder createBeanBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public DaoAuthenticationProvider createBeanDaoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(createBeanUserDetailsService());
		authenticationProvider.setPasswordEncoder(createBeanBCryptPasswordEncoder());
		
		return authenticationProvider;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception	{
		
		http.csrf()
			.disable()
			.authorizeHttpRequests()
				.antMatchers("/","/auth/login","/auth/registration/**")
					.permitAll()
				.anyRequest()
        			.authenticated()
        		.and()
        	.formLogin()
        		.loginPage("/auth/login")
        		.defaultSuccessUrl("/account?friend-page=1")
        		.permitAll()
        		.and()
        	.logout()
        		.logoutUrl("/auth/logout")
        		.logoutSuccessUrl("/auth/login")
        		.invalidateHttpSession(true)
        		.deleteCookies("JSESSIONID");
		
		http.headers().frameOptions().sameOrigin();

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2/**","/css/**","/js/**","/images/**");
	}
	
	
	
	
	
	
	
}
