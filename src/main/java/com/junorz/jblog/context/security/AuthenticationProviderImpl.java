package com.junorz.jblog.context.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.junorz.jblog.context.Messages;
import com.junorz.jblog.context.utils.MsgUtil;
import com.junorz.jblog.domain.User;
import com.junorz.jblog.service.UserService;

public class AuthenticationProviderImpl implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationProviderImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.findByName((String) authentication.getPrincipal());
        if (user == null) {
            throw new UsernameNotFoundException(MsgUtil.message(Messages.USER_NOT_FOUND));
        }
        
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException(MsgUtil.message(Messages.SYSTEM_AUTH_FAILED));
        }

        if (passwordEncoder.matches((String) authentication.getCredentials(), user.getPassword())) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getName(), null,
                    AuthorityUtils.createAuthorityList(user.getAuthority().toString()));
            return token;
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
