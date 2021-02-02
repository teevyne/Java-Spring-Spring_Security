package com.dev.multisource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DSRestController {

    @Autowired
    ProfileServiceDAO profileServiceDAO;

    @Autowired
    TaskServiceDAO taskServiceDAO;

    @RequestMapping(value = "/userscount")
    public String userCountFromProfileService() {
        return "Count from users table is " + profileServiceDAO.getCount_from_users();
    }

    @RequestMapping(value = "/taskscount")
    public String taskCountFromTaskService(){
        return "Count from tasks table is " + taskServiceDAO.getCount_from_tasks();
    }
}
