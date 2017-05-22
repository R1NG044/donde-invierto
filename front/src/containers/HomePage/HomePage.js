import React from 'react';
import {Col, Row, Grid} from 'react-bootstrap';
import ListaEmpresas from '../../components/ListaEmpresas/ListaEmpresas';
import DetalleEmpresa from '../../components/DetalleEmpresa/DetalleEmpresa';
import {connect} from 'react-redux';
import * as dataActions from '../../actions/dataActions';
import * as uiActions from '../../actions/uiActions';
import {bindActionCreators} from 'redux';
import {periodosOrdenados} from '../../selectors/DataSelector';

const HomePage = (props) =>
  <Grid fluid>
    <Col lg={2} md={2} sm={4} xs={12}>
      <ListaEmpresas empresas={props.empresas}
                      selectEmpresa={props.uiActions.selectEmpresa}/>
    </Col>
    <Col lg={10} md={10} sm={6} xs={12}>
      <DetalleEmpresa empresa={props.empresaSelected} />
    </Col>

  </Grid>;



function mapStateToProps(state) {
  return {
    empresaSelected: state.ui.empresaSelected,
    empresas: periodosOrdenados(state)
  };
}

function mapDispatchToProps(dispatch) {
  return {
    dataActions: bindActionCreators(dataActions, dispatch),
    uiActions: bindActionCreators(uiActions, dispatch)
  };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HomePage);
