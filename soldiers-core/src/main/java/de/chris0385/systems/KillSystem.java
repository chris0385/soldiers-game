package de.chris0385.systems;


import de.chris0385.GameContext;
import de.chris0385.components.ControllableComponent;
import de.chris0385.components.HealthComponent;
import de.slyh.toolkit.entitysystem.Entity;
import de.slyh.toolkit.entitysystem.EntitySystem;

public class KillSystem extends EntitySystem {

	private GameContext ctx;

	public KillSystem(GameContext ctx) {
		super("KillSystem", HealthComponent.class);
		this.ctx = ctx;
		ctx.entitySystemManager.registerSystem(this);
	}


	@Override
	protected void prepareUpdate(float tpf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void update(Entity entity, float tpf) {
		HealthComponent hc = entity.getComponent(HealthComponent.class);
		if (hc.health <= 0) {
			// TODO: Death event
			//entity.getId()
			//entity.getOptionalComponent(ControllableComponent.class).player;
			entity.dispose();
//			entity.addComponent(new DeadBody());
		}
	}

	@Override
	protected void dispose() {
		// TODO Auto-generated method stub
		
	}
}
