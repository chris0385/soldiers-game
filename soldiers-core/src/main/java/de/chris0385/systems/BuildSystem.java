package de.chris0385.systems;


import com.github.antag99.retinazer.EntityProcessorSystem;
import com.github.antag99.retinazer.FamilyConfig;
import com.github.antag99.retinazer.Mapper;

import de.chris0385.components.BuildingComponent;
import de.chris0385.components.HealthComponent;
import de.chris0385.game.GameContext;

public class BuildSystem extends EntityProcessorSystem {

	private GameContext gameContext;
	private Mapper<BuildingComponent> buildingMapper;

	public BuildSystem(GameContext gameContext) {
		super(new FamilyConfig().with(BuildingComponent.class));
		this.gameContext = gameContext;
		buildingMapper = engine.getMapper(BuildingComponent.class);
	}

	@Override
	protected void process(int entity) {
		BuildingComponent bc = buildingMapper.get(entity);
		if (bc.typeBuilt == null) {
			// TODO New conquest: init
			
			return;
		}
		if (changeBuildtype(bc)) {
			// TODO: Reset build, change built type
			
		}
		if (buildingReady(bc)) {
			// TODO spawn
			//gameContext.entityFactory.createSoldier();
			
		}
		if (sectorCountChanged()) {
			// TODO: update build time
		}
	}

	private boolean changeBuildtype(BuildingComponent bc) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return true if sector count changed (may be because of player change)
	 */
	private boolean sectorCountChanged() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean buildingReady(BuildingComponent bc) {
		return bc.timeWhenBuildDone < gameContext.gameTime();
	}

}
