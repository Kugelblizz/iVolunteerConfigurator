package at.jku.cis.iVolunteer.marketplace.security.configuration;
//
//import java.util.Arrays;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import static org.springframework.http.HttpMethod.*;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import at.jku.cis.iVolunteer.marketplace.security.entrypoint.UnauthorizedAuthenticationEntryPoint;
//import at.jku.cis.iVolunteer.marketplace.security.filter.JWTAuthorizationFilter;

//@EnableGlobalMethodSecurity(securedEnabled = true)
//
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
//	@Autowired
//	private UnauthorizedAuthenticationEntryPoint authenticationEntryPoint;
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// @formatter:off
//
//		http.cors().and().csrf().disable();
//		http.authorizeRequests()
//				.antMatchers("/v2/api-docs").permitAll() 
//				.antMatchers("/swagger-resources").permitAll() 
//				.antMatchers("/swagger-ui.html").permitAll() 
//				.antMatchers("/api/**").permitAll()
//				.antMatchers("/init/**").permitAll()
//				.antMatchers("/user/register").permitAll()
////				.antMatchers("/user/subscribe").permitAll()
////				.antMatchers("/user/unsubscribe").permitAll()
//				.antMatchers("/rule/engine/**").permitAll()
//				//TODO DEBUG
////				.antMatchers("/task/**").permitAll()
////				.antMatchers("/tasktemplate/**").permitAll()
////				.antMatchers("/taskCertificate/**").permitAll()
////				.antMatchers("/meta/core/class/instance/**").permitAll()
////				.antMatchers("/meta/core/task-instance/**").permitAll()
////				.antMatchers("/aggregate/**").permitAll()
////				END DEBUG
//				.anyRequest().authenticated();
//
//		http.addFilter(new JWTAuthorizationFilter(authenticationManager())).sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
//		//@formatter:on
//	}
//
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/auth/refreshToken");
//	}
//
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.applyPermitDefaultValues();
//		corsConfiguration
//				.setAllowedMethods(Arrays.asList(HEAD.name(), GET.name(), DELETE.name(), POST.name(), PUT.name()));
//		source.registerCorsConfiguration("/**", corsConfiguration);
//		return source;
//	}
//}