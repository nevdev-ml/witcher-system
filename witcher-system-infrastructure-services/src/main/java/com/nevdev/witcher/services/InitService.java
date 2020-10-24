package com.nevdev.witcher.services;

import com.nevdev.witcher.core.*;
import com.nevdev.witcher.enums.Bestiary;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.enums.Region;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InitService {
    private final TaskService taskService;
    private final UserService userService;
    private final AuthorityService authorityService;

    @Autowired
    UserRepository userRepository;

    public InitService(TaskService taskService, UserService userService, AuthorityService authorityService) {
        this.taskService = taskService;
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @PostConstruct
    private void init(){
        if(userRepository.count() == 0){
            List<User> users = initUsers();
            User customer = users.stream().filter(user -> user.getRole() == Role.USER).findFirst().orElse(null);
            List<User> witchers = users.stream().filter(user -> user.getRole() == Role.WITCHER).collect(Collectors.toList());
            if (customer != null && witchers.size() > 0){
                initTasks(customer.getId(), witchers);
            }
        }
    }

    private List<User> initUsers(){
        List<String> username = new ArrayList<>(Arrays.asList("king", "witcher", "witcher11", "user", "vendor",
                "blacksmith"));
        String password = "password";
        List<Role> roles = new ArrayList<>(Arrays.asList(Role.KING, Role.WITCHER, Role.WITCHER, Role.USER, Role.VENDOR,
                Role.BLACKSMITH));
        List<String> firstNames = new ArrayList<>(Arrays.asList("Фольтест", "Геральт", "Лето", "Бедный", "Жадный",
                "Умелый"));
        List<String> lastNames = new ArrayList<>(Arrays.asList("Темерский", "Ривийский", "Гулета", "Простолюдин",
                "Купец", "Кузнец"));
        List<String> emails = new ArrayList<>(Arrays.asList("foltest@gmail.com", "geerkus@gmail.com",
                "witcher@gmail.com", "vodka@gmail.com", "trade@gmail.com", "blacksmith@gmail.com"));
        List<User> users = new ArrayList<>();
        for (int i = 0; i < username.size(); i++){
            users.add(createUser(username.get(i), password, roles.get(i), firstNames.get(i), lastNames.get(i),
                    emails.get(i)));
        }
        return users;
    }

    private User createUser(String username, String password, Role role, String firstName, String lastName,
                            String email){
        Authority authority = new Authority();
        authority.setRoleName(role);
        Authority userAuthority = authorityService.create(authority);
        List<Authority> authoritiesList = new ArrayList<>();
        authoritiesList.add(userAuthority);
        Boolean kingRepository = role == Role.KING;
        return userService.create(new User(username, BCrypt.hashpw(password, BCrypt.gensalt()), role, firstName, lastName, email,
                true, new Date(System.currentTimeMillis()), authoritiesList, kingRepository));
    }

    private void initTasks(Long customerId, List<User> witchers){
        String title = "Заказ на %s";
        String comment = "Водится в окрестностях %s";

        List<Pair> beasts = new ArrayList<>();
        beasts.add(new Pair(Bestiary.ALP, "альпа"));
        beasts.add(new Pair(Bestiary.BASILISK, "василиска"));
        beasts.add(new Pair(Bestiary.BRUXA, "бруксу"));
        beasts.add(new Pair(Bestiary.DRACONID, "драконида"));
        beasts.add(new Pair(Bestiary.DROWNER, "утопца"));
        beasts.add(new Pair(Bestiary.GHOUL, "гуля"));
        beasts.add(new Pair(Bestiary.GOLEM, "голема"));
        beasts.add(new Pair(Bestiary.KIKIMORE, "кикимору"));
        beasts.add(new Pair(Bestiary.NIGHTWRAITH, "ночницу"));
        beasts.add(new Pair(Bestiary.STRIGA, "стрыгу"));
        beasts.add(new Pair(Bestiary.WYVERN, "виверну"));
        beasts.add(new Pair(Bestiary.OTHER, "неизведанное существо"));

        List<Pair> locations = new ArrayList<>();
        locations.add(new Pair(Region.AEDIRN, "Аэдирна"));
        locations.add(new Pair(Region.BROKILON, "Брокилона"));
        locations.add(new Pair(Region.CIDARIS, "Цидариса"));
        locations.add(new Pair(Region.CINTRA, "Цинтры"));
        locations.add(new Pair(Region.HENGFORS, "Хенгфорса"));
        locations.add(new Pair(Region.KAEDWEN, "Каэдвена"));
        locations.add(new Pair(Region.KOVIR, "Ковира"));
        locations.add(new Pair(Region.LYRIA, "Лирии"));
        locations.add(new Pair(Region.REDANIA, "Редании"));
        locations.add(new Pair(Region.SKELLIGE, "Скеллиге"));
        locations.add(new Pair(Region.TEMERIA, "Темерии"));
        locations.add(new Pair(Region.VERDEN, "Вердена"));

        List<Currency> currencies = new ArrayList<>(Arrays.asList(Currency.CROWN, Currency.DUCAT, Currency.OREN));
        List<Integer> prices = new ArrayList<>();
        for (int i = 500; i < 10000; i += 500){
            prices.add(i);
        }

        Random random = new Random();
        int taskCount = 10;
        for(int i = 0; i < taskCount; i++){
            Pair beastPair = beasts.get(randomInt(random, beasts.size()));
            Beast beast = new Beast(beastPair.beast);
            Pair locationPair = locations.get(randomInt(random, locations.size()));
            Location location = new Location(locationPair.region);
            Currency currency = currencies.get(randomInt(random, currencies.size()));
            Double price = (double)prices.get(randomInt(random, prices.size()));
            Reward reward = new Reward(price, currency);

            Task task = new Task(String.format(title, beastPair.title), String.format(comment, locationPair.title), location,
                    reward, customerId, new ArrayList<>(Collections.singletonList(beast)));
            task = taskService.create(task);
            task.setWitchers(witchers);
            taskService.edit(task);
        }
    }

    private static class Pair{
        private Bestiary beast;
        private String title;
        private Region region;

        Pair(Bestiary beast, String title){
            this.beast = beast;
            this.title = title;
        }

        Pair(Region region, String title){
            this.region = region;
            this.title = title;
        }
    }

    private static int randomInt(Random random, int rightBound) {
        return (int)(random.nextFloat() * rightBound);
    }
}
