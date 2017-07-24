/**
 * Created by claudia on 18/07/17.
 * Pokepage is responsible for rendering the detailed pokemon page. This page includes a pokecard and a twitter timeline
 * windows. It is called when clicking on a pokemon of the initial page. It sends to pokepage a pokemon object as
 * `props` by the router.
 */


import React from 'react';
import Pokecard from './../utils/Pokecard';
//import {autobind} from 'core-decorators';

//@autobind
class Pokepage extends React.Component {
      render(){
        //console.log("Pokecard Props: " + this.state.pokemon.name);

        var pk = {
            url: "http://pokeapi.co/api/v2/pokemon/" + this.props.match.params.name,
            name: this.props.match.params.name,
        };
        return (
            <section id="pokecard">
                <div className="container">
                    <div className="row justify-content-center align-items-center">
                        <div className="col-xs-12 col-sm-7 pokecard-item border-gray">
                            <Pokecard pokemon={pk}/>
                        </div>
                        <div className="col-xs-12 col-sm-5 pokecard-item">
                            <img className="img-responsive" src="./img/timeline-twitter.png"
                                alt="Printscreen of Twitter timeline"/>
                        </div>
                    </div>
                </div>
            </section>
        )
    }
}

export default Pokepage;