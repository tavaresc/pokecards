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
                {this.props.name}
                <span className="tw-nick">@{this.props.nick}</span>
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

    renderTweet(tw) {
        console.log("Tweets for ", this.props.pokemon.name, tw);
        return (
            <div key={tw.id} className="tw-tweet row">
                <div className="row">
                    <div className="tw-identifer">
                        <div className="col-md-4">
                            <User nick={tw.user.screen_name} name={tw.user.name} img={tw.user.profile_img}/>
                        </div>
                        <div className="tw-date col-md-8">{tw.date}</div>
                    </div>
                </div>
                <div className="row">
                    <div className="tw-urls col-md-12">
                        {tw.urls.map((u,id) => {
                            return (<a key={id} href={u}>{u}</a>);
                        }) }
                    </div>
                    <div className="tw-hashtags col-md-12">
                        {tw.hashtags.map((h,id) => {
                            var txt = "#" + h + " ";
                            var href = "https://twitter.com/search?q=%23" + h;
                            return (<a key={id} className="tw-hashtag" href={href}>{txt}</a>);
                        })}
                    </div>
                    <div className=" tw-text col-md-12">
                        { tw.text }
                    </div>
                    <div className="col-md-12 center">
                        {tw.media.map((m,id) => {
                            return (<Media key={id} media={m}/>);
                        })}
                    </div>
                </div>
            </div>
        );
    }

    render() {
        return (
            <div>
                <h4 className="tweets-header">
                    <i className="fa fa-twitter" aria-hidden="true"></i>
                    # {this.props.pokemon.name}
                </h4>
                <div className="tweets">
                    {this.state.tweets.map(tw => this.renderTweet(tw))}
                </div>

            </div>
        );
    }
}