import React from 'react';
import ReactDOM from 'react-dom';
import Main from './components/Main';
import '../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './../node_modules/jquery/dist/jquery.min.js';
//import './../freelancer-theme/vendor/bootstrap/css/bootstrap.min.css';
import './../freelancer-theme/vendor/font-awesome/css/font-awesome.min.css';
import './../freelancer-theme/css/freelancer.css';
//import jQuery from './../freelancer-theme/js/freelancer.js';
//export default jQuery;
//import './../freelancer-theme/js/freelancer.js';
//import jQuery from 'jquery';
//window.jQuery = jQuery;





ReactDOM.render(
    <Main source="http://pokeapi.co/api/v2/pokemon/" />,
    document.getElementById('root')
);
/*<img src={path.sprites.front_default} class="img-responsive" alt={this.state.pokemons[0].name}></img>*/
