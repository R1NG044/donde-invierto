import React, {PropTypes, Component} from 'react';
import {connect} from 'react-redux';
import * as dataActions from '../../actions/dataActions';
import * as uiActions from '../../actions/uiActions';
import {bindActionCreators} from 'redux';
import Periodos from './Periodos'
import './CargaCuenta.scss';
import './CargaBase.scss';


export default class CargaCuenta extends Component  {
  componentWillMount() {
    // Fetch de empresas
  }

  render() {
    if(!this.props.empresas) {
      return <div>Cargando empresas...</div>
    }

    const {empresas, type, inputsValues, dataActions, uiActions} = this.props;

    const empresaSelected = empresas
        .find(empresa => empresa.id === parseInt(inputsValues.empresaSelected));

    return (
      <div className="Carga Cuenta">
        <form onSubmit={dataActions.cargarCuenta(type)}>
          <p className="description"> Seleccione Empresa: </p>
          <select name="empresaSelected"
                  onChange={uiActions.inputChanged(type)}>
            {
              empresas.map(empresa =>
                <option value={empresa.id}>{empresa.nombre}</option>
              )
            }
          </select>
          <p className="description"> Seleccione un periodo: </p>
          <select name="periodoSelected"
                  onChange={uiActions.inputChanged(type)}>
            {
              empresaSelected ?
                empresaSelected.periodos.map(periodo =>
                  <option value={periodo.id}>{periodo.nombre}</option>
                )
                :
                <option value="undefined">Seleccione primero una empresa</option>
            }
          </select>

          <p className="description"> Ingrese la descripcion de la cuenta: </p>
          <input type="text"
                  name="nombreCuenta"
                  placeholder="Descripcion de la cuenta"
                  onChange={uiActions.inputChanged(type)}
                  value={inputsValues.nombreCuenta} />

          <p className="description"> Ingrese el valor de la cuenta: </p>
          <input type="text"
                  name="valorCuenta"
                  placeholder="Valor de la cuenta"
                  onChange={uiActions.inputChanged(type)}
                  value={inputsValues.valorCuenta} />

          <input type="submit" value="Guardar Cuenta" />
        </form>
      </div>
    )
  }
}