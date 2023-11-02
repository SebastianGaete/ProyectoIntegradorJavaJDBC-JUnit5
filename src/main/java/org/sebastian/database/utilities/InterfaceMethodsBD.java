package org.sebastian.database.utilities;
import org.sebastian.models.User;
import java.util.List;

public interface InterfaceMethodsBD<T> {

    List<T> listAll(String table);
    User read(Integer id);
    void create(User user);
    void update(Integer id,  String firstName);
    void update(Integer id,  String firstName, String lastName);
    void update(Integer id, String firstName, String lastName, int age);
    void update(Integer id,  String firstName, String lastName, int age, String email);

    void delete(String email);

}
