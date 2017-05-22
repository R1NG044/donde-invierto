import React from 'react';
import {Col, Row} from 'react-bootstrap';
import './DetalleEmpresa.scss';

const DetalleEmpresa = (props) =>
  <div className="DetalleEmpresa">
    <Row>
      <Col lg={12} md={12} sm={12} xs={12} className="detalle-nombre-empresa">
        {props.empresa.nombre}
      {
        props.empresa.periodos &&
        Object.values(props.empresa.periodos).map(periodosAnuales =>
        <div>
          <Col lg={12} md={12} sm={12} xs={12} className="anio">
            {periodosAnuales[0].anio}
          </Col>
          {
            periodosAnuales.map(periodo =>
              <Col lg={periodo.rango[1] - periodo.rango[0]}
                    md={periodo.rango[1] - periodo.rango[0]}
                    sm={12}
                    xs={12}
                    className="periodo">
                {periodo.nombre} : {periodo.anio}
              </Col>
            )
          }
        </div>
      )}
      </Col>
    </Row>
  </div>;

export default DetalleEmpresa;
