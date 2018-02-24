package de.chris0385.api.commands;

public interface CommandVisitor<R> {

	R visit(MoveCommand moveCommand);

}
