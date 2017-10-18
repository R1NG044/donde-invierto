import React, {PropTypes, Component} from 'react';
import {Grid} from 'react-bootstrap';
import {Link} from 'react-router';
import {LinkContainer} from 'react-router-bootstrap';
import {connect} from 'react-redux';
import * as dataActions from '../../actions/dataActions';
import * as uiActions from '../../actions/uiActions';
import {bindActionCreators} from 'redux';
import Login from '../Login';
import './App.scss';

class App extends Component {

  componentWillReceiveProps(nextProps) {
    if (!this.props.data.loggedIn && nextProps.data.loggedIn) {
      this.props.dataActions.loadData();      
    }
  }

  render() {
      return (
      <div>
        <Link to="/" className="dump-navbar">
          ¿Donde Invierto?
        </Link>

        {
         this.props.showMessage &&
         <div className="save-message">
            {this.props.showMessage}
         </div>
        }


        <Grid fluid>
          {
            this.props.data.loggedIn ?
            <div>{this.props.children}</div> :
            <Login login={this.props.dataActions.login} uiActions={this.props.uiActions} inputsVals={this.props.loginData} />
          }
        </Grid>
      </div>
    )
  }
}


App.propTypes = {
  children: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    data: state.data,
    showMessage: state.ui.showMessage,
    loginData: state.ui.inputsValues.login
  };
}

function mapDispatchToProps(dispatch) {
  return {
    dataActions: bindActionCreators(dataActions, dispatch),
    uiActions: bindActionCreators(uiActions, dispatch)    
  };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App);
