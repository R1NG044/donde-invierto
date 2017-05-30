import React, {PropTypes, Component} from 'react';
import {Grid} from 'react-bootstrap';
import {Link} from 'react-router';
import {LinkContainer} from 'react-router-bootstrap';
import {connect} from 'react-redux';
import * as dataActions from '../../actions/dataActions';
import {bindActionCreators} from 'redux';
import './App.scss';

class App extends Component {
  componentWillMount() {
    this.props.dataActions.loadData(20);
  }

  render() {
    return (
      <div>
        <Link to="/" className="dump-navbar">
          Donde Invierto?
        </Link>

        <Grid fluid>
          {this.props.children}
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
    data: state.data
  };
}

function mapDispatchToProps(dispatch) {
  return {
    dataActions: bindActionCreators(dataActions, dispatch)
  };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App);
