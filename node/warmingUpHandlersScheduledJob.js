const https = require('https');

exports.handler = (event, context, callback) => {
    var options = {
        host: 'api.exemplar.ai',
        port: 443,
        method: 'GET',
        headers: {
            'X-Exemplar-Warm-Up': 'true'
        }
    };

    options = Object.assign(options, {
        path: '/analytics/location/method1'
    });

    https.get(options);

    options = Object.assign(options, {
        path: '/analytics/location/method2'
    });

    https.get(options);

    options = Object.assign(options, {
        path: '/analytics/location/method3'
    });

    https.get(options);

    options = Object.assign(options, {
        path: '/analytics/location/method4'
    });

    https.get(options);

    options = Object.assign(options, {
        path: '/analytics/location/method5'
    });

    https.get(options);

    options = Object.assign(options, {
        path: '/locations'
    });

    https.get(options);

    callback(null);
};