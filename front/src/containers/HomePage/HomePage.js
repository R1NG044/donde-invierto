import React from 'react';
import {Col, Row, Grid} from 'react-bootstrap';
import ListaEmpresas from '../../components/ListaEmpresas/ListaEmpresas';
import DetalleEmpresa from '../../components/DetalleEmpresa/DetalleEmpresa';
import {connect} from 'react-redux';
import * as dataActions from '../../actions/dataActions';
import * as uiActions from '../../actions/uiActions';
import {bindActionCreators} from 'redux';
import {periodosOrdenados} from '../../selectors/DataSelector';

class HomePage extends React.Component {
  componentWillMount() {
    if(this.props.withRanking) {
      this.props.uiActions.selectEmpresa(this.props.empresas[0]);
    }
  }

  render() {
    return (
      <Grid fluid>
        <Col lg={2} md={2} sm={4} xs={12}>
          <ListaEmpresas empresas={this.props.empresas}
                          selectEmpresa={this.props.uiActions.selectEmpresa}
                          withRanking/>
        </Col>
        <Col lg={10} md={10} sm={6} xs={12}>
          <DetalleEmpresa empresa={this.props.empresaSelected} />
        </Col>

      </Grid>
    )
  }
}

function mapStateToProps(state, props) {
  const empresas = props.empresas ? periodosOrdenados(props.empresas) : periodosOrdenados(state);
  return {
    empresaSelected: state.ui.empresaSelected,
    empresas
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
