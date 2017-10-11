import React, {PropTypes, Component} from 'react';
import {Button} from 'react-bootstrap';
import './CargaIndicadores.scss';
import './CargaBase.scss';

export default class CargaIndicadores extends Component  {

  constructor(props) {
    super(props);

    this.state = {
      secondPartOfForm: false
    };

      this.toSecondPartOfForm = this.toSecondPartOfForm.bind(this);
  }


  toSecondPartOfForm(e) {
    e.preventDefault();

    this.setState({
     secondPartOfForm: true
    });
  }

  render() {
    const { type, dataActions, uiActions, inputsValues, empresas, indicadores, cuentas } = this.props;
    const secondPartOfForm = this.state.secondPartOfForm;

    const empresaSelected = empresas.find(empresa => empresa.oid === parseInt(inputsValues.empresaSelected) );

    return (
      <div className="Carga Indicadores">
        <form onSubmit={secondPartOfForm ? dataActions.cargarIndicador(type) : this.toSecondPartOfForm}>

          {
            !secondPartOfForm &&
            <div>
            <p className="description"> Ingrese el nombre del indicador: </p>
            <input type="text"
              name="nombreIndicador"
              placeholder="Nombre del indicador"
              onChange={uiActions.inputChanged(type)}
              value={inputsValues.nombreIndicador} />

              <p className="description"> Ingrese la expresion del indicador: </p>
              <input type="text"
                      name="expresionIndicador"
                      placeholder="Expresion del indicador"
                      onChange={uiActions.inputChanged(type)}
                      value={inputsValues.expresionIndicador} />

              <p className="description short"> Agregar indicador a la expresión: </p>
              <select name="expresionSelected"
              onChange={uiActions.addDataToExpresion(type)}>
              <option selected disabled>Seleccione un indicador:</option>

              {
                  indicadores.map(indicador =>
                      <option value={`indicador{${indicador.oid}}`}>{indicador.nombre}</option>
                  )
              }
              </select>

              <p className="description short"> Agregar cuenta a la expresión: </p>
              <select name="cuentaSelected"
              onChange={uiActions.addDataToExpresion(type)}>
              <option selected disabled>Seleccione una cuenta:</option>
              {
                  cuentas.map(cuenta =>
                      <option value={`cuenta{${cuenta.descripcion}}`}>{cuenta.descripcion}</option>
                  )
              }
              </select>

            </div>
          }

          {
            secondPartOfForm &&
             <div>
              <p className="description short"> Seleccione empresa a evaluar: </p>
              <select name="empresaSelected"
                onChange={uiActions.inputChanged(type)}>
                <option selected disabled>Seleccione una empresa</option>
                {
                  empresas.map(empresa =>
                  <option value={empresa.oid}>{empresa.nombre}</option>
                  )
                }
              </select>

               <p className="description short"> Seleccione periodo a evaluar: </p>
               <select name="periodoSelected"
                       onChange={uiActions.inputChanged(type)}>
                  <option selected disabled>Seleccione un periodo</option>
                   {
                     empresaSelected &&
                     empresaSelected.periodos.map(periodo =>
                       <option value={periodo.oid}>{periodo.nombre}</option>
                     )
                   }
               </select>
             </div>
           }



          {
           !secondPartOfForm ?
               <Button onClick={this.toSecondPartOfForm} >Guardar Indicador</Button>
               :
               <input type="submit" value="Guardar Indicador" />
          }
        </form>
      </div>
    )
  }
}
