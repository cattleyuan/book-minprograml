package com.ab.puzzle.controller.user;

import com.ab.dcomment.service.DynamicCommentService;
import com.ab.dynamic.service.DynamicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DynamicCommentControllerTest {
    @Autowired
    private DynamicCommentService dynamicCommentService;
    @Autowired
    private DynamicService dynamicService;
    @Test
    void getNumberOfDComment() {
        dynamicCommentService.getNumberOfDComment(2L);
    }
    @Test
    void get (){
        dynamicService.getDynamicCTNumber(2L);
    }

}