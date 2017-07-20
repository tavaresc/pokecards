/**
 * Created by claudia on 20/07/17.
 * The Main component renders one of the provided Routes (provided that one matches).
 * The `/about` route will match any pathname that starts with `/about`.
 * The `/` route will only match when the pathname is exactly the string `/`.
 */
import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Home from '../../components/Home';
import About from '../../components/About';


const Main = () => (
    <main>
        <Router>
            <Switch>
                <div>
                <Route exact path='/' component={Home}/>
                <Route path='/about' component={About}/>
                </div>
            </Switch>
        </Router>
    </main>
);

export default Main;