const devServer = config.devServer

// noinspection JSUnresolvedVariable
if (devServer) {
    // devServer.hot = true
    devServer.port = 8888
    devServer.overlay = true
    devServer.watchOptions = {
        aggregateTimeout: 500,
        poll: 500
    };
    devServer.stats = {
        warnings: true
    };
    // config.devServer.clientLogLevel = 'error';
}
