/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;

/**
 *
 * @author sassi
 */
public interface IUser<T> {
     void insert(T t);
    void delete(T t);
    void update(T t);
    List<T> readAll();
    T readByID(int idUser);
    List<T> readIdNom();
}
