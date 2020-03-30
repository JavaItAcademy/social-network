package kg.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "username")
    String username;

    @ManyToMany
    List<User> followers;

    @ManyToMany(mappedBy="followers")
    List<User> following;

//    @ManyToMany
//    @JoinTable(name = "followers1",
//        joinColumns = @JoinColumn(name = "follow_user_id"),
//        inverseJoinColumns = @JoinColumn(name = "following_user_id"))
//    List<User> followers1;
//
//    @ManyToMany
//    @JoinTable(name = "followers1",
//        joinColumns = @JoinColumn(name = "following_user_id"),
//        inverseJoinColumns = @JoinColumn(name = "follow_user_id"))
//    List<User> followers2;

}
