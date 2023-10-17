var CalculDistance = function () {
	const R = 6378.137;

	this.degreesToRadians = function(degrees) {
		return Math.PI * degrees / 180;
	}

	this.calculDistance2PointsGPS = function(lat1, long1, lat2, long2) {
		lat1 = this.degreesToRadians(lat1);
		long1 = this.degreesToRadians(long1);
		lat2 = this.degreesToRadians(lat2);
		long2 = this.degreesToRadians(long2);

		let d = R * Math.acos(Math.sin(lat2) * Math.sin(lat1) + Math.cos(lat2) * Math.cos(lat1) * Math.cos(long2 - long1));
		return d;
	}

	this.calculDistanceTrajet = function(activityObject) {
		let data = activityObject.data;
		let totalDistance = 0;

		for (let i = 0; i < data.length - 1; i++) {
			let currentPoint = data[i];
			let nextPoint = data[i + 1];
			
			totalDistance += this.calculDistance2PointsGPS(currentPoint.latitude, currentPoint.longitude, nextPoint.latitude, nextPoint.longitude);
		}

		return totalDistance;
	}
};

var calculDistance = new CalculDistance();
module.exports = calculDistance;
