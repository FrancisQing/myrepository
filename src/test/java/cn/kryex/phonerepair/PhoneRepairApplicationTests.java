package cn.kryex.phonerepair;

import cn.kryex.phonerepair.entity.po.User;
import cn.kryex.phonerepair.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PhoneRepairApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("测试");
    }


    /**
     * 做测试
     */
    @Autowired
    private UserService userService;
    @Test
    void testLink(){
        List<User> list = userService.list();
        for (User user : list) {
            System.out.println(user);
        }
    }

}
