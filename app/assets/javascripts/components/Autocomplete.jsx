// See https://developer.mozilla.org/en/docs/Web/JavaScript/Guide/Regular_Expressions#Using_Special_Characters
function escapeRegexCharacters(str) {
    return str.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

function getSuggestions(value, valset) {
    const escapedValue = escapeRegexCharacters(value.trim());

    if (escapedValue === '') {
        return [];
    }

    const regex = new RegExp('^' + escapedValue, 'i');

    return valset.filter(p => regex.test(p));
}

function getSuggestionValue(suggestion) {
    return suggestion;
}

function renderSuggestion(suggestion) {
    return (
        <span>{suggestion}</span>
    );
}
var borderStyle;
var theme = {
    container:                'react-autosuggest__container',
    containerOpen:            'react-autosuggest__container--open',
    input:                    'form-control',
    inputOpen:                'react-autosuggest__input--open',
    inputFocused:             'react-autosuggest__input--focused',
    suggestionsContainer:     'react-autosuggest__suggestions-container',
    suggestionsContainerOpen: 'react-autosuggest__suggestions-container--open',
    suggestionsList:          'list-inline',
    suggestion:               '',
    suggestionFirst:          'react-autosuggest__suggestion--first',
    suggestionHighlighted:    'react-autosuggest__suggestion--highlighted',
    sectionContainer:         'react-autosuggest__section-container',
    sectionContainerFirst:    'react-autosuggest__section-container--first',
    sectionTitle:             'react-autosuggest__section-title'
};

class Autocomplete extends React.Component {
    constructor(props) {
        super(props);

        /** Autosuggest is a controlled component.
         * This means that you need to provide an input value
         * and an onChange handler that updates this value (see below).
         * Suggestions also need to be provided to the Autosuggest,
         * and they are initially empty because the Autosuggest is closed.
         * */
        this.state = {
            value: '',
            suggestions: []
        };

        this.props = props;

        this.onChange = this.onChange.bind(this);
        this.onSuggestionsFetchRequested = this.onSuggestionsFetchRequested.bind(this);
        this.onSuggestionsClearRequested = this.onSuggestionsClearRequested.bind(this);
        this.onSuggestionSelected = this.onSuggestionSelected.bind(this);
    }

    onChange (event, { newValue }) {
        this.setState({
            value: newValue
        });
    }

    /** Autosuggest will call this function every time we need to update
     *  suggestions. We already implemented this logic above, so just use it.
     */
    onSuggestionsFetchRequested ({ value }) {
        var selectable = getSuggestions(value, this.props.pokemons);
        this.setState({
            suggestions: selectable
        });
        this.props.updateParent(selectable);
    };

    /** Autosuggest will call this function every time we need to clear
     *  suggestions.
     */
    onSuggestionsClearRequested () {
        var s = [];
        this.setState({
            suggestions: s
        });
        this.props.resetParent();
    };

    onSuggestionSelected(event, { suggestion, suggestionValue, suggestionIndex, sectionIndex, method }) {
        window.location = "/pokecard/" + suggestionValue; // Goto this url :-)
    }

    render() {
        const { value, suggestions } = this.state;
        console.log("Autocomplete", this.props);
        // Autosuggest will pass through all these props to the input element.
        const inputProps = {
            placeholder: 'Type a Pokemon name',
            value,
            onChange: this.onChange
        };

        // Finally, render it!
        return (
            <Autosuggest
                suggestions={suggestions}
                onSuggestionsFetchRequested={this.onSuggestionsFetchRequested}
                onSuggestionsClearRequested={this.onSuggestionsClearRequested}
                getSuggestionValue={getSuggestionValue}
                onSuggestionSelected={this.onSuggestionSelected}
                renderSuggestion={renderSuggestion}
                inputProps={inputProps}
                theme={theme}
            />
        );
    }
}
