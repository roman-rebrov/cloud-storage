package com.cloudstorage.storage.entity.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Users")
public class UserPersistence {
    @Id
    private long id;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private String directory;
}
