/**
 * Created by claudia on 21/07/17.
 * Portfolio shows the portfolio of pokemons.
 */

/** Imports useful for ReactJS
import React from 'react';
import PortfolioItems from './PortfolioItems';
*/

class Portfolio extends React.Component {
    constructor () {
        super();
        var v = ["pichu", "ivysaur", "pikachu", "wartortle", "arceus", "mewtwo",
            "pikachu", "jigglypuff", "eevee", "doduo", "dodrio", "mew",
            "snorlax", "articuno", "metagross" ].sort();
        v = [];
        this.state = {
            pokemons: v,
            selectable: v
        };
        this.onSearchChange = this.onSearchChange.bind(this);
        this.resetSelectable = this.resetSelectable.bind(this);
    }

    onSearchChange(filteredSelection) {
        this.setState({selectable : filteredSelection});
    }

    resetSelectable() {
        var selectable = this.state.pokemons;
        this.setState({ selectable: selectable});
    }

    componentDidMount() {
        var url = "/pokemons/";
        fetch(url).then(r => r.json()).then(
            d => {
                var choice = d.slice(0, 20).sort();
                this.setState({
                    pokemons: choice,
                    selectable: choice
                });
            }
        );
    }

    signalEnter() {
        alert("Enter");
    }

    render() {
        return (
            <section id="portfolio">
                <div className="container">
                    <div className="row">
                        <div className="col-lg-12 text-center">
                            <h2>Pokemons</h2>
                            <hr className="star-primary"/>
                        </div>
                    </div>

                    <div className="row" id="search">
                        <div className="col-lg-4 col-lg-offset-4 text-center">

                            <Autocomplete name={"Test"}
                                          pokemons={this.state.pokemons}
                                          updateParent={this.onSearchChange}
                                          resetParent={this.resetSelectable}/>
                        </div>
                    </div>

                    {/* Grid portfolio */}
                    <div className="row">
                        <PokemonList pokemons={this.state.selectable}/>
                    </div>
                </div>
            </section>
        );
    }
}


// export default Portfolio;