package com.revature.yolp.daos;

import java.util.List;

public interface CrudDAO<T> {
    void save();
    void delete();
    void update();
    T findById();
    List<T> findAll();
}
