package com.nevdev.witcher.controller;

import com.nevdev.witcher.core.Deal;
import com.nevdev.witcher.core.Reward;
import com.nevdev.witcher.core.User;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.models.DealListViewModel;
import com.nevdev.witcher.models.DealModifyViewModel;
import com.nevdev.witcher.models.DealViewModel;
import com.nevdev.witcher.services.DealService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("deal")
public class DealController {
    private final UserService userService;
    private final DealService dealService;

    @Value("${jwt.header}")
    private String tokenHeader;

    private final JwtTokenUtil jwtTokenUtil;

    private static final
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    public DealController(JwtTokenUtil jwtTokenUtil, DealService dealService, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.dealService = dealService;
        this.userService = userService;
    }

    @RequestMapping(value = "/deals")
    public ResponseEntity<?> deals(@RequestHeader("Authorization") String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        if(user != null){
            List<DealViewModel> deals = new ArrayList<>();
            dealService.findDone(false).forEach((item) -> deals.add(new DealViewModel(item,
                    userService.get(item.getCustomerId()),
                    userService.get(item.getExecutorId()))
            ));
            logger.info("Get all deals");
            return ResponseEntity.ok(deals);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/shop")
    public ResponseEntity<?> shop(@RequestHeader("Authorization") String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        if(user != null){
            List<DealViewModel> deals = new ArrayList<>();
            dealService.findTraderAndDone(true,false).forEach((item) -> deals.add(new DealViewModel(item,
                    userService.get(item.getCustomerId()),
                    userService.get(item.getExecutorId()))
            ));
            logger.info("Get all deals");
            return ResponseEntity.ok(deals);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/workshop")
    public ResponseEntity<?> workshop(@RequestHeader("Authorization") String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        if(user != null){
            List<DealViewModel> deals = new ArrayList<>();
            dealService.findTraderAndDone(false, false).forEach((item) -> deals.add(new DealViewModel(item,
                    userService.get(item.getCustomerId()),
                    userService.get(item.getExecutorId()))
            ));
            logger.info("Get all deals");
            return ResponseEntity.ok(deals);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/accepted-deals")
    public ResponseEntity<?> acceptedDeals(@RequestHeader("Authorization") String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        if(user != null){
            List<DealViewModel> activeDeals = new ArrayList<>();
            dealService.getActiveDeals(user.getId()).forEach((item) -> activeDeals.add(new DealViewModel(item,
                    userService.get(item.getCustomerId()),
                    userService.get(item.getExecutorId()))
            ));
            List<DealViewModel> successDeals = new ArrayList<>();
            dealService.getSuccessHistoryDeals(user.getId()).forEach((item) -> successDeals.add(new DealViewModel(item,
                    userService.get(item.getCustomerId()),
                    userService.get(item.getExecutorId()))
            ));
            List<DealViewModel> bookmarkedDeals = new ArrayList<>();
            dealService.getBookmarkedHistoryDeals(user.getId()).forEach((item) -> bookmarkedDeals.add(new DealViewModel(item,
                    userService.get(item.getCustomerId()),
                    userService.get(item.getExecutorId()))
            ));
            DealListViewModel deals = new DealListViewModel(activeDeals, successDeals, bookmarkedDeals);

            logger.info("Get all accepted deals");
            return ResponseEntity.ok(deals);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/customer-deals")
    public ResponseEntity<?> customerDeals(@RequestHeader("Authorization") String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        if(user != null){
            List<DealViewModel> allDeals = new ArrayList<>();
            dealService.getCustomerDeals(user.getId()).forEach((item) -> allDeals.add(new DealViewModel(item,
                    userService.get(item.getCustomerId()),
                    userService.get(item.getExecutorId()))
            ));
            List<DealViewModel> activeDeals = new ArrayList<>();
            allDeals.stream().filter(item -> !item.getDone()).forEach(activeDeals::add);
            List<DealViewModel> doneDeals = new ArrayList<>();
            allDeals.stream().filter(item -> item.getExecutors().size() > 0).forEach(doneDeals::add);
            List<DealViewModel> historyDeals = new ArrayList<>();
            allDeals.stream().filter(Deal::getDone).forEach(historyDeals::add);

            DealListViewModel deals = new DealListViewModel(activeDeals, doneDeals, historyDeals);

            logger.info("Get all customer deals");
            return ResponseEntity.ok(deals);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody DealModifyViewModel dealRequest, @RequestHeader("Authorization") String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        if (user != null) {
            Deal deal = mapModifyTask(dealRequest, user.getId(), user.getRole(), new Reward());
            Deal created = dealService.create(deal);
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/details/{id}")
                    .buildAndExpand(created.getId())
                    .toUri();
            logger.info(String.format("Created deal on %s", uri));
            return ResponseEntity.created(uri).body(created);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/details/{id}")
    public ResponseEntity<?> details(HttpServletRequest request, @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Deal deal = dealService.get(id);
        if(user != null && deal != null){
            logger.info("Get deal");
            return ResponseEntity.ok(new DealViewModel(deal, userService.get(deal.getCustomerId()),
                    userService.get(deal.getExecutorId())));
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/accept/{id}")
    public ResponseEntity<?> accept(HttpServletRequest request,  @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Deal deal = dealService.get(id);
        if(user != null && deal != null){
            deal.getExecutorsBookmarked().add(user);
            deal = dealService.edit(deal);

            logger.info("Accept deal");
            return ResponseEntity.ok(new DealViewModel(deal,
                    userService.get(deal.getCustomerId()),
                    userService.get(deal.getExecutorId()))
            );
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/cancel/{id}")
    public ResponseEntity<?> cancel(HttpServletRequest request,  @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Deal deal = dealService.get(id);
        if(user != null && deal != null){
            deal.getExecutors().remove(user);
            deal.getExecutorsBookmarked().remove(user);
            deal = dealService.edit(deal);
            logger.info("Cancel deal");
            return ResponseEntity.ok(new DealViewModel(deal,
                    userService.get(deal.getCustomerId()),
                    userService.get(deal.getExecutorId())));
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/complete/{id}")
    public ResponseEntity<?> complete(HttpServletRequest request,  @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Deal deal = dealService.get(id);
        if(user != null && deal != null){
            if(deal.getExecutorsBookmarked().remove(user)){
                deal.getExecutors().add(user);
            }
            else{
                deal.getExecutors().remove(user);
                deal.getExecutorsBookmarked().add(user);
            }
            deal = dealService.edit(deal);
            logger.info("Complete deal");
            return ResponseEntity.ok(deal);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/reward/{id}")
    public ResponseEntity<?> reward(HttpServletRequest request, @RequestBody long userID, @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Deal deal = dealService.get(id);
        User witcher = userService.get(userID);
        if(user != null && deal != null && witcher != null){
            dealService.acceptDeal(user, witcher, deal.getReward());
            deal.setDone(true);
            deal.setExecutorId(witcher.getId());
            deal.setCompletionOn(new Date(System.currentTimeMillis()));
            deal.getExecutors().remove(witcher);
            deal.getExecutorsBookmarked().addAll(deal.getExecutors());
            deal.setExecutors(null);
            deal.setPaid(true);

            deal = dealService.edit(deal);
            logger.info("Reward deal");
            return ResponseEntity.ok(deal);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/refuse/{id}")
    public ResponseEntity<?> refuse(HttpServletRequest request, @RequestBody long userID, @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Deal deal = dealService.get(id);
        User witcher = userService.get(userID);
        if(user != null && deal != null && witcher != null){
            deal.getExecutors().remove(witcher);
            deal.getExecutorsBookmarked().add(witcher);
            deal = dealService.edit(deal);
            logger.info("Refuse deal");
            return ResponseEntity.ok(deal);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(HttpServletRequest request,  @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Deal deal = dealService.get(id);
        if(user != null && deal != null){
            if(user.getRole() == Role.KING || user.getId().equals(deal.getCustomerId())){
                dealService.delete(deal);
                logger.info("Delete deal");
                return ResponseEntity.ok(deal);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/edit/{id}")
    public ResponseEntity<?> edit(HttpServletRequest request, @RequestBody DealModifyViewModel deal, @PathVariable long id){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.find(username);
        Deal oldDeal = dealService.get(id);
        if(user != null && deal != null){
            if(user.getRole() == Role.KING || user.getId().equals(oldDeal.getCustomerId())){
                Deal modifiedDeal = mapModifyTask(deal, oldDeal.getCustomerId(), user.getRole(), oldDeal.getReward());

                oldDeal.setTitle(modifiedDeal.getTitle());
                oldDeal.setTitle(modifiedDeal.getDescription());
                oldDeal.setReward(modifiedDeal.getReward());

                Deal updatedDeal = dealService.edit(oldDeal);
                Deal updated = dealService.edit(updatedDeal);

                URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/details/{id}")
                        .buildAndExpand(updated.getId())
                        .toUri();
                logger.info(String.format("Updated deal on %s", ServletUriComponentsBuilder
                        .fromCurrentRequest()));
                return ResponseEntity.created(uri).body(updated);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    private Deal mapModifyTask(DealModifyViewModel deal, Long customerId, Role role, Reward reward){
        switch (deal.getCheckedCurrency()){
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
        boolean isSale;
        switch (deal.getCheckedType()){
            case "Продажа":
            case "Ремонт":
                isSale = false;
                break;
            default:
                isSale = true;
                break;
        }
        boolean isTrader = role == Role.VENDOR;
        reward.setReward(deal.getCheckedRewardValue());
        return new Deal(deal.getTitle(), deal.getDescription(), isTrader, isSale, reward, customerId);
    }
}
