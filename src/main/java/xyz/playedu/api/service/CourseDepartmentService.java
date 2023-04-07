/**
 * This file is part of the PlayEdu.
 * (c) 杭州白书科技有限公司
 */
package xyz.playedu.api.service;

import com.baomidou.mybatisplus.extension.service.IService;

import xyz.playedu.api.domain.CourseDepartment;

import java.util.List;

/**
 * @author tengteng
 * @description 针对表【course_department】的数据库操作Service
 * @createDate 2023-02-24 14:53:52
 */
public interface CourseDepartmentService extends IService<CourseDepartment> {

    List<Integer> getCourseIdsByDepIds(List<Integer> depIds);

    List<Integer> getDepIdsByCourseId(Integer courseId);

    void removeByCourseId(Integer courseId);
}
