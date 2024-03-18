package hiber.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Data
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "username")
   private String username;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "first_name")
   private String firstName;

   @Column(name = "password")
   private String password;


   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
   @Fetch(FetchMode.JOIN)
   @JoinTable(name = "users_roles",
           joinColumns = @JoinColumn(name = "users_id"),
           inverseJoinColumns = @JoinColumn(name = "roles_id"))
   private Set<Role> roles;



   public User() {}

   public User(String username, String lastName, String firstName, String password, Set<Role> roles) {
      this.username = username;
      this.lastName = lastName;
      this.firstName = firstName;
      this.password = password;
      this.roles = roles;
   }


   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return getRoles();
   }


   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

//   public String getAllRolesString() {
//      StringBuilder allRolesString = new StringBuilder(new String());
//      String roleUser = "";
//      for (Role role: getRoles()) {
//         roleUser = allRolesString
//                 .append(role.toString())
//                 .append(" ")
//                 .toString()
//                 .split(" ")[1]
//                 .replace("name=","")
//                 .replace(",","")
//                 .replace("ROLE_", "");
//      }
//
//      return roleUser;
//   }

   public String getAllRolesString() {
      List<String> roleNames = new ArrayList<>();
      for (Role role : getRoles()) {
         String roleName = role.toString()
                 .split(" ")[1]
                 .replace("name=", "")
                 .replace(",", "")
                 .replace("ROLE_", "");
         roleNames.add(roleName);
      }
      return String.join(", ", roleNames);
   }

}
