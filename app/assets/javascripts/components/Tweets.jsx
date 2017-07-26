class Media extends React.Component {
    render() {
        console.log(this.props.media);
        /* Here we probably do not handle all types well.
         * If this is a picture we inline it with a img tag
         * Otherwise it is treated as the specification an anchor tag
         */
        switch (this.props.media.type) {
            case "photo":
                return (
                    <img className="tw-img" src={this.props.media.url} alt={this.props.media.url}/>
                );
            default:
                return (
                    <a className="tw-media" href={this.props.media.url}>{this.props.media.url}</a>
                );
        }
    }
}

class User extends React.Component {
    render() {
        return(
            <div className="tw-user">
                <img className="tw-profile-img" src={this.props.img} alt={this.props.name}/>
                <span className="tw-name">{this.props.name}</span>
                <span className="tw-nick">@{this.props.nick}</span>
                <span className="tw-date text-right">{this.props.date}</span>
            </div>
        );
    }
}
class Tweets extends React.Component {
    constructor(props) {
        super(props);
        this.state = { tweets : [] };
    }

    componentDidMount() {
        var url = "/twitterfeed/" + this.props.pokemon.name;

        fetch(url).then(r => r.json()).then(
            data => {
                this.setState({tweets : data });
            }
        );
    }

    getHashTag(text, hashtag) {
        return
    }

    renderTweet(tw) {
        console.log("Tweets for ", this.props.pokemon.name, tw);
        return (
            <div key={tw.id} className="tw-tweet row">
                <div className="row">
                    <div className="tw-identifer">
                        <div className="col-sx-12">
                            <User date={tw.date} nick={tw.user.screen_name} name={tw.user.name} img={tw.user.profile_img}/>
                        </div>
                    </div>
                </div>
                <div className="row">
                    {/*
                    <div className="tw-urls col-md-12 hyphenate">
                        {tw.urls.map((u,id) => {
                            return (<a key={id} href={u}>{u}</a>);
                        }) }
                    </div>
                    */}

                    <div className="hyphenate tw-text col-md-12">
                        { tw.text }
                    </div>

                    <div className="tw-hashtags col-md-12">
                        {tw.hashtags.map((h,id) => {
                            var txt = "#" + h + " ";
                            var href = "https://twitter.com/search?q=%23" + h;
                            return (<a key={id} className="tw-hashtag" href={href}>{txt}</a>);
                        })}
                    </div>

                    <div className="tw-media col-md-12">
                        {tw.media.map((m,id) => {
                            return (<Media key={id} media={m}/>);
                        })}
                    </div>
                </div>
            </div>
        );
    }

    render() {
        var tweet_render;
        var tweet_search_url = "https://twitter.com/search?q=%23" + this.props.pokemon.name;

        if (this.state.tweets.length > 0) {
            tweet_render = this.state.tweets.map(tw => this.renderTweet(tw));
        }
        else {
            tweet_render = (
               <div>
                   <span>Seems like noone's been tweeting about poor</span>
                   <span className="pokemon-name"> {this.props.pokemon.name} </span>
                   <i className="fa fa-frown-o" aria-hidden="true"> </i>
                </div>);
        }

        return (
            <div>
                <span className="tweets-header">
                    <i className="fa fa-twitter-square twitter-color" aria-hidden="true"></i>
                </span>
                <span className="tweet-query">
                    <a href={tweet_search_url}># {this.props.pokemon.name}</a>
                </span>
                <div className="tweets">
                    {tweet_render}
                </div>

            </div>
        );
    }
}