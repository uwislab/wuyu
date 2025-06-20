import Mock from 'mockjs';
Mock.mock("http://49.51.69.99:33380/api/manageAct/getActivityListByPage", "get", {
    "data":{
        "list|11-100": [{
            "activityId|1111-2100": 1200,
            "activityName": Mock.mock('@word(5)'),
            "startTime": Mock.mock('@datetime()'),
            "activityType": Mock.mock('@word(5)'),
            "activityState|0-2": 0,
        }],
        "total|11-100": 21
    },
    "message": 'success',
    "errno": 0
})
