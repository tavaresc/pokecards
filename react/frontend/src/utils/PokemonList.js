/**
 * Created by claudia on 22/07/17.
 * PokemonList creates a list of pokemon models and render information according to the current webpage:
 * - renderPortfolio(): it renders all pokemons on the initial page
 */


import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import Pokemon from './../utils/Pokemon';

class PokemonList extends Component {
    /* Warning: useless
     constructor(props) {
     super(props);
     }
     */

    renderPortfolio(pk) {
        console.log("PokemonList- props name: " + pk.name);
        console.log("PokemonList- props url: " + pk.url);

        return (
            <div className="col-xs-6 col-sm-3 col-md-2 portfolio-item" key={pk.name}>
                <Link to={`/${pk.name}`} className="portfolio-link">
                    <div className="caption">
                        <div className="caption-content">
                            <i className="fa fa-search-plus fa-3x"></i>
                        </div>
                    </div>
                    <Pokemon key={pk.name} pokemon={pk} />
                </Link>
                <div className="name"><h3>{pk.name}</h3></div>
                <div className="todo"><a href="#">Like   Dislike</a></div>
            </div>
        );
    }

    render() {
        return (
            <div>
                {(this.props.pokemons.slice(0,6)).map(pk => this.renderPortfolio(pk))}
            </div>
        )
    }
};

export default PokemonList;