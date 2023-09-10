package borelgabriel.com.br.springwebstore.service;

import borelgabriel.com.br.springwebstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImplUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository.findUserByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(user.getLogin(), user.getPassword(), user.getAuthorities());
    }
}
