import React, {PureComponent} from 'react';
import {Col, Row} from 'react-bootstrap';
import './DetalleMetodologia.scss';

export default class DetalleMetodologia extends PureComponent {
  render() {
    if(!this.props.metodologia) {
      return (
        <h3>Por favor seleccione una metodologia</h3>
      )
    }
    return (
      <div className="DetalleMetodologia">
        <Row>
          <Col lg={12} md={12} sm={12} xs={12} className="detalle-nombre-metodologia">
            {this.props.metodologia.nombre}
          </Col>
        </Row>
      </div>
    )
  }

}
