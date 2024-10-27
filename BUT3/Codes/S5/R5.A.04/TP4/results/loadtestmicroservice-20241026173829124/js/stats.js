var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "1470",
        "ok": "443",
        "ko": "1027"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "4",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "60002",
        "ok": "394",
        "ko": "60002"
    },
    "meanResponseTime": {
        "total": "17432",
        "ok": "9",
        "ko": "24947"
    },
    "standardDeviation": {
        "total": "27237",
        "ok": "23",
        "ko": "29571"
    },
    "percentiles1": {
        "total": "6",
        "ok": "7",
        "ko": "2"
    },
    "percentiles2": {
        "total": "60000",
        "ok": "8",
        "ko": "60000"
    },
    "percentiles3": {
        "total": "60001",
        "ok": "10",
        "ko": "60001"
    },
    "percentiles4": {
        "total": "60001",
        "ok": "93",
        "ko": "60001"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 443,
    "percentage": 30
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
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 1027,
    "percentage": 70
},
    "meanNumberOfRequestsPerSecond": {
        "total": "5.676",
        "ok": "1.71",
        "ko": "3.965"
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
        "total": "735",
        "ok": "253",
        "ko": "482"
    },
    "minResponseTime": {
        "total": "1",
        "ok": "5",
        "ko": "1"
    },
    "maxResponseTime": {
        "total": "60002",
        "ok": "394",
        "ko": "60002"
    },
    "meanResponseTime": {
        "total": "20494",
        "ok": "10",
        "ko": "31246"
    },
    "standardDeviation": {
        "total": "28450",
        "ok": "28",
        "ko": "29974"
    },
    "percentiles1": {
        "total": "7",
        "ok": "7",
        "ko": "60000"
    },
    "percentiles2": {
        "total": "60000",
        "ok": "8",
        "ko": "60000"
    },
    "percentiles3": {
        "total": "60001",
        "ok": "10",
        "ko": "60001"
    },
    "percentiles4": {
        "total": "60001",
        "ok": "92",
        "ko": "60001"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 253,
    "percentage": 34
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
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 482,
    "percentage": 66
},
    "meanNumberOfRequestsPerSecond": {
        "total": "2.838",
        "ok": "0.977",
        "ko": "1.861"
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
        "total": "735",
        "ok": "190",
        "ko": "545"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "4",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "60002",
        "ok": "147",
        "ko": "60002"
    },
    "meanResponseTime": {
        "total": "14370",
        "ok": "8",
        "ko": "19377"
    },
    "standardDeviation": {
        "total": "25604",
        "ok": "14",
        "ko": "28056"
    },
    "percentiles1": {
        "total": "2",
        "ok": "6",
        "ko": "1"
    },
    "percentiles2": {
        "total": "10",
        "ok": "7",
        "ko": "60000"
    },
    "percentiles3": {
        "total": "60001",
        "ok": "9",
        "ko": "60001"
    },
    "percentiles4": {
        "total": "60001",
        "ok": "34",
        "ko": "60001"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 190,
    "percentage": 26
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
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 545,
    "percentage": 74
},
    "meanNumberOfRequestsPerSecond": {
        "total": "2.838",
        "ok": "0.734",
        "ko": "2.104"
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
