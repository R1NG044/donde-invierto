import React, {PropTypes, Component} from 'react';
import {Col, Row} from 'react-bootstrap';
import './ListaMetodologias.scss';

export default class ListaMetodologias extends Component  {

  render() {

    return (
      <div className="ListaMetodologias">
        {
          this.props.metodologias.map(metodologia =>
            <div className="single-metodologia"
                  onClick={e => this.props.selectMetodologia(metodologia)}>
              <Row>
                <Col lg={12} md={12} sm={12} xs={12}>
                  <h1 className="nombre-metodologia">
                    {metodologia.nombre}
                  </h1>
                </Col>
              </Row>
            </div>
          )
        }
      </div>
    )
  }
}
