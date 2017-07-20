/**
 * Created by claudia on 18/07/17.
 * Creating a bundler `webpack` to deal with all javascript modules.
 * Tell webpack what is the root component (the `entry`) and the exported config object.
 * See https://medium.com/@harinilabs/day-1-simple-react-app-1251d257ab17
 * See also: https://scotch.io/tutorials/routing-react-apps-the-complete-guide
 * Note: webpackversion 3.3.0 does not work. Downgrade to 1.13.2
 */

module.exports = {
    entry: "./src/index.js",
    output: {
        filename: "./public/bundle.js"
    },
    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                exclude: /(node_modules|bower_components)/,
                loader: 'babel-loader',
                query: {
                    presets: ['react', 'es2015']
                }
            }
        ]
    }
}