const devServer = config.devServer

// noinspection JSUnresolvedVariable
if (devServer) {
    devServer.hot = true
    devServer.port = 8888
    devServer.watchOptions = {
        aggregateTimeout: 300,
        poll: 300
    };
    devServer.stats = {
        warnings: true
    };
    // config.devServer.clientLogLevel = 'error';
}
