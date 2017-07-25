/**
 * Created by claudia on 22/07/17.
 * PokemonList creates a list of pokemon models and render information according to the current webpage:
 * - renderPortfolio(): it renders all pokemons on the initial page
 */

/** Imports necessary in reactJS
import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import Pokemon from './../utils/Pokemon';
 */

class Opinion extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            likes : 0,
            dislikes : 0
        };
        this.like = this.like.bind(this);
        this.dislike = this.dislike.bind(this);
        this.expressOpinion = this.expressOpinion.bind(this);
    }

    componentDidMount() {
        var that = this;
        var base_url = "/pokemons/opinions/" + this.props.name;
        fetch(base_url).then(r => r.json()).then(
            data => that.setState(data)
        );
    }

    expressOpinion (opinion_type) {
        var that = this;
        var url = "/pokemons/" + opinion_type + "/" + this.props.name;
        fetch(url, { method : 'POST'}).then(r => r.json()).then(
            d => {
                console.log("Opinion expressed for" + this.props.name, d);
                that.setState(d);
            }
        );
    }

    like() {
        this.expressOpinion("like");
    }

    dislike() {
        this.expressOpinion("dislike");
    }

    render() {
        return(
            <div>
                <button className="btn btn-default" onClick={this.like}>
                    <i className="fa fa-thumbs-o-up" aria-hidden="true"></i>
                </button>
                <span className="label label-success">{this.state.likes}</span>
                <button className="btn btn-default" onClick={this.dislike}>
                    <i className="fa fa-thumbs-o-down" aria-hidden="true"></i>
                </button>
                <span className="label label-danger">{this.state.dislikes}</span>
            </div>
        );
    }
}

class PokemonList extends Component {
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

                <div className="name">
                    <h3>{pk.name}</h3>
                </div>

                <div className="opinion">
                    <Opinion name={pk}/>
                </div>
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

// export default PokemonList;