import React from 'react';
import {Col, Row, Grid} from 'react-bootstrap';
import HomePage from '../../containers/HomePage/HomePage';
import ListaMetodologias from '../../components/ListaMetodologias/ListaMetodologias';
import DetalleMetodologia from '../../components/DetalleMetodologia/DetalleMetodologia';
import NoMetodologias from '../../components/NoMetodologias/NoMetodologias';
import SelectMetodologia from '../../components/SelectMetodologia/SelectMetodologia';
import * as dataActions from '../../actions/dataActions';
import * as uiActions from '../../actions/uiActions';
import {bindActionCreators} from 'redux';
import {periodosOrdenados} from '../../selectors/DataSelector';
import {Link} from 'react-router';
import {connect} from 'react-redux';

const Metodologias = (props) =>
  <Grid fluid>
    {
      !props.metodologias.length ?
      <NoMetodologias />
      :
      <div>
        <Row>
          <SelectMetodologia metodologias={props.metodologias}
                              dataActions={props.dataActions} />
        </Row>
        <Row>
          {
            props.metodologiaSelected &&
            <HomePage empresas={props.empresas}
                      withRanking />
          }
        </Row>
      </div>
    }

  </Grid>;



function mapStateToProps(state) {
  return {
    metodologiaSelected: state.ui.metodologiaSelected,
    metodologias: state.data.metodologias,
    empresasByMetodologias: state.data.empresasByMetodologias
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
)(Metodologias);
