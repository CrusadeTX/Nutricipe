package com.project.nutricipe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@EnableWebSecurity(debug = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login?error";
	private static final String LOGIN_URL = "/login";
	private static final String LOGOUT_SUCCESS_URL = "/login";
	private PasswordEncoder passwordEncoder;
	
	private ApplicationUserDetailsService userDetailService; 
	
	public WebSecurityConfig(ApplicationUserDetailsService userDetailService) {

		this.userDetailService = userDetailService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		super.configure(auth); 
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.authorizeRequests().antMatchers("/**").permitAll()
		.requestMatchers(EndpointRequest.toAnyEndpoint())
		.permitAll()
		.and().csrf().disable();
		http.headers().frameOptions().disable();*/
		
		 http
	        .authorizeRequests()
	          .antMatchers("/public/**", "/resources/**","/resources/public/**","/resources/templates/**", "/register", "/h2-console/**", "/")
	            .permitAll().antMatchers("/users.html").hasRole("ADMIN").anyRequest().authenticated().and()
	       .formLogin()
	         .loginPage("/login").defaultSuccessUrl("/home", true)
	         .permitAll()
	         .and()
	      .logout() 
	        .permitAll().and().csrf().disable();
		 http.headers().frameOptions().disable();
		 
		}
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/api/auth/**","/v2/api-docs", 
	            "/configuration/ui", 
	            "/swagger-resources", 
	            "/configuration/security",
	            "/swagger-ui.html", 
	            "/webjars/**",
	            "/favicon.ico",
	            "/**/*.png",
	            "/**/*.gif",
	            "/**/*.svg",
	            "/**/*.jpg",
	            "/**/*.css",
	            "/**/*.js",
	            "/console/**");
	}
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
