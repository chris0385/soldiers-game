package de.chris0385.systems;


import com.github.antag99.retinazer.EntityProcessorSystem;
import com.github.antag99.retinazer.FamilyConfig;

import de.chris0385.components.HealthComponent;

public class KillSystem extends EntityProcessorSystem {

	public KillSystem() {
		super(new FamilyConfig().with(HealthComponent.class));
	}

	@Override
	protected void process(int entity) {
		// TODO Auto-generated method stub
		HealthComponent hc = engine.getMapper(HealthComponent.class).get(entity);
		if (hc.health <= 0) {
			engine.destroyEntity(entity);
		}
	}

}
