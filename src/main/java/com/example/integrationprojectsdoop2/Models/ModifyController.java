package com.example.integrationprojectsdoop2.Models;

public interface ModifyController<T extends ShowComponent> {
    void initializeData(T data);
}
