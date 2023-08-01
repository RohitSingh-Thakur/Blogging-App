package com.singh.rdst.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

									// 1st Check //
		// a. Get JWT token from Request (token will be in header so we can get it from request)//
		
		String requestTokenHeader = request.getHeader("Authorization"); // its a key by which we can get Token
		// Token will be start from Bearer 2587465657sadfags imp (number is bearer and followed by token)
		System.out.println("Token = " + requestTokenHeader);
		
		
		// Now we will be fetch username and Token with the help of JWTHelper methods
		String username = null;
		String token = null;
		
		// Now apply some checks 
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			// Now fetch token only not Bearer 
			// Bearer 2587465657sadfags
			// 01234567
			// here token starts with 7th index
			
			token = requestTokenHeader.substring(7); // Token Without Bearer
			
			// Getting the user name by token using jwtTokenHelper.getUsernameFromToken method which might be 
			// throw multiple exceptions we need to handle it by try and catch block
			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token Has Expired");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid JWT");
			}
			
			
		}else {
			System.out.println("JWT Token Does Not Starts With Bearer...");
		}
		
		
											// 2nd Check //
		// b. Validate Token -> is token expired or not 
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) { 
		// SecurityContextHolder.getContext().getAuthentication()==null means authentication is null
			// now we need to validate JWT Authentication and set into Authentication because 
			// SecurityContextHolder.getContext().getAuthentication()==null spring security is not authentication to anyone
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); // validateToken() requires token and userDetails object
			if(this.jwtTokenHelper.validateToken(token, userDetails)) { // if it returns true then token is valid else 
				// we need to set authenticate By using SecurityContextHolder.getContext().setAuthentication(null); // It requires Authentication Object
				// First We will Create Authentication Object
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				// userDetails.getAuthorities() method returns list of granted authorities 
				
				// Now Before passing usernamePasswordAuthenticationToken object to SecurityContextHolder.getContext().setAuthentication(null);
				// we need to set the details in usernamePasswordAuthenticationToken
				// usernamePasswordAuthenticationToken has a method called usernamePasswordAuthenticationToken.setDetails(Here we need to create details);
				// for creating details we have Builder method buildDetails which present in 
				//WebAuthenticationDetailsSource().buildDetails(here we pass out request object)
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); // It requires Authentication Object
			}else {
				System.out.println("Invalid Token...");
			}
		}else {
			System.out.println("UserName is Null OR Security Context is Not Null...");
		}
		
		filterChain.doFilter(request, response); // if everything is good then Authentication will be set in SpringSecurityContext and request moves ahed 
												// else Security will not allowed to access apis and then JwtAuthenticationEntryPoint.java 
												//class method will get called commence method will executed
		
			// This class JwtAuthenticationFilter will execute each and every time while calling any apis to check the token is valid or not			
	}

}
