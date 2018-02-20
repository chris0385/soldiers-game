// JS format temporär, wegen IDE, um Kommentare rein machen zu können.
const CONFIG=
{
	"units": {
		"grenadier": {
			"velocity": 5,
			"radius": 1,  // Circles? Makes calculations easier (no orientation)
			"weapon": {
				"motion": "ballistic", // Model for trajectory: ballistic => takes time
				"velocity": 15,        // param of ballistic model
				"explode-radius": 3,   // Damage calculation for exploding ammunition
				"explode-strength": 3
			},
			"build-time" : 20, // for factory
		},
		"gunner": {
			"velocity": 5,
			"radius": 1,
			"weapon": {
				"motion": "instant",  // Instant trajectory
				"damage": 5   // Impact damage
			},
			"build-time" : 20, 
		},
		"tank-light": {
			"velocity": 5,
			"radius": 5,
			"weapon": {
				"motion": "instant", 
				"damage": 20,          // Impact damage       
				"explode-radius": 3,   // Explosion damage (can use together with impact damage)
				"explode-strength": 5
			},
			"build-time" : 60, // for factory
		}
	},
	"buildings" : {
		"tank-factory" : {
			"size" : {
				"x" : 2,
				"y" : 3
			},
			"built-units" : [ "tank-light"]
		}
	}
}