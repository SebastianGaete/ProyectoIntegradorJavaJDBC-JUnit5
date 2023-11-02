package org.sebastian.database.utilities;
import org.sebastian.models.User;
import java.util.List;

public interface InterfaceMethodsBD<T> {

    List<T> listAll(String table);
    User read(Integer id);
    void create(User user);
    void update(Integer id,  String... attributes);
    void delete(String email);

}
