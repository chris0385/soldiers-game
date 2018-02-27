package de.chris0385.systems;


import com.github.antag99.retinazer.EntityProcessorSystem;
import com.github.antag99.retinazer.FamilyConfig;
import com.github.antag99.retinazer.Mapper;

import de.chris0385.components.BuildingComponent;
import de.chris0385.components.FlagComponent;
import de.chris0385.components.LocationComponent;
import de.chris0385.components.UnitComponent;
import de.chris0385.game.GameContext;
import de.chris0385.game.GamePlayer;

/**
 * Not threadsafe, single thread use of sytems.
 */
public class ConquestSystem extends EntityProcessorSystem {

	private final GameContext gameContext;
	private final Mapper<LocationComponent> locationMapper;
	private final Mapper<FlagComponent> flagMapper;
	private final Mapper<UnitComponent> unitMapper;
	private final Mapper<BuildingComponent> buildingMapper;
	
	private GamePlayer tmp_player;
	private boolean changed;

	public ConquestSystem(GameContext gameContext) {
		super(new FamilyConfig().with(FlagComponent.class, LocationComponent.class));
		this.gameContext = gameContext;
		locationMapper = engine.getMapper(LocationComponent.class);
		buildingMapper = engine.getMapper(BuildingComponent.class);
		unitMapper = engine.getMapper(UnitComponent.class);
		flagMapper = engine.getMapper(FlagComponent.class);
	}
	
	@Override
	protected void processEntities() {
		this.changed = false;
		super.processEntities();
		if (changed) {
			// TODO: something?
		}
	}

	@Override
	protected void process(int entity) {
		LocationComponent flagLocation = locationMapper.get(entity);
		
		tmp_player = null;
		gameContext.spatialIndex.iterateIntersectingCircle(flagLocation.location, flagLocation.size, (ent) -> {
			UnitComponent unit = unitMapper.get(ent);
			if (unit == null) {
				return;
			}
			
			if (tmp_player != null && !tmp_player.equals(unit.player)) {
				// TODO: what if multiple users on flag?
			}
			tmp_player = unit.player;
		});
		
		if (tmp_player != null) {
			// A flag is a special building
			BuildingComponent flagBuilding = buildingMapper.get(entity);
			GamePlayer previousPlayer = flagBuilding.player;
			if (!tmp_player.equals(previousPlayer)) {
				changePlayer(flagMapper.get(entity), tmp_player);
				changePlayer(flagBuilding, tmp_player);
				changed = true;
				recalculateBuildTimes(tmp_player);
				recalculateBuildTimes(previousPlayer);
			}
		}
	}

	private void recalculateBuildTimes(GamePlayer player) {
		if (player == null) {
			return;
		}
		// TODO Auto-generated method stub
		
	}

	private void changePlayer(BuildingComponent building, GamePlayer newPlayer) {
		// TODO: signal?
		building.player = newPlayer;
	}

	private void changePlayer(FlagComponent flag, GamePlayer newPlayer) {
		for (int b : flag.linkedBuildings) {
			if (b < 0) {
				return;
			}
			changePlayer(buildingMapper.get(b), newPlayer);
		}
	}

}
