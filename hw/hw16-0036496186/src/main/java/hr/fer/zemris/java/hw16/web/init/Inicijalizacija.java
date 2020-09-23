package hr.fer.zemris.java.hw16.web.init;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw16.model.DataBase;
import hr.fer.zemris.java.hw16.model.Image;

@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			Path path = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt"));
			List<String> lines=Files.readAllLines(path);
			if(lines.size()%3==0) {
				int max=lines.size()/3;
				for(int i=0;i<max;i++) {
					DataBase.put(new Image(lines.get(3*i).trim(),
							lines.get(3*i+1).trim(),
							lines.get(3*i+2).split("\\s*,\\s*")));
				}
			}else throw new RuntimeException("Invalid database");
		} catch (Exception e) {
			throw new RuntimeException("Couldn't initialize database", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
