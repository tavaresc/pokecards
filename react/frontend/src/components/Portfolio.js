/**
 * Created by claudia on 21/07/17.
 * Portfolio display each pokemon item of the portfolio on the initial page.
 */

import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class Pokemon extends Component {
    constructor(props) {
        super(props);

        this.state = {
            sprite: "",
            types: [{url: "", name: ""}],
        };
    }

    componentDidMount() {
        var axios = require('axios');

        this.serverRequest = axios.get(this.props.pokemon.url)
            .then(result => {
                console.log("Server: " + result.data.types.map(ty => ty.type));
                this.setState({
                    types: result.data.types.map(ty => ty.type),
                    // NOT sure it works
                    sprite: result.data.sprites.front_default,
                });
            });
    }

    getStyle(ty) {
        // `sprite` is the default CSS class for a sprite
        var color = "sprite ";

        if (ty !== undefined) {
            if (ty.length === 2) // a pokemon can have at most 2 types
                /* we have created 18x18 css class name for each combination of types named `type1-type2` */
                color += (ty[0].name < ty[1].name)? (ty[0].name + "-" + ty[1].name): (ty[1].name + "-" + ty[0].name);
            else // ty.length === 1
                color += ty[0].name;
        }
        return color;
    }

    renderSprite() {
        return (
            <div className="circle">
                <img src={this.state.sprite} className="img-responsive"
                     alt={this.props.pokemon.name + " picture"}/>
            </div>
        );
    }

    render() {
        console.log("Pokemon - sprite: " + this.state.sprite);
        console.log("Pokemon - types: " + this.state.types);
        return (
            <div className={this.getStyle(this.state.types)}>
                {this.renderSprite()}
            </div>
        )
    }
};

class PokemonList extends Component {
    /* Warning: useless
    constructor(props) {
        super(props);
    }
    */

   renderPortfolio(pk) {
        return (
            <div className="col-xs-6 col-sm-3 col-md-2 portfolio-item" key={pk.name}>
                <Link to='/pokecard' className="portfolio-link">
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
                {this.props.pokemons.map(pk => this.renderPortfolio(pk))}
            </div>
        )
    }
};

class Portfolio extends Component {
    constructor() {
        super();
        //TODO: put everything into `pokemons` to keep infos linked by its index
        this.state = {
            pokemons: [],
        };
    }

    componentDidMount() {
        var axios = require('axios');
        var pk_url = "http://pokeapi.co/api/v2/pokemon/";

        this.serverRequest =
            axios.get(pk_url)
                .then(result => {
                    this.setState({
                        pokemons: result.data.results,
                    });
                });
    }

    /* This method throws an Error when a link is called. It is then necessary to click twice to go the the linked page.
     * The error message: `Uncaught TypeError: this.serverRequest.abort is not a function`
     */
    /*
    componentWillUnmount() {
        this.serverRequest.abort();
    } */

    render() {
        console.log("Portfolio: " + this.state.pokemons);

        // pk => <Pokemon key={pk.name} name={pk.name} url={pk.url} />
        return (
            <div>
                <PokemonList pokemons={this.state.pokemons}/>
            </div>
        );
    }
}

export default Portfolio;

// See https://github.com/facebookincubator/create-react-app/blob/master/packages/react-scripts/template/README.md#adding-a-css-preprocessor-sass-less-etc
//import './less/freelancer.less';
/*
 class Test extends React.Component {
 render() {
 return (
 <div className="test">
 <p>Whether you're a student looking to showcase your work, a professional looking to attract clients, or a
 graphic artist looking to share your projects, this template is the perfect starting point!</p>
 </div>
 );
 }
 }

 ReactDOM.render(
 <Test />,
 document.getElementById('root')
 );
 */