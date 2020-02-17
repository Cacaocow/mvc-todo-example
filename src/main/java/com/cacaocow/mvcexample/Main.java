package com.cacaocow.mvcexample;

import com.cacaocow.mvcexample.controller.TodoListController;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        LOG.info("Starting MVC-Todo Example");
        var controller = new TodoListController();
        controller.init();
        controller.showView(true);
    }
}
