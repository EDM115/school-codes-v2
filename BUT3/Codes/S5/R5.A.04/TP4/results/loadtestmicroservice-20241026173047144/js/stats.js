var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "1538",
        "ok": "577",
        "ko": "961"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "3",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "60003",
        "ok": "24843",
        "ko": "60003"
    },
    "meanResponseTime": {
        "total": "26497",
        "ok": "6258",
        "ko": "38648"
    },
    "standardDeviation": {
        "total": "28040",
        "ok": "8118",
        "ko": "28727"
    },
    "percentiles1": {
        "total": "6648",
        "ok": "3044",
        "ko": "60000"
    },
    "percentiles2": {
        "total": "60000",
        "ok": "6685",
        "ko": "60001"
    },
    "percentiles3": {
        "total": "60001",
        "ok": "23088",
        "ko": "60001"
    },
    "percentiles4": {
        "total": "60001",
        "ok": "24634",
        "ko": "60002"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 229,
    "percentage": 15
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t ≥ 800 ms <br> t < 1200 ms",
    "count": 2,
    "percentage": 0
},
    "group3": {
    "name": "t ≥ 1200 ms",
    "htmlName": "t ≥ 1200 ms",
    "count": 346,
    "percentage": 22
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 961,
    "percentage": 62
},
    "meanNumberOfRequestsPerSecond": {
        "total": "5.915",
        "ok": "2.219",
        "ko": "3.696"
    }
},
contents: {
"req_get-all-c386e": {
        type: "REQUEST",
        name: "Get All",
path: "Get All",
pathFormatted: "req_get-all-c386e",
stats: {
    "name": "Get All",
    "numberOfRequests": {
        "total": "769",
        "ok": "292",
        "ko": "477"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "5",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "60002",
        "ok": "24843",
        "ko": "60002"
    },
    "meanResponseTime": {
        "total": "26296",
        "ok": "6375",
        "ko": "38491"
    },
    "standardDeviation": {
        "total": "27982",
        "ok": "8359",
        "ko": "28773"
    },
    "percentiles1": {
        "total": "6824",
        "ok": "2733",
        "ko": "60000"
    },
    "percentiles2": {
        "total": "60000",
        "ok": "7143",
        "ko": "60001"
    },
    "percentiles3": {
        "total": "60001",
        "ok": "22376",
        "ko": "60001"
    },
    "percentiles4": {
        "total": "60001",
        "ok": "24594",
        "ko": "60002"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 131,
    "percentage": 17
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t ≥ 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t ≥ 1200 ms",
    "htmlName": "t ≥ 1200 ms",
    "count": 161,
    "percentage": 21
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 477,
    "percentage": 62
},
    "meanNumberOfRequestsPerSecond": {
        "total": "2.958",
        "ok": "1.123",
        "ko": "1.835"
    }
}
    },"req_get-one-user-31329": {
        type: "REQUEST",
        name: "Get One User",
path: "Get One User",
pathFormatted: "req_get-one-user-31329",
stats: {
    "name": "Get One User",
    "numberOfRequests": {
        "total": "769",
        "ok": "285",
        "ko": "484"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "3",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "60003",
        "ok": "24822",
        "ko": "60003"
    },
    "meanResponseTime": {
        "total": "26697",
        "ok": "6139",
        "ko": "38802"
    },
    "standardDeviation": {
        "total": "28097",
        "ok": "7861",
        "ko": "28680"
    },
    "percentiles1": {
        "total": "6339",
        "ok": "3299",
        "ko": "60000"
    },
    "percentiles2": {
        "total": "60000",
        "ok": "6364",
        "ko": "60001"
    },
    "percentiles3": {
        "total": "60001",
        "ok": "23020",
        "ko": "60001"
    },
    "percentiles4": {
        "total": "60001",
        "ok": "24638",
        "ko": "60002"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 98,
    "percentage": 13
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t ≥ 800 ms <br> t < 1200 ms",
    "count": 2,
    "percentage": 0
},
    "group3": {
    "name": "t ≥ 1200 ms",
    "htmlName": "t ≥ 1200 ms",
    "count": 185,
    "percentage": 24
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 484,
    "percentage": 63
},
    "meanNumberOfRequestsPerSecond": {
        "total": "2.958",
        "ok": "1.096",
        "ko": "1.862"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
