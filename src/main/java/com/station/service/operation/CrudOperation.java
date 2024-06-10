package com.station.service.operation;

import java.util.List;

public interface CrudOperation<T>{
    T findById(int id);
    List<T> findAll();
    T save(T toSave);
    T update(T toUpdate);
}
