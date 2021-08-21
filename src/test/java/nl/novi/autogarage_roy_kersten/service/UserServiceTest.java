package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Authority;
import nl.novi.autogarage_roy_kersten.model.User;
import nl.novi.autogarage_roy_kersten.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Test
    void getUsersTest() {
        //Arrange => create mock user object as input for test
        User user1 = new User("karel", "password1", true);
        User user2 = new User("karel", "password2", true);

        Collection<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn((List<User>) users);

        //Act
        Collection<User> validateUsers = userService.getUsers();

        //Assert => validate return of method getUsers and perform assertions
        verify(userRepository, times(1)).findAll();
        assertThat(validateUsers.size()).isEqualTo(2);
    }

    @Test
    void getUserTest() {
        //Arrange => create mock user object as input for test
        User user = new User("karel","password1",true);
        when(userRepository.existsById("karel")).thenReturn(true);
        when(userRepository.findById("karel")).thenReturn(Optional.of(user));

        //Act => call method getUser
        Optional<User> validateUser = userService.getUser("karel");

        //Assert => validate return of method getUser and perform assertions
        assertThat(validateUser.get().getUsername()).isEqualTo("karel");
        assertThat(validateUser.get().getPassword()).isEqualTo("password1");
        assertThat(validateUser.get().isEnabled()).isEqualTo(true);
    }

    @Test
    void createUserTest() {
        //Arrange => create mock user object as input for test
        User user = new User("karel","password1",true);
        when(userRepository.save(user)).thenReturn(user);


        //Act call method createUser
        userService.createUser(user);

        //Assert => validate return of method getUser and perform assertions
        verify(userRepository,times(2)).save(userCaptor.capture());
        User validateUser = userCaptor.getValue();

        assertThat(validateUser.getUsername()).isEqualTo("karel");
        assertThat(validateUser.getPassword()).isEqualTo(null);                                                     //because of passwordEncoder return is null
        assertThat(validateUser.isEnabled()).isEqualTo(true);
    }

    @Test
    void deleteUserTest() {
        //Arrange => check if username exists and return boolean true to pass BadRequestException check
        User user = new User("karel","password1",true);
        when(userRepository.existsById("karel")).thenReturn(true);

        //Act => call method deleteUser
        userService.deleteUser("karel");

        //Assert => verify if mock userRepository.deleteById has been called one time
        verify(userRepository, times(1)).deleteById("karel");
    }

    @Test
    void updateUserTest() {
        //Arrange => check if username exists and return boolean true to pass BadRequestException check
        User user = new User("karel","password1",true);
        when(userRepository.existsById("karel")).thenReturn(true);
        when(userRepository.findById("karel")).thenReturn(Optional.of(user));


        //Act => call method deleteUser
        userService.updateUser("karel", user);

        //Assert => validate return of method getUser and perform assertions
        verify(userRepository,times(1)).save(userCaptor.capture());
        User validateUser = userCaptor.getValue();

        assertThat(validateUser.getUsername()).isEqualTo("karel");
        assertThat(validateUser.getPassword()).isEqualTo(null);                                                     //because of passwordEncoder return is null
        assertThat(validateUser.isEnabled()).isEqualTo(true);
    }

    @Test
    void getAuthoritiesTest() {
        //Arrange => check if username exists and return boolean true to pass BadRequestException check
        Authority authority1 = new Authority("karel", "ROLE_ADMIN");
        Authority authority2 = new Authority("karel", "ROLE_MECHANIC");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority1);
        authorities.add(authority2);

        User user = new User("karel","password1",true,authorities);

        when(userRepository.existsById("karel")).thenReturn(true);
        when(userRepository.findById("karel")).thenReturn(Optional.of(user));

        //Act
        Set<Authority> validateAuthorities = userService.getAuthorities("karel");

        //Assert => validate return of method getAuthorities and perform assertions
        verify(userRepository, times(1)).findById("karel");
        assertThat(validateAuthorities.size()).isEqualTo(2);
    }

    @Test
    void addAuthorityTest() {
        //Arrange => check if username exists and return boolean true to pass BadRequestException check
        Authority authority1 = new Authority("karel", "ROLE_ADMIN");
        Authority authority2 = new Authority("karel", "ROLE_MECHANIC");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority1);
        authorities.add(authority2);

        User user = new User("karel", "password1", true, authorities);
        when(userRepository.existsById("karel")).thenReturn(true);
        when(userRepository.findById("karel")).thenReturn(Optional.of(user));

        //ACT
        userService.addAuthority("karel", "ROLE_CASHIER");

        //ASSERT
        verify(userRepository,times(1)).save(userCaptor.capture());
        User validateUser = userCaptor.getValue();
        assertThat(validateUser.getAuthorities().size()).isEqualTo(3);                                                  //check if authority has been added
    }

    @Test
    void removeAuthorityTest() {
        //Arrange => check if username exists and return boolean true to pass BadRequestException check
        Authority authority1 = new Authority("karel", "ROLE_ADMIN");
        Authority authority2 = new Authority("karel", "ROLE_MECHANIC");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority1);
        authorities.add(authority2);

        User user = new User("karel", "password1", true, authorities);
        when(userRepository.existsById("karel")).thenReturn(true);
        when(userRepository.findById("karel")).thenReturn(Optional.of(user));

        //ACT
        userService.removeAuthority("karel", "ROLE_MECHANIC");

        //ASSERT
        verify(userRepository,times(1)).save(userCaptor.capture());
        User validateUser = userCaptor.getValue();
        assertThat(validateUser.getAuthorities().size()).isEqualTo(1);                                                  //check if authority has been removed
    }


}
