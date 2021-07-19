package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.exception.UsernameNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Authority;
import nl.novi.autogarage_roy_kersten.model.User;
import nl.novi.autogarage_roy_kersten.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    //Attributes
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    //Methods

    //get all users
    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    //get user by username
    @Override
    public Optional<User> getUser(String username) {
        return userRepository.findById(username);
    }


    //create a new user set username, set password (
    @Override
    public String createUser(User user) {
        User newUser = userRepository.save(user);
        user.setUsername(newUser.getUsername());
        user.setEnabled(newUser.isEnabled());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));                                                //password will be stored encoded in the database
        userRepository.save(user);
        return newUser.getUsername();
    }

    //delete a user by Id (username)
    @Override
    public void deleteUser(String username) {
        if (!userRepository.existsById(username)) {
            throw new BadRequestException();
        }

        userRepository.deleteById(username);
    }


    //update user password and enable or lock user (enable = true or false)
    @Override
    public void updateUser(String username, User newUser) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
        User user = userRepository.findById(username).get();
        user.setEnabled(newUser.isEnabled());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));                                                //password will be stored encoded in the database
        userRepository.save(user);
    }

    //Get Authorities by Id (username)
   @Override
    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        return user.getAuthorities();
    }

    //add Authorities by Id (username)
    @Override
    public void addAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
        }


    //remove authority by Id (username)
    @Override
    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

}
