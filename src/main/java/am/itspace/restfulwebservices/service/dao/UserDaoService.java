package am.itspace.restfulwebservices.service.dao;

import am.itspace.restfulwebservices.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1,"Vzgo",new Date(),null));
        users.add(new User(2,"Valod",new Date(),null));
        users.add(new User(3,"Poxos",new Date(),null));
    }

    public List<User> findAll(){
        return users;
    }

    public User saveUser(User user){
        if (user.getId() == null){
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User getUserById(int id){
        for (User user: users) {
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public User deleteUserById(int id){
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()){
            User next = iterator.next();
            if (next.getId() == id){
                iterator.remove();
                return next;
            }
        }
        return null;
    }
}
