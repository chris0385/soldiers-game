package de.chris0385.systems;


import com.github.antag99.retinazer.EntityProcessorSystem;
import com.github.antag99.retinazer.FamilyConfig;
import com.github.antag99.retinazer.Mapper;

import de.chris0385.components.FlagComponent;
import de.chris0385.components.LocationComponent;
import de.chris0385.game.GameContext;

public class ConquestSystem extends EntityProcessorSystem {

	private GameContext gameContext;
	private Mapper<LocationComponent> locationMapper;
	private Mapper<FlagComponent> flagMapper;

	public ConquestSystem(GameContext gameContext) {
		super(new FamilyConfig().with(FlagComponent.class, LocationComponent.class));
		this.gameContext = gameContext;
		locationMapper = engine.getMapper(LocationComponent.class);
		flagMapper = engine.getMapper(FlagComponent.class);
	}

	@Override
	protected void process(int entity) {
		// There are a few flags, but many units.
		LocationComponent flagLocation = locationMapper.get(entity);
		
		// TODO: fast way to query close units.
	}

}
