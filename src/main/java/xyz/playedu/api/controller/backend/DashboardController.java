/**
 * This file is part of the PlayEdu.
 * (c) 杭州白书科技有限公司
 */
package xyz.playedu.api.controller.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.playedu.api.constant.BackendConstant;
import xyz.playedu.api.constant.SystemConstant;
import xyz.playedu.api.domain.User;
import xyz.playedu.api.domain.UserLearnDurationStats;
import xyz.playedu.api.service.*;
import xyz.playedu.api.types.JsonResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author 杭州白书科技有限公司
 *
 * @create 2023/3/7 13:55
 */
@RestController
@RequestMapping("/backend/v1/dashboard")
public class DashboardController {

    @Autowired private AdminUserService adminUserService;

    @Autowired private ResourceCategoryService resourceCategoryService;

    @Autowired private UserService userService;

    @Autowired private CourseService courseService;

    @Autowired private DepartmentService departmentService;

    @Autowired private ResourceService resourceService;

    @Autowired private UserLearnDurationStatsService userLearnDurationStatsService;

    @GetMapping("/index")
    public JsonResponse index() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("version", SystemConstant.VERSION);

        data.put("user_total", userService.total()); // 总学员数量
        data.put("user_today", userService.todayCount()); // 今日注册学员数量
        data.put("user_yesterday", userService.yesterdayCount()); // 昨日注册学员数量

        data.put("course_total", courseService.total()); // 线上课数量

        data.put("department_total", departmentService.total());
        data.put("resource_category_total", resourceCategoryService.total());
        data.put("admin_user_total", adminUserService.total());

        data.put(
                "resource_video_total", resourceService.total(BackendConstant.RESOURCE_TYPE_VIDEO));
        data.put(
                "resource_image_total", resourceService.total(BackendConstant.RESOURCE_TYPE_IMAGE));

        data.put("user_learn_today", userLearnDurationStatsService.todayTotal());
        data.put("user_learn_yesterday", userLearnDurationStatsService.yesterdayTotal());

        List<UserLearnDurationStats> userLearnTop10 = userLearnDurationStatsService.top10();
        Map<Integer, User> top10Users =
                userService
                        .chunks(
                                userLearnTop10.stream()
                                        .map(UserLearnDurationStats::getUserId)
                                        .toList(),
                                new ArrayList<>() {
                                    {
                                        add("id");
                                        add("name");
                                        add("avatar");
                                        add("email");
                                    }
                                })
                        .stream()
                        .collect(Collectors.toMap(User::getId, e -> e));
        data.put("user_learn_top10", userLearnTop10);
        data.put("user_learn_top10_users", top10Users);

        return JsonResponse.data(data);
    }
}
