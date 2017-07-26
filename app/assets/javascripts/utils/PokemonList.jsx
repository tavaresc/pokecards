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
            data => that.setState({likes: data.likes, dislikes: data.dislikes})
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
                <button className="btn btn-opinion btn-like" onClick={this.like}>
                    <i className="fa fa-thumbs-o-up" aria-hidden="true"></i>
                </button>
                <span className="single-text like">
                    {this.state.likes}
                </span>

                <button className="btn btn-opinion btn-dislike" onClick={this.dislike}>
                    <i className="fa fa-thumbs-o-down" aria-hidden="true"></i>
                </button>
                <span className="single-text dislike">
                    {this.state.dislikes}
                </span>
            </div>
        );
    }
}

class PokemonList extends React.Component {
    renderPortfolio(pk) {
        var href = '/pokecard/' + pk;
        return (
            <div className="col-xs-6 col-sm-3 col-md-2 portfolio-item" key={pk}>
                <a href={ href } className="portfolio-link">
                    <div className="caption">
                        <div className="caption-content">
                            <i className="fa fa-search-plus fa-3x"></i>
                        </div>
                    </div>
                    <Pokemon key={pk} pokemon={pk} />
                </a>

                <div className="name">
                    <h3>{pk}</h3>
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
                {this.props.pokemons.map(pk => this.renderPortfolio(pk))}
            </div>
        )
    }
};

// export default PokemonList;