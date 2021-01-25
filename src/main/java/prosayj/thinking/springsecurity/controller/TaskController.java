package prosayj.thinking.springsecurity.controller;

import org.springframework.web.bind.annotation.*;

/**
 * TaskController
 *
 * @author yangjian@bubi.cn
 * @date 2020-07-15 下午 11:42
 * @since 1.0.0
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @GetMapping
    public String listTasks() {
        return "任务列表";
    }

    @PostMapping
    public String newTasks() {
        return "创建了一个新的任务";
    }

    @PutMapping("/{taskId}")
    public String updateTasks(@PathVariable("taskId") Integer id) {
        return "更新了一下 id: " + id + " 的任务";
    }

    @DeleteMapping("/{taskId}")
    public String deleteTasks(@PathVariable("taskId") Integer id) {
        return "删除了 id: " + id + " 的任务";
    }
}