/**
 * Created by claudia on 20/07/17.
 * The index fills the `index.html` page with content from `App.js`.
 * It also imports a router plugin to deal with sub-pages and their paths.
 * See https://medium.com/@pshrmn/a-simple-react-router-v4-tutorial-7f23ff27adf
 */
import React from 'react';
import ReactDOM from 'react-dom';

import './../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './../node_modules/jquery/dist/jquery.min.js';
import './../node_modules/font-awesome/css/font-awesome.min.css';
import './styles/css/freelancer.css';
import './styles/css/colors.css';
import './styles/css/pokecards.css';


import App from './components/App';

ReactDOM.render(
    <App />,
    document.getElementById('root')
);