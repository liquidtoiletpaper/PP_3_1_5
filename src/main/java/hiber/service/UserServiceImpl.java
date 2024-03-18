package hiber.service;


import hiber.model.Role;
import hiber.model.User;
import hiber.repository.RoleRepository;
import hiber.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

   private final UserRepository userRepository;
   private final RoleRepository roleRepository;
   private final RoleService roleService;

   @Override
   @Transactional
   public void saveUser(User user) {
      User userDB = userRepository.findUserByUsername(user.getUsername());
      if (userDB != null) {
         return;
      }
      if (user.getFirstName().equals("") | user.getPassword().equals("")) {
         return;
      }
      user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
      userRepository.save(user);
   }

   @Override
   public User findUserById(Long id) {
      return userRepository.getById(id);
   }

   @Override
   public List<User> findAllUsers() {
      return userRepository.findAll();
   }

   @Override
   @Transactional
   public void updateUser(User user) {
      if(user.getPassword().isEmpty()) {
         user.setPassword(findUserById(user.getId()).getPassword());
      } else {
         user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
      }
      userRepository.save(user);
   }

   @Override
   @Transactional
   public void deleteUserById(Long id) {
      userRepository.deleteById(id);
   }

   @Override
   public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
      UserDetails userDetails = userRepository.findUserByUsername(email);
      if (userDetails == null) {
         throw new UsernameNotFoundException("Ты кого искал по почте " + email +" не нашел и не найдешь...");
      }
      return userDetails;
   }

   @Override
   public Long getUserIdByEmail(String email) {
      return userRepository.findUserByUsername(email).getId();
   }

   @Override
   public void getUserAndRoles(User user, String[] roles) {
      user.setRoles(roleService.findRoleByRole(Objects.requireNonNullElseGet(roles,() -> new String[] {"ROLE_USER"})));

   }

   @Override
   public void getNotNullRole(User user) {
      if(user.getRoles() == null) {
         user.setRoles(Collections.singleton(new Role(2L)));
      }
   }

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      UserDetails userDetails = userRepository.findUserByUsername(email);
      if (userDetails == null) {
         throw new UsernameNotFoundException("Ты кого искал по почте " + email +" не нашел и не найдешь...");
      }
      return  userDetails;
   }

   public void saveUserTest(User user) {
      User userFromDB = userRepository.findUserByUsername(user.getUsername());
      if (userFromDB != null) {
         return;
      }
      user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
      userRepository.save(user);
   }


   @PostConstruct
   public void addTestUsers() {
      roleRepository.save(new Role(1L, "ROLE_ADMIN"));
      roleRepository.save(new Role(2L, "ROLE_USER"));
      User newAdmin = new User("admin", "admin", "admin", "admin" , roleService.findRoleByRole(new String[]{"ROLE_ADMIN"}));
      saveUserTest(newAdmin);
      User newUser = new User("user", "user", "user", "user", roleService.findRoleByRole(new String[]{"ROLE_USER"}));
      saveUserTest(newUser);
   }

}
