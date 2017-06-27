import React, {PropTypes, Component} from 'react';
import {connect} from 'react-redux';
import * as dataActions from '../../actions/dataActions';
import * as uiActions from '../../actions/uiActions';
import {bindActionCreators} from 'redux';
import {Link} from 'react-router'
import './CargaMetodologias.scss';

class CargaMetodologias extends Component  {


  render() {
    const {type, indicadores} = this.props;

    const inputsValues = this.props.inputsValues.metodologia;

    debugger;
    return (
      <div className="Carga Metodologias">
        <form onSubmit={this.props.dataActions.cargarMetodologia(type)}>
          <input type="text"
                  name="nombreMetodologia"
                  placeholder="Nombre Metodologia"
                  onChange={this.props.uiActions.inputChanged(type)}
                  value={inputsValues.nombreMetodologia} />
            {
              indicadores.length ?
              <div>
                <h5>Desea crear reglas?</h5>
                <select name="indicadorSelected"
                        onChange={uiActions.addIndicadorAMetodologia(type)}>
                    {
                        indicadores.map(indicador =>
                            <option value={indicador.id}>{indicador.nombre}</option>
                        )
                    }
                </select>
                <select name="reglaSelected">
                  <option value="Sasa">MAX</option>
                  <option value="Sasa">MIN</option>
                  <option value="Sasa"> &le; </option>
                  <option value="Sasa"> &ge; </option>
                </select>
              </div>
                  :
              <div>
                Si desea utilizar indicadores por favor cree uno <Link to="/cargar/newIndicador">aqui</Link>
              </div>
            }
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
