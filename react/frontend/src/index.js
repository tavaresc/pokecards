import React from 'react';
import ReactDOM from 'react-dom';
import './../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './../node_modules/jquery/dist/jquery.min.js';
//import './../freelancer-theme/vendor/bootstrap/css/bootstrap.min.css';
import './../freelancer-theme/vendor/font-awesome/css/font-awesome.min.css';
import './../freelancer-theme/css/freelancer.css';
//import jQuery from './../freelancer-theme/js/freelancer.js';
//export default jQuery;
//import './../freelancer-theme/js/freelancer.js';
//import jQuery from 'jquery';
//window.jQuery = jQuery;


// See https://github.com/facebookincubator/create-react-app/blob/master/packages/react-scripts/template/README.md#adding-a-css-preprocessor-sass-less-etc
//import './less/freelancer.less';

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