import React from 'react';
import CargaEmpresa from './CargaEmpresa';
import CargaIndicadores from './CargaIndicadores';
import CargaCuenta from './CargaCuenta';
import BorrarIndicadores from './BorrarIndicadores';
import {Col, Grid, Row} from 'react-bootstrap';
import './CargaBase.scss';

const Carga = (props) =>
  <Grid fluid className="Menu">
    <Col lg={6} lgOffset={3}
          md={6} mdOffset={3}
          sm={8} smOffset={2}
          xs={12}>
      <div className="border-box">
        <div className="gray-box">

          {props.children}

        </div>
      </div>
    </Col>
  </Grid>;

Carga.Empresa = CargaEmpresa;
Carga.Cuenta = CargaCuenta;
Carga.Indicadores = CargaIndicadores;
Carga.DeleteIndicadores = BorrarIndicadores;

export default Carga;
