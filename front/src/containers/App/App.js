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
  componentWillMount() {
    this.props.dataActions.loadData(20);
  }

  render() {
      return (
      <div>
        <Link to="/" className="dump-navbar">
          ¿Donde Invierto?
        </Link>

        {
         this.props.showSuccess &&
         <div className="save-message">
            Se ha guardado con exito
         </div>
        }


        <Grid fluid>
          {
            this.props.data.loggedIn ?
            <div>{this.props.children}</div> :
            <Login login={dataActions.login} uiActions={this.props.uiActions} inputsVals={this.props.loginData} />
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
    showSuccess: state.ui.showSuccess,
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
