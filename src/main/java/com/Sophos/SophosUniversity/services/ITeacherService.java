package com.Sophos.SophosUniversity.services;

import com.Sophos.SophosUniversity.entities.Teacher;

import java.util.List;

public interface ITeacherService {
    List<Teacher> getAllTeachers() throws Exception;

    List<Teacher> searchAllTeachersById(Long id) throws Exception;

    List<Teacher> searchAllTeachersByName(String nameSearch) throws Exception;


    Teacher getTeacherById(Long id) throws Exception;

    List<Teacher> getMultipleTeachersById(Iterable<Long> id) throws Exception;

    String addNewTeacher(Teacher teacher) throws Exception;

    String updateTeacher(Teacher teacher) throws Exception;

    String deleteTeacher(Long id) throws Exception;
}
