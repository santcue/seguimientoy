package cue.edu.co.repository;

import java.util.List;

public interface Repository <T> {

    List<T> list();
    T byId(int id);
    void save(T t);
    void delete(int id);

    void update(T t);

}
