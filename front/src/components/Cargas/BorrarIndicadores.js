import React, {PropTypes, Component} from 'react';
import {connect} from 'react-redux';
import * as dataActions from '../../actions/dataActions';
import * as uiActions from '../../actions/uiActions';
import {bindActionCreators} from 'redux';
import Periodos from './Periodos'
import {Link} from 'react-router';
//import './BorrarIndicadores.scss';
import './CargaBase.scss';

export default class BorrarIndicadores extends Component  {

  render() {
    const { type, dataActions, uiActions, inputsValues } = this.props;

    // TODO: MOCK JUANI
    let indicadores = [
      {nombre: "indicador1", descripcion: "es un mock de indicador", id: 0},
      {nombre: "indicador2", descripcion: "es un mock de indicador", id: 1}
    ]
    return (
      <div className="Carga Indicadores">
        <form onSubmit={dataActions.borrarIndicador(type)}>
          <p className="description"> Seleccione Empresa: </p>
          <select name="nombreIndicador"
                  onChange={uiActions.inputChanged(type)}>
            {
              indicadores.map(indicador =>
                <option value={indicador.id}>{indicador.nombre}</option>
              )
            }
          </select>

          <input type="submit" value="Borrar Indicador" />
        </form>
      </div>
    )
  }
}
