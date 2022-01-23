const kafka = require('kafka-node')
const axios = require('axios')


function getParameters(response) {
	let id = response.id
	let magnitude = response.properties.mag
	let latitude = response.geometry.coordinates[0]
	let longitude = response.geometry.coordinates[1]
	let depth = response.geometry.coordinates[2]
	let timestamp = response.properties.time
	let place_r = response.properties.place
	return [id, parseFloat(magnitude), parseFloat(latitude), parseFloat(longitude), parseFloat(depth), parseInt(timestamp)/1000, place_r]
}


function sendObjects(objects) {
	objects.forEach(object => {
		producer.send([ {topic: "earthquakes", messages: JSON.stringify(object)} ], function(err, data) {} )
	})
}


function createObjects(response) {
	let magnitude = 0
	let latitude = 0
	let id = ""
	let longitude = 0
	let depth = 0
	let objects = []
	let timestamp = 0
	let place = ""
	response.features.forEach(element => {
		[id, magnitude, latitude, longitude, depth, timestamp, place] = getParameters(element)
		objects.push({
			"id": id,
			"longitude": longitude,
			"latitude": latitude,
			"magnitude": magnitude,
			"depth": depth,
			"timestamp": timestamp,
			"place": place,
		})
	})
	sendObjects(objects)
}


function addDays(myDate) {
return new Date(myDate.getTime() + 86400000)
}


function createStream() {
	var today = new Date();
	var current_date = null
	const http = 'https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=';
	(function () {
		var callback = function() {
			current_date = new Date(today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate())
			console.log(current_date.toISOString().split('T')[0])
			console.log(addDays(current_date).toISOString().split('T')[0])
			axios.get(http.concat(current_date.toISOString().split('T')[0], "&endtime=", addDays(current_date).toISOString().split('T')[0]))
				.then(response => {
					createObjects(response.data)
				})
		};
		callback();
		setInterval(callback, 60000*0.1);
	})();
}


const client = new kafka.KafkaClient({kafkaHost: 'broker:29092'});
var producer = new kafka.Producer(client);

createStream()
