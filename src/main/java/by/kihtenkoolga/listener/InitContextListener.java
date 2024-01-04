package by.kihtenkoolga.listener;

import by.kihtenkoolga.command.CommandHelper;
import by.kihtenkoolga.config.ApplicationConfig;
import by.kihtenkoolga.util.DbScriptUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
public class InitContextListener implements ServletContextListener {

    public static CommandHelper commandHelper;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);

        ApplicationContext app = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        commandHelper = app.getBean(CommandHelper.class);
        DbScriptUtil dbScriptUtil = app.getBean(DbScriptUtil.class);

        dbScriptUtil.initAndExecuteScriptsByProperties();
    }

}
