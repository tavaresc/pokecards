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
        this.state = {
            pokemons: [],
            selectable: []
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
                // console.log("Portfolio pks: " + d);
                //console.log("Hey", d.slice(0, 150).map(e => "\"" + e + "\""));
                var howmany = 500; // Between 10 & 811
                var choice = d.slice(0, howmany).sort();
                this.setState({
                    pokemons: choice,
                    selectable: choice
                });
            }
        );
    }

    // Replace componentDidMount by this function in case it is problematic
    componentDidMountHardCoded() {
        var pokemons = ["bulbasaur","ivysaur","venusaur","charmander","charmeleon","charizard","squirtle","wartortle",
            "blastoise","caterpie","metapod","butterfree","weedle","kakuna","beedrill","pidgey","pidgeotto","pidgeot",
            "rattata","raticate","spearow","fearow","ekans","arbok","pikachu","raichu","sandshrew","sandslash",
            "nidoran-f","nidorina","nidoqueen","nidoran-m","nidorino","nidoking","clefairy","clefable","vulpix",
            "ninetales","jigglypuff","wigglytuff","zubat","golbat","oddish","gloom","vileplume","paras","parasect",
            "venonat","venomoth","diglett","dugtrio","meowth","persian","psyduck","golduck","mankey","primeape",
            "growlithe","arcanine","poliwag","poliwhirl","poliwrath","abra","kadabra","alakazam","machop","machoke",
            "machamp","bellsprout","weepinbell","victreebel","tentacool","tentacruel","geodude","graveler","golem","ponyta",
            "rapidash","slowpoke","slowbro","magnemite","magneton","farfetchd","doduo","dodrio","seel","dewgong",
            "grimer","muk","shellder","cloyster","gastly","haunter","gengar","onix","drowzee","hypno","krabby",
            "kingler","voltorb","electrode","exeggcute","exeggutor","cubone","marowak","hitmonlee","hitmonchan",
            "lickitung","koffing","weezing","rhyhorn","rhydon","chansey","tangela","kangaskhan","horsea","seadra",
            "goldeen","seaking","staryu","starmie","mr-mime","scyther","jynx","electabuzz","magmar","pinsir","tauros",
            "magikarp","gyarados","lapras","ditto","eevee","vaporeon","jolteon","flareon","porygon","omanyte","omastar"].sort();
        this.setState({
            pokemons: pokemons,
            selectable: pokemons
        });
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

                            <Autocomplete name={"Pokecomplete"}
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