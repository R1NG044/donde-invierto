import React from 'react';
import {Button} from 'react-bootstrap';
import './Periodos.scss';

const Periodos = (props) =>
  <div className="Periodos">
    <input type="text"
            placeholder="Nombre del periodo a agregar"
            name="nombrePeriodo"
            onChange={props.onInputChange}
            value={props.inputsValues.nombrePeriodo} />
    <input type="text"
            placeholder="Año del período"
            name="anioPeriodo"
            onChange={props.onInputChange}
            value={props.inputsValues.anioPeriodo} />
    <input type="text"
            placeholder="Inicio del rango en meses"
            name="mesInicio"
            onChange={props.onInputChange}
            value={props.inputsValues.mesInicio}/>
    <input type="text"
            placeholder="Fin del rango en meses"
            name="mesFin"
            onChange={props.onInputChange}
            value={props.inputsValues.mesFin} />

    <Button onClick={e => props.agregarPeriodo(props.inputsValues)}
            className="agregar-periodo">
        Agregar nuevo periodo
    </Button>

    {props.periodos.map(periodo => {
      return (
        <input type="text" value={periodo.nombre} readOnly />
      )
    })}
  </div>;

export default Periodos;
