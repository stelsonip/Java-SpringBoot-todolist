package com.ivan.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ivan.todolist.user.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 
@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // validation should only be in the /tasks path/route

        var servletPath = request.getServletPath();

        if (servletPath.equals("/tasks/")) {
            // get auth (user & password)
            var authorization = request.getHeader("Authorization");

            // substring method used to extract a part of a text (in this case to separate
            // the *Basic* from the Base64 generated
            var authEncoded = authorization.substring("Basic".length()).trim();

            // gets the enconded Base64 and turns into an array of bytes
            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            // gets the array of bytes and turns into string
            var authString = new String(authDecode);

            // will return an array spliting the username and password which were
            // username:password => will now be ["username", "password"]
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // validate users existence
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401);
            } else {
                // validate password
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    // sending an attribute inside the controller
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
            
        } else {
            filterChain.doFilter(request, response);
        }

        
    }
}
