import React from 'react';
import {Col, Row} from 'react-bootstrap';
import './DetalleEmpresa.scss';
import Cuenta from './Cuenta';

const Periodo = (props) =>  {
    debugger;
    return (
        <Col lg={props.periodo.endRange - props.periodo.startRange}
        md={props.periodo.endRange - props.periodo.startRange}
        sm={12}
        xs={12}
        className="periodo"
        onClick={props.onClick(props.periodo.oid)}>
        {props.periodo.nombre} : {props.periodo.anio}

        {
            props.selected === props.periodo.oid ? 
            <div>
                {
                    props.periodo.cuentas.map( (cuentaX) => 
                    <Cuenta cuentaX={cuentaX} />
                )
                }
            </div> :
            <div></div>
        }

    </Col>
    )
}


export default Periodo