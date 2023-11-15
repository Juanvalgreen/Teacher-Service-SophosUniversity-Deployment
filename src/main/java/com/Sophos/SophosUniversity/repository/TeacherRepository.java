package com.Sophos.SophosUniversity.repository;

import com.Sophos.SophosUniversity.entities.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher,Long> {

    @Query(value = "SELECT * FROM teachers t WHERE CAST(t.teacher_id AS VARCHAR) LIKE :searchId || '%'", nativeQuery = true)
    List<Teacher> searchAllTeachersById(@Param("searchId") Long searchId);

    @Query(value = "SELECT * FROM teachers t WHERE LOWER(t.teacher_full_name) LIKE LOWER(:searchName || '%')", nativeQuery = true)
    List<Teacher> searchAllTeachersByName(@Param("searchName") String searchName);

}
