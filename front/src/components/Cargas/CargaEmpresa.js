import React, {PropTypes, Component} from 'react';
import {connect} from 'react-redux';
import * as dataActions from '../../actions/dataActions';
import * as uiActions from '../../actions/uiActions';
import {bindActionCreators} from 'redux';
import Periodos from './Periodos'
import './CargaEmpresa.scss';

class CargaEmpresa extends Component  {

  render() {
    return (
      <div className="CargaEmpresa">
        <form onSubmit={this.props.dataActions.cargarEmpresa}>
          <input type="text"
                  name="nombreEmpresa"
                  placeholder="Nombre Empresa"
                  onChange={this.props.uiActions.cargarEmpresaInputChange}
                  value={this.props.inputsValues.nombreEmpresa} />
          <br />
          <Periodos agregarPeriodo={this.props.uiActions.agregarPeriodo}
                    onInputChange={this.props.uiActions.cargarEmpresaInputChange}
                    periodos={this.props.periodosYaAgregados}
                    inputsValues={this.props.inputsValues} />
          <input type="submit" value="Guardar Empresa" />
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
  )(CargaEmpresa);
