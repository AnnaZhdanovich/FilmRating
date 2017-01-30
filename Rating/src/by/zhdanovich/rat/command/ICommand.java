package by.zhdanovich.rat.command;

import javax.servlet.http.HttpServletRequest;
import by.zhdanovich.rat.controller.util.Carrier;
import by.zhdanovich.rat.command.exception.CommandException;

public interface ICommand {
	void  execute(HttpServletRequest request, Carrier carrier) throws CommandException;
}
