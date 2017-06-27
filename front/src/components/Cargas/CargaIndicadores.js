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
    const { type, dataActions, uiActions, inputsValues, empresas } = this.props;
    const secondPartOfForm = this.state.secondPartOfForm;


    const indicadores = [{ id: 0, nombre: "Indicador X", expresion: "indicador{indicador1}" },
                         { id: 0, nombre: "Indicador Y", expresion: "indicador{indicador2}" }];

    const cuentas = [{ id: 0, nombre: "Cuenta X", periodo: "Q1", valor: "cuenta{cuenta1}" },
                      { id: 0, nombre: "Cuenta Y", periodo: "Q2", valor: "cuenta{cuenta2}" }];

    const empresaSelected = empresas.find(empresa => empresa.id === parseInt(inputsValues.empresaSelected) );

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
              {
                  indicadores.map(indicador =>
                      <option value={indicador.expresion}>{indicador.nombre}</option>
                  )
              }
              </select>

              <p className="description short"> Agregar cuenta a la expresión: </p>
              <select name="cuentaSelected"
              onChange={uiActions.addDataToExpresion(type)}>
              {
                  cuentas.map(cuenta =>
                      <option value={cuenta.valor}>{cuenta.nombre}</option>
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
                {
                  empresas.map(empresa =>
                  <option value={empresa.id}>{empresa.nombre}</option>
                  )
                }
              </select>

               <p className="description short"> Seleccione periodo a evaluar: </p>
               <select name="periodoSelected"
                       onChange={uiActions.inputChanged(type)}>
                   {
                     empresaSelected &&
                     empresaSelected.periodos.map(periodo =>
                       <option value={periodo.id}>{periodo.nombre}</option>
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
