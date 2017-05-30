import React, {PropTypes, Component} from 'react';
import {connect} from 'react-redux';
import * as dataActions from '../../actions/dataActions';
import * as uiActions from '../../actions/uiActions';
import {bindActionCreators} from 'redux';
import Periodos from './Periodos'
import {Link} from 'react-router';
import './CargaIndicadores.scss';
import './CargaBase.scss';

export default class CargaIndicadores extends Component  {

  render() {
    const { type, dataActions, uiActions, inputsValues } = this.props;

    return (
      <div className="Carga Indicadores">
        <form onSubmit={dataActions.cargarIndicador(type)}>
          <p className="description"> Ingrese el nombre del indicador: </p>
          <input type="text"
                  name="nombreIndicador"
                  placeholder="Nombre del indicador"
                  onChange={uiActions.inputChanged(type)}
                  value={inputsValues.nombreIndicador} />

          <p className="description"> Ingrese la descripcion del indicador: </p>
          <input type="text"
                  name="descripcionIndicador"
                  placeholder="Descripcion del indicador"
                  onChange={uiActions.inputChanged(type)}
                  value={inputsValues.descripcionIndicador} />

          <input type="submit" value="Guardar Cuenta" />
        </form>
      </div>
    )
  }
}
