package de.chris0385.lobby;

import java.util.concurrent.Future;

import de.chris0385.api.model.World;

public interface ClientUpdateListener {

	Future<Boolean> worldUpdate(World world);

}
