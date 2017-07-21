/**
 * Created by claudia on 20/07/17.
 * The Header creates links that can be used to navigate between routes.
 * See https://medium.com/@pshrmn/a-simple-react-router-v4-tutorial-7f23ff27adf
 * NOTE:
 * Defining links and paths in separated files causes the following problem:
 * - the only way a component could be accessed was by direct typing the URL in the address bar;
 * - when clicked, links changed the URL but did not render the page
 */
import React from 'react';
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';

import './../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './../../node_modules/jquery/dist/jquery.min.js';
import './../../freelancer-theme/vendor/font-awesome/css/font-awesome.min.css';
import './../../freelancer-theme/css/freelancer.css';

import Home from './Home';
import Pokecard from './Pokecard';


const App = () => (
    <Router>
        <div>
            <Switch>
                <Route exact path='/' component={Home}/>
                <Route path='/pokecard' component={Pokecard}/>

            </Switch>
        </div>
    </Router>
);

export default App;