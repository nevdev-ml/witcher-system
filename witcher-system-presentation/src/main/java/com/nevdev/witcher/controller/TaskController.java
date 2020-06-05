package com.nevdev.witcher.controller;

import com.nevdev.witcher.core.*;
import com.nevdev.witcher.enums.Bestiary;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.enums.Region;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.models.TaskViewModel;
import com.nevdev.witcher.services.TaskService;
import com.nevdev.witcher.services.UserService;
import com.nevdev.witcher.services.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


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

    @GetMapping(value = "/tasks") //TODO: ANY ROLE
    public Iterable<Task> index(){
        buildMock();
        return taskService.find(false);
    }

    @GetMapping(value = "/create") //TODO: IF ROLE IS USER
    public ResponseEntity<?> create(@RequestBody TaskViewModel task, @RequestHeader("Authorization") String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        if (user != null) {
            switch (task.getCheckedLocation()){
                case "Аэдирн":
                    task.setLocation(new Location(Region.AEDIRN));
                    break;
                case "Брокилон":
                    task.setLocation(new Location(Region.BROKILON));
                    break;
                case "Цидарис":
                    task.setLocation(new Location(Region.CIDARIS));
                    break;
                case "Цинтра":
                    task.setLocation(new Location(Region.CINTRA));
                    break;
                case "Хенгфорс":
                    task.setLocation(new Location(Region.HENGFORS));
                    break;
                case "Каэдвен":
                    task.setLocation(new Location(Region.KAEDWEN));
                    break;
                case "Ковир":
                    task.setLocation(new Location(Region.KOVIR));
                    break;
                case "Лирия":
                    task.setLocation(new Location(Region.LYRIA));
                    break;
                case "Редания":
                    task.setLocation(new Location(Region.REDANIA));
                    break;
                case "Скеллиге":
                    task.setLocation(new Location(Region.SKELLIGE));
                    break;
                case "Темерия":
                    task.setLocation(new Location(Region.TEMERIA));
                    break;
                default:
                    task.setLocation(new Location(Region.VERDEN));
                    break;
            }
            switch (task.getCheckedCurrency()){
                case "Орен":
                    task.setReward(new Reward(task.getRewardValue(), Currency.OREN));
                    break;
                case "Крона":
                    task.setReward(new Reward(task.getRewardValue(), Currency.CROWN));
                    break;
                default:
                    task.setReward(new Reward(task.getRewardValue(), Currency.DUCAT));
                    break;
            }
            switch (task.getCheckedBeast()){
                case "Альп":
                    task.setBeasts(new ArrayList<>(Collections.singletonList(new Beast(Bestiary.ALP))));
                    break;
                case "Василиск":
                    task.setBeasts(new ArrayList<>(Collections.singletonList(new Beast(Bestiary.BASILISK))));
                    break;
                case "Брукса":
                    task.setBeasts(new ArrayList<>(Collections.singletonList(new Beast(Bestiary.BRUXA))));
                    break;
                case "Драконид":
                    task.setBeasts(new ArrayList<>(Collections.singletonList(new Beast(Bestiary.DRACONID))));
                    break;
                case "Утопец":
                    task.setBeasts(new ArrayList<>(Collections.singletonList(new Beast(Bestiary.DROWNER))));
                    break;
                case "Гуль":
                    task.setBeasts(new ArrayList<>(Collections.singletonList(new Beast(Bestiary.GHOUL))));
                    break;
                case "Голем":
                    task.setBeasts(new ArrayList<>(Collections.singletonList(new Beast(Bestiary.GOLEM))));
                    break;
                case "Кикимора":
                    task.setBeasts(new ArrayList<>(Collections.singletonList(new Beast(Bestiary.KIKIMORE))));
                    break;
                case "Ночница":
                    task.setBeasts(new ArrayList<>(Collections.singletonList(new Beast(Bestiary.NIGHTWRAITH))));
                    break;
                case "Стрыга":
                    task.setBeasts(new ArrayList<>(Collections.singletonList(new Beast(Bestiary.STRIGA))));
                    break;
                case "Виверна":
                    task.setBeasts(new ArrayList<>(Collections.singletonList(new Beast(Bestiary.WYVERN))));
                    break;
                default:
                    task.setBeasts(new ArrayList<>(Collections.singletonList(new Beast(Bestiary.OTHER))));
                    break;
            }
            task.setCustomerId(user.getId());
            Task created = taskService.create(task);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/details/{id}")
                    .buildAndExpand(created.getId())
                    .toUri();
            return ResponseEntity.created(location).body(created);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/details/{id}") //TODO: ANY ROLE
    public ResponseEntity<?> details(HttpServletRequest request,  @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Task task = taskService.get(id);
        if(user != null && task != null){
            return ResponseEntity.ok(task);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/accept/{id}") //TODO: IF ROLE IS WITCHER
    public ResponseEntity<?> accept(HttpServletRequest request,  @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Task task = taskService.get(id);
        if(user != null && task != null){
            task.getWitchers().add(user);
            taskService.edit(task);
            return ResponseEntity.ok(task);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @GetMapping(value = "/quests") //TODO: IF ROLE IS WITCHER
    public ResponseEntity<?> quests(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        if(user != null){
            return ResponseEntity.ok(taskService.getAllQuests(user.getId()));
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    private void buildMock(){ //TODO: Mock data
        String title = "Нужно убить этих чертовых эльфов!";
        String locationComment = "Водятся в лесах Брокилона!";
        Location location = new Location(Region.BROKILON);
        Reward reward = new Reward(1000d, Currency.CROWN);
        Beast beast = new Beast(Bestiary.BRUXA);

        Authority USER = new Authority();
        USER.setRoleName(Role.USER);
        List<Authority> authoritiesList = new ArrayList<>();
        authoritiesList.add(USER);
        User user = new User("login", BCrypt.hashpw("password", BCrypt.gensalt()), Role.USER,
                "Фольтест", "Темерский", "foltest@gmail.com", true,
                new Date(System.currentTimeMillis()), authoritiesList);

        Authority WITCHER = new Authority();
        WITCHER.setRoleName(Role.WITCHER);
        List<Authority> authoritiesList1 = new ArrayList<>();
        authoritiesList1.add(WITCHER);
        User user1 = new User("witcher", BCrypt.hashpw("password", BCrypt.gensalt()), Role.WITCHER,
                "Геральт", "Ривийский", "witcher@gmail.com", true,
                new Date(System.currentTimeMillis()), authoritiesList1);

        Long customerId = userService.create(user).getId();

        //WITCHER TEST
        User witcher = userService.create(user1);

        // TODO WHEN DONE SET DONE(TRUE), COMPLETION DATE, WITCHER(USER)

        Task task = new Task(title, locationComment, location, reward, customerId,
                new ArrayList<>(Collections.singletonList(beast)));
        task.setWitchers(new ArrayList<>(Collections.singletonList(witcher)));
        taskService.create(task);
    }
}
