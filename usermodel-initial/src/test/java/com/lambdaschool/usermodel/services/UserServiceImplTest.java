package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        List<User> myList = userService.findAll();
        for (User u : myList)
        {
            System.out.println(u.getUserid() + " " + u.getUsername());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = ResourceNotFoundException.class)
    public void a_findUserByIdNotFound() {
        assertEquals("test admin",userService.findUserById(5000).getUsername());
    }

    @Test
    public void aa_findUserById() {
        assertEquals("test admin",userService.findUserById(4).getUsername());
    }

    @Test
    public void b_findByNameContaining() {
        assertEquals(1,userService.findByNameContaining("barn").size());
    }

    @Test
    public void c_findAll() {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void g_delete() {
        userService.delete(4);
        assertEquals(5,userService.findAll().size());
    }

    @Test
    public void d_findByName() {
        assertEquals("test cinnamon",
                userService.findByName("test cinnamon").getUsername());
    }
    @Test(expected = ResourceNotFoundException.class)
    public void dd_findByNameNotFound() {
        assertEquals("",
                userService.findByName("test dog").getUsername());
    }

    @Test
    public void e_save() {
        Role r2 = new Role("user");
        r2.setRoleid(2);
        String user4Name = "test dog not not";
        User u4 = new User(user4Name,
                "drowssap",
                "hello@school.lambda");
        u4.getRoles()
                .add(new UserRoles(u4, r2));
        User addUser =  userService.save(u4);
        assertNotNull(addUser);
        assertEquals(user4Name,addUser.getUsername());
    }
    @Test
    public void efsaveput() {
        Role r2 = new Role("user");
        r2.setRoleid(2);
        String user4Name = "test dog not not";
        User u4 = new User(user4Name,
                "dog1234",
                "dog@school.lambda");
        u4.setUserid(13);

        u4.getRoles().clear();
        u4.getRoles()
                .add(new UserRoles(u4, r2));
        User addUser =  userService.save(u4);

        assertNotNull(addUser);
        assertEquals(user4Name,addUser.getUsername());
    }
    @Test(expected = ResourceNotFoundException.class)
    public void eee_saveputfailed() {

        Role r2 = new Role("dog");
        r2.setRoleid(2);
        String user4Name = "test dog not not";
        User u4 = new User(user4Name,
                "drowssap",
                "hello@school.lambda");
        u4.setUserid(13333);

        u4.getRoles()
                .add(new UserRoles(u4, r2));
        User addUser =  userService.save(u4);
        assertNotNull(addUser);
        assertEquals(user4Name,addUser.getUsername());
    }

    @Test
    public void f_update() {
        Role r2 = new Role("user");
        r2.setRoleid(2);
        String user5Name = "test misskitty";
        User u5 = new User(user5Name,
                "password",
                "misskitty@school.lambda");
        u5.setUserid(14);
        u5.getRoles()
                .add(new UserRoles(u5, r2));
        User addUser = userService.update(u5,14);
        assertNotNull(addUser);
        assertEquals(user5Name,addUser.getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ffg_updateNotFound() {
        Role r2 = new Role("user");
        r2.setRoleid(2);
        String user5Name = "test misskitty";
        User u5 = new User(user5Name,
                "password",
                "misskitty@school.lambda");
        u5.setUserid(400);
        u5.getRoles()
                .add(new UserRoles(u5, r2));
        User addUser = userService.update(u5,400);
        assertNotNull(addUser);
        assertEquals(user5Name,addUser.getUsername());
    }

    @Test
    public void h_deleteAll() {
        userService.deleteAll();
        assertEquals(0,userService.findAll().size());
    }
}