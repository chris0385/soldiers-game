// JS format temporär, wegen IDE, um Kommentare rein machen zu können.
const CONFIG=
[ 
	{
		"name" : "grenadier",
		"components" : {
			"health" : {
				"maxHealth" : 100,
				"health" : 100
			},
			"unit" : {
				// No default. TODO: properties should be automatically reset to null 
			},
			"location": {},
			"physics": {
				"curVelocity" : 0
			},
			"shooting" : {
				// TODO
			},
		}
	} 
]


DEPRECATED={
	"grenadier": {
		"velocity": 5,
		"radius": 1,  // Circles? Makes calculations easier (no orientation)
		"weapon": {
			"motion": "ballistic", // Model for trajectory: ballistic => takes time, flies over soldiers in the way
			"velocity": 15,        // param of ballistic model
			"explode-radius": 3,   // Damage calculation for exploding ammunition
			"explode-strength": 3,
			"cooldown": 1          // time to reload
		},
		"build-time" : 20, // for factory
	},
	"gunner": {
		"velocity": 5,
		"radius": 1,
		"weapon": {
			"motion": "instant",  // Instant trajectory
			"damage": 5,   // Impact damage
			"cooldown": 7
		},
		"build-time" : 20, 
	},
	"tank-light": {
		"velocity": 5,
		"radius": 5,
		"weapon": {
			"motion": "straight",  // Shoots straight, but "slow" bullet. 
			"velocity": 25,        // param of straight shoot model
			"damage": 20,          // Impact damage       
			"explode-radius": 3,   // Explosion damage (can use together with impact damage)
			"explode-strength": 5,
			"cooldown": 5
		},
		"build-time" : 60, // for factory
	}

}