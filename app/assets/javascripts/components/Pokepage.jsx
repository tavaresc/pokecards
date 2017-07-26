/**
 * Created by claudia on 18/07/17.
 * Pokepage is responsible for rendering the detailed pokemon page. This page includes a pokecard and a twitter timeline
 * windows. It is called when clicking on a pokemon of the initial page. It sends to pokepage a pokemon object as
 * `props` by the router.
 */

/** Imports useful for ReactJS
import React from 'react';
import Pokecard from './../utils/Pokecard';
*/
//import {autobind} from 'core-decorators';

//@autobind
class Pokepage extends React.Component {
      render(){
        //console.log("Pokecard Props: " + this.state.pokemon.name);

        // `window.location.pathname` javascript command to get current url without `http://`
        // TODO: in ReactJS, this corresponds to props and is set by the router
        var path_elements = window.location.pathname.split("/"); //try this.props.location
        console.log(path_elements);
        // get the main url, until the first `/`
        var name =  path_elements[path_elements.length - 1];
        var pk = {
          url: "/pokemons/stats/" + name,
          name: name
        };

        return (
            <section id="pokecard">
                <div className="container">
                    <div className="row justify-content-center align-items-center">
                        <div className="col-xs-12 col-sm-7 pokecard-item border-gray">
                            <Pokecard pokemon={pk}/>
                        </div>
                        <div className="col-xs-12 col-sm-5 pokecard-item">
                            <Tweets pokemon={pk}/>
                        </div>
                    </div>
                </div>
            </section>
        )
    }
}

/**
 * This code comes originally from index.js (in ReactJS)
 */
ReactDOM.render(
    <Pokepage />,
    document.getElementById('root')
);

//export default Pokepage;