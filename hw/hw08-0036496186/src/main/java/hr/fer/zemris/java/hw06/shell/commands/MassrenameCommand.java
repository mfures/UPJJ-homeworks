package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.FilterResult;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;
import hr.fer.zemris.java.hw06.shell.parsing.ArgumentParser;
import hr.fer.zemris.java.hw06.shell.parsing.NameBuilder;
import hr.fer.zemris.java.hw06.shell.parsing.NameBuilderParser;

public class MassrenameCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Objects.requireNonNull(env);
		Objects.requireNonNull(arguments);

		Path source, dest;
		String pattern;

		try {
			ArgumentParser parser = new ArgumentParser(arguments);
			if (parser.numOfArgs() < 4) {
				env.writeln("Expected atleast 4 arguments, " + parser.numOfArgs() + " were given.");
				return ShellStatus.CONTINUE;
			}

			source = env.getCurrentDirectory().resolve(parser.get(0));
			source = Utility.getPathIfDirectory(source.toString());
			dest = env.getCurrentDirectory().resolve(parser.get(1));
			dest = Utility.getPathIfDirectory(dest.toString());
			pattern = parser.get(3);
			
			switch(parser.get(2)){
			case "filter":
				if(parser.numOfArgs()>4) {
					env.writeln("Expected atmost 4 arguments, " + parser.numOfArgs() + " were given.");
					return ShellStatus.CONTINUE;
				}
				
				try {
					for(FilterResult r:FilterResult.filter(source, pattern)) {
						env.writeln(r.toString());
					}
				} catch (Exception e) {
					env.writeln("Couldn't filter results");
				}
				return ShellStatus.CONTINUE;
			case "groups":				
				if(parser.numOfArgs()>4) {
					env.writeln("Expected atmost 4 arguments, " + parser.numOfArgs() + " were given.");
					return ShellStatus.CONTINUE;
				}
				
				StringBuilder sb=new StringBuilder();
				try {
					for(FilterResult r:FilterResult.filter(source, pattern)) {
						sb.append(r.toString());
						
						for(int i=0;i<r.numberOfGroups();i++) {
							sb.append(" "+i+": "+r.group(i));
						}
						
						env.writeln(sb.toString());
						sb.setLength(0);
					}
				} catch (Exception e) {
					env.writeln("Couldn't filter results");
				}
				return ShellStatus.CONTINUE;
			case "show":
				if(parser.numOfArgs()!=5) {
					env.writeln("Expected atleast 5 arguments, " + parser.numOfArgs() + " were given.");
					return ShellStatus.CONTINUE;
				}
				
				NameBuilder nb;
				
				try {
					nb=new NameBuilderParser(parser.get(4)).getNameBuilder();
				} catch (Exception e) {
					env.writeln(e.getMessage());
					return ShellStatus.CONTINUE;
				}
				
				StringBuilder sbShow=new StringBuilder();
				
				try {
					for(FilterResult r:FilterResult.filter(source, pattern)) {
						nb.execute(r, sbShow);
						env.writeln(r.toString()+" => "+sbShow.toString());
						sbShow.setLength(0);
					}
				} catch (Exception e) {
					
					env.writeln("Couldn't filter aresults");
				}
				
				return ShellStatus.CONTINUE;
			case "execute":
				if(parser.numOfArgs()!=5) {
					env.writeln("Expected atleast 5 arguments, " + parser.numOfArgs() + " were given.");
					return ShellStatus.CONTINUE;
				}
				
				NameBuilder nbExec;
				
				try {
					nbExec=new NameBuilderParser(parser.get(4)).getNameBuilder();
				} catch (Exception e) {
					env.writeln(e.getMessage());
					return ShellStatus.CONTINUE;
				}
				
				StringBuilder sbExec=new StringBuilder();
				
				try {
					for(FilterResult r:FilterResult.filter(source, pattern)) {
						nbExec.execute(r, sbExec);
						Files.move(source.resolve(r.toString()), dest.resolve(sbExec.toString()));
						env.writeln(source.getFileName().toString()+"/"+r.toString()+" => "+dest.getFileName()+"/"+sbExec.toString());

						sbExec.setLength(0);
					}
				} catch (Exception e) {
					env.writeln("Couldn't filter results");
				}
				return ShellStatus.CONTINUE;

				default:
					env.writeln("Unsupported operation: "+parser.get(2));
			}
		} catch (Exception e) {
			env.writeln("Couldn't resolve given path.");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command massrename can be run with atleast four arguments");
		list.add("First argument must be path to source directory");
		list.add("Second argument must be path to destination directory");
		list.add("Third argument must be path to type of operation directory");
		list.add("Fourth argument must be pattern");
		list.add("If third argument is filter, command writes all files that match it in source dir");
		list.add("If third argument is groups, command writes all files that match it in source dir in grouped way");
		list.add("If third argument is show or execute command needs extra argument which is pattern for renaming purpose");
		list.add("If command is show, names are writen if they were renamed");
		list.add("If command is execute, files are moved with given names");


		return list;
	}

}
