package com.example.demo.controllers;

import com.example.demo.model.*;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class TestTaskController {
    @Autowired
    private UserRepository userRepository;
    EnterUser enterUser = new EnterUser();
    @Autowired
    private RolesUserRepository rolesUserRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private ApplicationStatusRepository applicationStatusRepository;

    @GetMapping("/authorization")
    public String authorization(Model model) {
        if (enterUser.getLogin() == null) {
            Iterable<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            model.addAttribute("title", "Вход");
            return "home";
        } else {
            return home(model);
        }
    }

    @PostMapping("/request/{id}")
    public String answer(@PathVariable(value = "id") Long id, @RequestParam ApplicationStatus status, Model model) {
        if (enterUser.getLogin() != null) {
            boolean c = false;
            for (int i = 0; i < enterUser.getRights().length; i++) {
                if (enterUser.getRights()[i] == 2) {
                    c = true;
                    break;
                }
            }
            if (c) {
                Orders order = ordersRepository.findById(id).orElseThrow(RuntimeException::new);
                order.setStatus(status);
                ordersRepository.save(order);
                return viewRequests(model);
            } else
                return "Ошибка";
        } else
            return authorization(model);
    }

    @PostMapping("/draftRequest/{id}/edit")
    public String editRequest(@PathVariable(value = "id") Long id, @RequestParam String request, @RequestParam ApplicationStatus status, Model model) {
        if (enterUser.getLogin() != null) {
            boolean c = false;
            for (int i = 0; i < enterUser.getRights().length; i++) {
                if (enterUser.getRights()[i] == 1) {
                    c = true;
                    break;
                }
            }
            if (c) {
                Orders order = ordersRepository.findById(id).orElseThrow(RuntimeException::new);
                order.setRequest(request);
                order.setStatus(status);
                ordersRepository.save(order);
                return draftRequest(model);
            } else
                return "Ошибка";
        } else
            return authorization(model);
    }

    @GetMapping("/draftRequest/{id}/edit")
    public String editRequest(@PathVariable(value = "id") Long id, Model model) {
        if (enterUser.getLogin() != null) {
            boolean c = false;
            for (int i = 0; i < enterUser.getRights().length; i++) {
                if (enterUser.getRights()[i] == 1) {
                    c = true;
                    break;
                }
            }
            if (c) {
                Optional<Orders> orders = ordersRepository.findById(id);
                model.addAttribute("Request", orders);
                return "editRequest";
            } else
                return "Ошибка";
        } else
            return authorization(model);
    }

    @GetMapping("/request/{id}/considered")
    public String detailedConsideredRequest(@PathVariable(value = "id") Long id, Model model) {
        if (enterUser.getLogin() != null) {
            boolean c = false;
            for (int i = 0; i < enterUser.getRights().length; i++) {
                if (enterUser.getRights()[i] == 1) {
                    c = true;
                    break;
                }
            }
            if (c) {
                Optional<Orders> orders = ordersRepository.findById(id);
                model.addAttribute("Request", orders);
                model.addAttribute("date", getDate(orders.get().getDate()) );
                return "DetailedConsideredRequest";
            } else
                return "Ошибка";
        } else
            return authorization(model);
    }

    @GetMapping("/request/{id}")
    public String detailedRequest(@PathVariable(value = "id") Long id, Model model) {
        if (enterUser.getLogin() != null) {
            boolean c = false;
            for (int i = 0; i < enterUser.getRights().length; i++) {
                if (enterUser.getRights()[i] == 2) {
                    c = true;
                    break;
                }
            }
            if (c) {
                Optional<Orders> orders = ordersRepository.findById(id);
                char[] buildRequest = orders.get().getRequest().toCharArray();
                String stringRequest = "";
                for (int i = 0; i < buildRequest.length; i++) {
                    stringRequest += buildRequest[i];
                    if (i != buildRequest.length - 1)
                        stringRequest += "-";
                }

                model.addAttribute("Request", stringRequest);
                model.addAttribute("date", getDate(orders.get().getDate()) );
                model.addAttribute("id", id);
                return "DetailedRequest";
            } else
                return "Ошибка";
        } else
            return authorization(model);
    }

    @GetMapping("/add")
    public String add(Model model) {
        if (enterUser.getLogin() != null) {
            model.addAttribute("a", enterUser.getLogin());
            return "AddRequst";
        } else
            return authorization(model);
    }

    @GetMapping("/requests")
    public String viewRequests(Model model) {
        if (enterUser.getLogin() != null) {
            boolean c = false;
            for (int i = 0; i < enterUser.getRights().length; i++) {
                if (enterUser.getRights()[i] == 2) {
                    c = true;
                    break;
                }
            }
            if (c) {
                Iterable<Orders> orders = ordersRepository.findAllSend();
                model.addAttribute("requests", orders);
                return "Requests";
            } else
                return "Ошибка";
        } else
            return authorization(model);

    }

    @GetMapping("/draftRequest")
    public String draftRequest(Model model) {
        if (enterUser.getLogin() != null) {
            boolean c = false;
            for (int i = 0; i < enterUser.getRights().length; i++) {
                if (enterUser.getRights()[i] == 1) {
                    c = true;
                    break;
                }
            }
            if (c) {
                Iterable<Orders> orders = ordersRepository.findAllDraft();
                model.addAttribute("requests", orders);
                return "DraftRequests";
            } else
                return "Ошибка";
        } else
            return authorization(model);
    }

    @GetMapping("/consideredRequest")
    public String consideredRequest(Model model) {
        if (enterUser.getLogin() != null) {
            boolean c = false;
            for (int i = 0; i < enterUser.getRights().length; i++) {
                if (enterUser.getRights()[i] == 1) {
                    c = true;
                    break;
                }
            }
            if (c) {
                Iterable<Orders> orders = ordersRepository.findAllNotDraft();
                model.addAttribute("requests", orders);
            } else
                return "Ошибка";
            return "ConsideredRequests";
        } else
            return authorization(model);
    }

    @GetMapping("/listUser")
    public String listUser(Model model) {
        if (enterUser.getLogin() != null) {
            boolean c = false;
            for (int i = 0; i < enterUser.getRights().length; i++) {
                if (enterUser.getRights()[i] == 3) {
                    c = true;
                    break;
                }
            }
            if (c) {
                Iterable<User> users = userRepository.findAll();
                model.addAttribute("user", users);
                return "ListUser";
            } else
                return "Ошибка";

        } else
            return authorization(model);
    }

    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable(value = "id") Long id, Model model) {
        if (enterUser.getLogin() != null) {
            boolean c = false;
            for (int i = 0; i < enterUser.getRights().length; i++) {
                if (enterUser.getRights()[i] == 3) {
                    c = true;
                    break;
                }
            }
            if (c) {

                User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
                Roles roles = rolesRepository.findById(2l).orElseThrow(RuntimeException::new);
                if (rolesUserRepository.findByUser(user, roles) != null)
                    model.addAttribute("checked", "checked");
                else
                    model.addAttribute("checked", "");
                model.addAttribute("user", user);
                return "EditUser";
            } else
                return "Ошибка";

        } else
            return authorization(model);
    }

    @PostMapping("/editUser/{id}")
    public String editUser(@RequestParam(required = false) Roles roles, @PathVariable(value = "id") User id, Model model) {
        if (enterUser.getLogin() != null) {
            boolean c = false;
            for (int i = 0; i < enterUser.getRights().length; i++) {
                if (enterUser.getRights()[i] == 3) {
                    c = true;
                    break;
                }
            }
            if (c) {
                if(roles != null){
                if (rolesUserRepository.findByUser(id, roles) == null) {
                    RolesUser rolesUser = new RolesUser(roles, id);
                    rolesUserRepository.save(rolesUser);
                    return listUser(model);
                } else {
                    return listUser(model);
                }
                }
                else{
                    roles = rolesRepository.findById(2l).orElseThrow(RuntimeException::new);
                    RolesUser rolesUser = rolesUserRepository.findByUser(id, roles);
                    if ( rolesUser != null) {
                        rolesUserRepository.delete(rolesUser);
                        return listUser(model);
                    } else {
                        return listUser(model);
                    }
                }
            } else
                return "Ошибка";

        } else
            return authorization(model);
    }

    @PostMapping("/add")
    public String addreqst(@RequestParam String requst, @RequestParam ApplicationStatus status, Model model) {
        if (enterUser.getLogin() != null) {
            boolean c = false;
            for (int i = 0; i < enterUser.getRights().length; i++) {
                if (enterUser.getRights()[i] == 1) {
                    c = true;
                    break;
                }
            }
            if (c) {
                Optional<User> users = userRepository.findById(enterUser.getLogin());
                Orders order = new Orders(users.get(), requst, status, System.currentTimeMillis());
                ordersRepository.save(order);
                model.addAttribute("a", enterUser.getLogin());
                return home(model);
            } else
                return "Ошибка";
        } else
            return authorization(model);
    }

    @GetMapping("/")
    private String home(Model model) {
        if (enterUser.getLogin() != null) {
            Optional<User> users = userRepository.findById(enterUser.getLogin());
            Iterable<RolesUser> rolesUsers = rolesUserRepository.findByUsers(users.get());
            model.addAttribute("a", rolesUsers);
            HashMap<String, String> actions = new HashMap<String, String>();
            for (RolesUser a : rolesUsers) {
                if (a.getRights().getId() == 1) {
                    actions.put("/add", "Оставить заявку");
                    actions.put("/consideredRequest", "Рассмотренные заявки");
                    actions.put("/draftRequest", "Черновики");
                } else if (a.getRights().getId() == 2) {
                    actions.put("/requests", "Посмотреть заявку");
                } else if (a.getRights().getId() == 3) {
                    actions.put("/listUser", "Смотреть пользователей");
                }
            }
            model.addAttribute("v", actions);
            return "main";
        } else {
            return authorization(model);
        }
    }

    @PostMapping("/")
    private String home(@RequestParam String user, Model model) {
        Optional<User> users = userRepository.findById(user);
        Iterable<RolesUser> rolesUsers = rolesUserRepository.findByUsers(users.get());
        ArrayList<Long> rUser = new ArrayList<>();

        enterUser.setLogin(user);
        for (RolesUser a : rolesUsers) {
            rUser.add(a.getRights().getId());
        }
        Long[] User = new Long[rUser.size()];
        rUser.toArray(User);
        enterUser.setRights(User);
        //response.addCookie(cookie = new Cookie("user", enterUser.getLogin()));
        model.addAttribute("a", rolesUsers);
        HashMap<String, String> actions = new HashMap<String, String>();
        for (RolesUser a : rolesUsers) {
            if (a.getRights().getId() == 1) {
                actions.put("add", "Оставить заявку");
                actions.put("consideredRequest", "Рассмотренные заявки");
                actions.put("draftRequest", "Черновики");
            } else if (a.getRights().getId() == 2) {
                actions.put("requests", "Посмотреть заявку");
            } else if (a.getRights().getId() == 3) {
                actions.put("listUser", "Смотреть пользователей");
            }
        }
        model.addAttribute("v", actions);
        return "main";
    }
    @GetMapping("/exit")
    public String exit(Model model) {
        if(enterUser.getLogin() != null)
            enterUser.clear();
            return authorization(model);

    }
    public String getDate(Long dateLong){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(dateLong);
        return date;
    }
}