import React from 'react';
import {Col, Row} from 'react-bootstrap';
import Periodo from './Periodo';
import './DetalleEmpresa.scss';

const DetalleEmpresa = (props) =>
  <div className="DetalleEmpresa">
    <Row>
      <Col lg={12} md={12} sm={12} xs={12} className="detalle-nombre-empresa">
        <span className="nombre-empresa">{props.empresa.nombre}</span>
      {
        props.empresa.periodos &&
        Object.values(props.empresa.periodos).map(periodosAnuales =>
        <div>
          <Col lg={12} md={12} sm={12} xs={12} className="anio">
            {periodosAnuales[0].anio}
          </Col>
          {
            periodosAnuales.map(periodo =>
              <Periodo periodo={periodo} selected={props.selected} onClick={props.selectPeriodo}/>
            )
          }
        </div>
      )}
      </Col>
    </Row>
  </div>;

export default DetalleEmpresa;
