import React, {PropTypes, Component} from 'react';
import {connect} from 'react-redux';
import * as dataActions from '../../actions/dataActions';
import * as uiActions from '../../actions/uiActions';
import {bindActionCreators} from 'redux';
import Periodos from './Periodos'
import './CargaMetodologias.scss';

class CargaMetodologias extends Component  {

  render() {
    const {type} = this.props;
    debugger;
    return (
      <div className="Carga Metodologias">
        <form onSubmit={this.props.dataActions.cargarMetodologia(type)}>
          <input type="text"
                  name="nombreMetodologia"
                  placeholder="Nombre Metodologia"
                  onChange={this.props.uiActions.inputChanged(type)}
                  value={this.props.inputsValues.metodologia.nombreMetodologia} />
          <br />
          <input type="submit" value="Guardar Metodología" />
        </form>
      </div>
    )
  }
}


  function mapStateToProps(state) {
    return {
      inputsValues: state.ui.inputsValues,
      periodosYaAgregados: state.ui.periodosPorAgregar
    };
  }

  function mapDispatchToProps(dispatch) {
    return {
      dataActions: bindActionCreators(dataActions, dispatch),
      uiActions: bindActionCreators(uiActions, dispatch),
    };
  }

  export default connect(
    mapStateToProps,
    mapDispatchToProps
  )(CargaMetodologias);
