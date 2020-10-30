package com.nevdev.witcher.controller;

import com.nevdev.witcher.core.*;
import com.nevdev.witcher.enums.Bestiary;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.enums.Region;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.models.TaskModifyViewModel;
import com.nevdev.witcher.models.TaskListViewModel;
import com.nevdev.witcher.models.TaskViewModel;
import com.nevdev.witcher.services.TaskService;
import com.nevdev.witcher.services.UserService;
import com.nevdev.witcher.services.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;


@RestController
@RequestMapping("task")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    @Value("${jwt.header}")
    private String tokenHeader;

    private final JwtTokenUtil jwtTokenUtil;

    private static final
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    public TaskController(JwtTokenUtil jwtTokenUtil, TaskService taskService, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.taskService = taskService;
        this.userService = userService;
    }

    @RequestMapping(value = "/tasks")
    public ResponseEntity<?> tasks(@RequestHeader("Authorization") String token){//HttpServletRequest request){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        if(user != null){
            List<TaskViewModel> tasks = new ArrayList<>();
            taskService.find(false).forEach((item) -> tasks.add(new TaskViewModel(item,
                    userService.get(item.getCustomerId()),
                    userService.get(item.getWitcherId()))
            ));
            logger.info("Get all tasks");
            return ResponseEntity.ok(tasks);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/quests")
    public ResponseEntity<?> quests(@RequestHeader("Authorization") String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        if(user != null){
            List<TaskViewModel> activeTasks = new ArrayList<>();
            taskService.getActiveQuests(user.getId()).forEach((item) -> activeTasks.add(new TaskViewModel(item,
                    userService.get(item.getCustomerId()),
                    userService.get(item.getWitcherId()))
            ));
            List<TaskViewModel> winTasks = new ArrayList<>();
            taskService.getWinHistoryQuests(user.getId()).forEach((item) -> winTasks.add(new TaskViewModel(item,
                    userService.get(item.getCustomerId()),
                    userService.get(item.getWitcherId()))
            ));
            List<TaskViewModel> loseTasks = new ArrayList<>();
            taskService.getLoseHistoryQuests(user.getId()).forEach((item) -> loseTasks.add(new TaskViewModel(item,
                    userService.get(item.getCustomerId()),
                    userService.get(item.getWitcherId()))
            ));
            TaskListViewModel tasks = new TaskListViewModel(activeTasks, winTasks, loseTasks);

            logger.info("Get all witcher tasks");
            return ResponseEntity.ok(tasks);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/customer-quests")
    public ResponseEntity<?> customerQuests(@RequestHeader("Authorization") String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        if(user != null){
            List<TaskViewModel> allTasks = new ArrayList<>();
            taskService.getCustomerQuests(user.getId()).forEach((item) -> allTasks.add(new TaskViewModel(item,
                    userService.get(item.getCustomerId()),
                    userService.get(item.getWitcherId()))
            ));
            List<TaskViewModel> activeTasks = new ArrayList<>();
            allTasks.stream().filter(item -> !item.getDone()).forEach(activeTasks::add);
            List<TaskViewModel> doneTasks = new ArrayList<>();
            allTasks.stream().filter(item -> item.getWitchersCompleted().size() > 0).forEach(doneTasks::add);
            List<TaskViewModel> historyTasks = new ArrayList<>();
            allTasks.stream().filter(Task::getDone).forEach(historyTasks::add);

            TaskListViewModel tasks = new TaskListViewModel(activeTasks, doneTasks, historyTasks);

            logger.info("Get all customer tasks");
            return ResponseEntity.ok(tasks);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody TaskModifyViewModel taskRequest, @RequestHeader("Authorization") String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        if (user != null) {
            Task task = mapModifyTask(taskRequest, user.getId(), new Reward());
            Task created = taskService.create(task);
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/details/{id}")
                    .buildAndExpand(created.getId())
                    .toUri();
            logger.info(String.format("Created task on %s", uri));
            return ResponseEntity.created(uri).body(created);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/details/{id}")
    public ResponseEntity<?> details(HttpServletRequest request,  @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Task task = taskService.get(id);
        if(user != null && task != null){
            logger.info("Get task");
            return ResponseEntity.ok(new TaskViewModel(task, userService.get(task.getCustomerId()),
                    userService.get(task.getWitcherId())));
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/accept/{id}")
    public ResponseEntity<?> accept(HttpServletRequest request,  @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Task task = taskService.get(id);
        if(user != null && task != null){
            task.getWitchers().add(user);
            task = taskService.edit(task);

            logger.info("Accept task");
            return ResponseEntity.ok(new TaskViewModel(task,
                    userService.get(task.getCustomerId()),
                    userService.get(task.getWitcherId()))
            );
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/cancel/{id}")
    public ResponseEntity<?> cancel(HttpServletRequest request,  @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Task task = taskService.get(id);
        if(user != null && task != null){
            task.getWitchers().remove(user);
            task.getWitchersCompleted().remove(user);
            task = taskService.edit(task);
            logger.info("Cancel task");
            return ResponseEntity.ok(new TaskViewModel(task,
                    userService.get(task.getCustomerId()),
                    userService.get(task.getWitcherId())));
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/complete/{id}")
    public ResponseEntity<?> complete(HttpServletRequest request,  @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Task task = taskService.get(id);
        if(user != null && task != null){
            if(task.getWitchers().remove(user)){
                task.getWitchersCompleted().add(user);
            }
            else{
                task.getWitchersCompleted().remove(user);
                task.getWitchers().add(user);
            }
            task = taskService.edit(task);
            logger.info("Complete task");
            return ResponseEntity.ok(task);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/reward/{id}")
    public ResponseEntity<?> reward(HttpServletRequest request, @RequestBody long userID, @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Task task = taskService.get(id);
        User witcher = userService.get(userID);
        if(user != null && task != null && witcher != null){
            taskService.rewardQuest(user, witcher, task.getReward());
            task.setDone(true);
            task.setWitcherId(witcher.getId());
            task.setCompletionOn(new Date(System.currentTimeMillis()));
            task.setWitchersCompleted(null);
            task.setPaid(true);

            task = taskService.edit(task);
            logger.info("Reward task");
            return ResponseEntity.ok(task);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/refuse/{id}")
    public ResponseEntity<?> refuse(HttpServletRequest request, @RequestBody long userID, @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Task task = taskService.get(id);
        User witcher = userService.get(userID);
        if(user != null && task != null && witcher != null){
            task.getWitchersCompleted().remove(witcher);
            task.getWitchers().add(witcher);
            task = taskService.edit(task);
            logger.info("Refuse task");
            return ResponseEntity.ok(task);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(HttpServletRequest request,  @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Task task = taskService.get(id);
        if(user != null && task != null){
            if(user.getRole() == Role.KING || user.getId().equals(task.getCustomerId())){
                taskService.delete(task);
                logger.info("Delete task");
                return ResponseEntity.ok(task);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/edit/{id}")
    public ResponseEntity<?> edit(HttpServletRequest request, @RequestBody TaskModifyViewModel task, @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Task oldTask = taskService.get(id);
        if(user != null && task != null){
            if(user.getRole() == Role.KING || user.getId().equals(oldTask.getCustomerId())){
                Task modifiedTask = mapModifyTask(task, oldTask.getCustomerId(), oldTask.getReward());

                oldTask.setTitle(modifiedTask.getTitle());
                oldTask.setLocationComment(modifiedTask.getLocationComment());
                oldTask.setBeasts(modifiedTask.getBeasts());
                oldTask.setReward(modifiedTask.getReward());

                Task updatedTask = taskService.edit(oldTask);
                updatedTask.setLocation(modifiedTask.getLocation());
                Task updated = taskService.edit(updatedTask);

                URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/details/{id}")
                        .buildAndExpand(updated.getId())
                        .toUri();
                logger.info(String.format("Updated task on %s", ServletUriComponentsBuilder
                        .fromCurrentRequest()));
                return ResponseEntity.created(uri).body(updated);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    private Task mapModifyTask(TaskModifyViewModel task, Long customerId, Reward reward){
        Location location;
        List<Beast> beasts;
        switch (task.getCheckedLocation()){
            case "Аэдирн":
                location = new Location(Region.AEDIRN);
                break;
            case "Брокилон":
                location = new Location(Region.BROKILON);
                break;
            case "Цидарис":
                location = new Location(Region.CIDARIS);
                break;
            case "Цинтра":
                location = new Location(Region.CINTRA);
                break;
            case "Хенгфорс":
                location = new Location(Region.HENGFORS);
                break;
            case "Каэдвен":
                location = new Location(Region.KAEDWEN);
                break;
            case "Ковир":
                location = new Location(Region.KOVIR);
                break;
            case "Лирия":
                location = new Location(Region.LYRIA);
                break;
            case "Редания":
                location = new Location(Region.REDANIA);
                break;
            case "Скеллиге":
                location = new Location(Region.SKELLIGE);
                break;
            case "Темерия":
                location = new Location(Region.TEMERIA);
                break;
            default:
                location = new Location(Region.VERDEN);
                break;
        }
        switch (task.getCheckedCurrency()){
            case "Орен":
                reward.setType(Currency.OREN);
                break;
            case "Крона":
                reward.setType(Currency.CROWN);
                break;
            default:
                reward.setType(Currency.DUCAT);
                break;
        }
        reward.setReward(task.getCheckedRewardValue());
        switch (task.getCheckedBeast()){
            case "Альп":
                beasts = new ArrayList<>(Collections.singletonList(new Beast(Bestiary.ALP)));
                break;
            case "Василиск":
                beasts = new ArrayList<>(Collections.singletonList(new Beast(Bestiary.BASILISK)));
                break;
            case "Брукса":
                beasts = new ArrayList<>(Collections.singletonList(new Beast(Bestiary.BRUXA)));
                break;
            case "Драконид":
                beasts = new ArrayList<>(Collections.singletonList(new Beast(Bestiary.DRACONID)));
                break;
            case "Утопец":
                beasts = new ArrayList<>(Collections.singletonList(new Beast(Bestiary.DROWNER)));
                break;
            case "Гуль":
                beasts = new ArrayList<>(Collections.singletonList(new Beast(Bestiary.GHOUL)));
                break;
            case "Голем":
                beasts = new ArrayList<>(Collections.singletonList(new Beast(Bestiary.GOLEM)));
                break;
            case "Кикимора":
                beasts = new ArrayList<>(Collections.singletonList(new Beast(Bestiary.KIKIMORE)));
                break;
            case "Ночница":
                beasts = new ArrayList<>(Collections.singletonList(new Beast(Bestiary.NIGHTWRAITH)));
                break;
            case "Стрыга":
                beasts = new ArrayList<>(Collections.singletonList(new Beast(Bestiary.STRIGA)));
                break;
            case "Виверна":
                beasts = new ArrayList<>(Collections.singletonList(new Beast(Bestiary.WYVERN)));
                break;
            default:
                beasts = new ArrayList<>(Collections.singletonList(new Beast(Bestiary.OTHER)));
                break;
        }
        return new Task(task.getTitle(), task.getLocationComment(), location, reward, customerId, beasts);
    }


}
