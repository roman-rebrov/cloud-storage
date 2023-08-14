package com.cloudstorage.storage.repository;

import com.cloudstorage.storage.entity.persistence.FilePersistence;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilesJpaRepository extends JpaRepository<FilePersistence, Long> {

    @Transactional
    @Query(nativeQuery = true, value = "select * from Files where user_dir = :dir limit :lim")
    public List<FilePersistence> findByDirname(String dir, int lim);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into files (user_dir, filename, size) values (:dir, :name, :size)")
    public void save(String dir, String name, long size);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update files set filename=:newName where user_dir=:dir and filename=:oldName")
    public void updateFilename(String dir, String oldName, String newName);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from files where user_dir=:dir and filename=:name")
    public void deleteFilename(String dir, String name);
}
