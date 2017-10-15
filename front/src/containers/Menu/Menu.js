import React from 'react';
import {Col, Row, Grid} from 'react-bootstrap';
import {periodosOrdenados} from '../../selectors/DataSelector';
import {Link} from 'react-router';
import MenuComponent from '../../components/commons/MenuComponent';

const Menu = (props) =>
  <MenuComponent title="Menu">
            <Link to="/cargar/cuenta"> Cargar cuenta </Link>
            <Link to="/menu/indicadores"> Cargar y/o eliminar indicadores </Link>
            <Link to="/metodologias"> Aplicar metodologias </Link>
            <Link to="/empresas"> Consultar Empresas </Link>
			<Link to="/empresa/analizar"> Realizar análisis </Link>
			

  </MenuComponent>;


export default Menu;
