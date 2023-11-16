package com.Sophos.SophosUniversity.services;

import com.Sophos.SophosUniversity.config.RestConts;
import com.Sophos.SophosUniversity.entities.Teacher;
import com.Sophos.SophosUniversity.exceptions.InternalServerErrorException;
import com.Sophos.SophosUniversity.exceptions.TeacherNotFoundException;
import com.Sophos.SophosUniversity.models.Courses;
import com.Sophos.SophosUniversity.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TeacherService implements ITeacherService{

    @Autowired
    private TeacherRepository repository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Teacher> getAllTeachers() throws Exception{
        try{
            return (List<Teacher>) repository.findAll();
        }catch (DataAccessException ex){
            ex.printStackTrace();
            throw new InternalServerErrorException("Error to access the database");
        }catch (RuntimeException ex) {
            // Manejar otras excepciones de tiempo de ejecución
            ex.printStackTrace();
            throw new InternalServerErrorException("Error interno del servidor");
        }
    }


    @Override
    public List<Teacher> searchAllTeachersById(Long id) throws Exception{

        try{
            return repository.searchAllTeachersById(id);
        }catch (DataAccessException ex){
            ex.printStackTrace();
            throw new InternalServerErrorException("Error to access the database");
        }catch (RuntimeException ex) {
            // Manejar otras excepciones de tiempo de ejecución
            ex.printStackTrace();
            throw new InternalServerErrorException("Error interno del servidor");
        }

    }

    @Override
    public List<Teacher> searchAllTeachersByName(String nameSearch) throws Exception{
        try{
            return repository.searchAllTeachersByName(nameSearch);
        }catch (DataAccessException ex){
            ex.printStackTrace();
            throw new InternalServerErrorException("Error to access the database");
        }catch (RuntimeException ex) {
            // Manejar otras excepciones de tiempo de ejecución
            ex.printStackTrace();
            throw new InternalServerErrorException("Error interno del servidor");
        }

    }



    @Override
    public Teacher getTeacherById(Long id) throws Exception{
        try{
           return  repository.findById(id).get();
        }catch (DataAccessException ex){
            ex.printStackTrace();
            throw new InternalServerErrorException("Error to access the database");
        } catch(RuntimeException ex){
            throw new TeacherNotFoundException("Maestro no encontrado con ID: " + id);
        }
    }

    @Override
    public List<Teacher> getMultipleTeachersById(Iterable<Long> id) throws Exception{
        try{
            return (List<Teacher>) repository.findAllById(id);
        }catch (DataAccessException ex){
            ex.printStackTrace();
            throw new InternalServerErrorException("Error to access the database");
        } catch(RuntimeException ex){
            throw new TeacherNotFoundException("Maestro no encontrado con esta lista de IDs");
        }

    }

    @Override
    public String addNewTeacher(Teacher teacher) throws Exception{
        try{
            repository.save(teacher);
            return "Maestro creado exitosamente";
        }catch (DataAccessException ex) {
            ex.printStackTrace();
            throw new InternalServerErrorException("Error al acceder a la base de datos");
        } catch (RuntimeException ex) {
            throw new InternalServerErrorException("Error interno del servidor");
        }
    }


    @Override
    public String updateTeacher(Teacher teacher) throws Exception{
        Long teacherId = teacher.getTeacher_id();
        if(repository.existsById(teacherId)){
            try{
                repository.save(teacher);
                return "Maestro actualizado correctamente";
            }catch (DataAccessException ex) {
                ex.printStackTrace();
                throw new InternalServerErrorException("Error al acceder a la base de datos");
            } catch (RuntimeException ex) {
                throw new InternalServerErrorException("Error interno del servidor");
            }
        }else{
            throw new TeacherNotFoundException("Maestro no encotrado con este ID: " + teacherId);
        }
    }


    @Override
    public String deleteTeacher(Long id) throws Exception{
        if(repository.existsById(id)){
            try{

                ResponseEntity<Courses[]> responseEntity = restTemplate.getForEntity(RestConts.BASE_URL_COURSES_DEPLOY + "/api/v1/courses/"+id+"/teachers", Courses[].class);
                List<Courses> courses = Arrays.asList(responseEntity.getBody());

                if(!courses.isEmpty()){
                    System.out.println("no ta vaciio");
                    for (Courses course : courses) {



                        course.setTeacher_id(null);
                        restTemplate.put(RestConts.BASE_URL_COURSES_DEPLOY+"/api/v1/courses",course,Courses.class);

                    }
                }

                repository.deleteById(id);
                return "Maestro eliminado exitosamente";
            }catch (DataAccessException ex) {
                ex.printStackTrace();
                throw new InternalServerErrorException("Error al acceder a la base de datos");
            } catch (RuntimeException ex) {
                System.out.println(ex);
                throw new InternalServerErrorException("Error interno del servidor");
            }
        }else{
            throw new TeacherNotFoundException("Maestro no encontrado con este ID: " + id);
        }
    }







}
