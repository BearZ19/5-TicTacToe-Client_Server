package Dao;

import java.util.List;

public interface IDao<T>
{
    void save(T item);
    List<T> loadAll();
    //void update(T item);
    void delete(T item);
}
