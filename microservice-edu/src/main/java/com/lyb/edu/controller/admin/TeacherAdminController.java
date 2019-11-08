package com.lyb.edu.controller.admin;

import com.lyb.edu.entity.Teacher;
import com.lyb.edu.service.TeacherService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liuyoubin
 * @since 2019/11/8 - 21:51
 */
@RestController
@RequestMapping("/admin/edu/teacher")
@CrossOrigin
public class TeacherAdminController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 查询所有教师
     */
    @GetMapping
    public List<Teacher> list(){
        return teacherService.list(null);
    }

    /**
     * 通过id删除教师
     */
    @DeleteMapping("/{id}")
    public boolean removeById(@PathVariable(value = "id") String id){
        return teacherService.removeById(id);
    }
}
