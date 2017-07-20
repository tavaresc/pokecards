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

import About from './About';
import Home from './Home';

const Main = () => (
        <Router>
            <div>
                {/* Navigation */}
                <nav id="mainNav" className="navbar navbar-default navbar-fixed-top navbar-custom">
                    <div className="container">
                        {/* Brand and toggle get grouped for better mobile display */}
                        <div className="navbar-header page-scroll">
                            <button type="button" className="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                                <span className="sr-only">Toggle navigation</span> Menu <i className="fa fa-bars"></i>
                            </button>
                            <Link className="navbar-brand" to='/'>Pokecards</Link>
                        </div>

                        {/* Collect the nav links, forms, and other content for toggling */}
                        <div className="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul className="nav navbar-nav navbar-right">
                                <li className="page-scroll">
                                    <Link to='/'>Portfolio</Link>
                                </li>
                                <li className="page-scroll">
                                    <Link to='/about'>About</Link>
                                </li>

                                <li className="page-scroll todo">Search bar</li>
                            </ul>
                        </div>
                        {/* end of navbar-collapse */}
                    </div>
                    {/* end of container-fluid */}
                </nav>
                <Switch>
                        <Route exact path='/' component={Home}/>
                        <Route path='/about' component={About}/>
                </Switch>
            </div>
        </Router>
);

export default Main;