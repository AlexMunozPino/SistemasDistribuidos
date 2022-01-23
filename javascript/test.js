const kafka = require('kafka-node')
const axios = require('axios')

const client = new kafka.KafkaClient({kafkaHost: '127.0.0.1:9092'});

function getParameters(response) {
	let magnitude = response.properties.mag
	let latitude = response.geometry.coordinates[0]
	let longitude = response.geometry.coordinates[1]
	let depth = response.geometry.coordinates[2]
	return [parseInt(magnitude), parseInt(latitude), parseInt(longitude), parseInt(depth)]
}

function createObjects(response) {
	let magnitude = 0
	let latitude = 0
	let longitude = 0
	let depth = 0
	let object = []
	response.features.forEach(element => {
		[magnitude, latitude, longitude, depth] = getParameters(element)
		object.push({
			"longitude": longitude,
			"latitude": latitude,
			"magnitude": magnitude,
			"depth": depth
		})
	})
	producer.send([ { topic : "consultas", messages: JSON.stringify(object) } ], function    (err, data) {})
}

var producer = new kafka.Producer(client);

axios.get('https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02')
	.then(response => {
		createObjects(response.data)
	})
