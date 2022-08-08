package com.example.hospitalsystem_abdelrahmantarek.Models;

import java.io.Serializable;

public class DocNameId  implements Serializable {
    String name;
    int id;

    public DocNameId(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
